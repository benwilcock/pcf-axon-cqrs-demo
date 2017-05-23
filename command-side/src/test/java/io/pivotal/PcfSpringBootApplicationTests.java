package io.pivotal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by benwilcock on 11/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcfSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PcfSpringBootApplicationTests {

    @Test
    public void contextLoads() {
    }
}
