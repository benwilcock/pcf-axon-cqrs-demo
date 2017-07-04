package io.pivotal.catalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Created by benwilcock on 11/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcfAxonCqrsQuerySideApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class PcfAxonCqrsQuerySideApplicationTest {

    @LocalServerPort
    int port;

    @Value("${local.management.port}")
    private int mgt;

    @MockBean
    AmqpAdmin admin;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldReturn200WhenSendingRequestToActuatorInfoEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
