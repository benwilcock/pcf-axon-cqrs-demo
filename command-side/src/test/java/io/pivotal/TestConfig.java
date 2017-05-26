package io.pivotal;

import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by amirea on 5/24/17.
 */
@Configuration
@Profile("test")
public class TestConfig {

    @MockBean
    public AmqpAdmin amqpAdmin;
}
