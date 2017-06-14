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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CatalogController.class)
public class CatalogControllerTest {

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
    public void getTheView() throws Exception {
        given(eventProcessor.getDataMap()).willReturn(map);
        mvc.perform(get("/catalog/view").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/html;charset=UTF-8")));
    }
}
