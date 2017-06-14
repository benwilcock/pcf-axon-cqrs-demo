package io.pivotal.catalog.controllers;


import io.pivotal.catalog.components.EventProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CatalogRestController.class)
public class CatalogRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventProcessor eventProcessor;

    private static ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

    @Before
    public void init() {
        map.putIfAbsent("id", "name");
    }

    @Test
    public void getTheData() throws Exception {
        when(eventProcessor.getDataMap()).thenReturn(map);

        mvc.perform(get("/catalog/data").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':'name'}"));

        then(eventProcessor.getDataMap());

    }
}
