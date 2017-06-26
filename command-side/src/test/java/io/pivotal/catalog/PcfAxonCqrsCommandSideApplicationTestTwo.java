package io.pivotal.catalog;

import io.pivotal.catalog.services.CatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benwilcock on 11/05/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PcfAxonCqrsCommandSideApplication.MainController.class)
public class PcfAxonCqrsCommandSideApplicationTestTwo {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkDefaultPageLoads() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}
