package io.pivotal.catalog.configurations;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by amirea on 5/24/17.
 */
@Configuration
@Profile("mockAmqpAdminForEventPublicationConfig")
public class TestAmqpAdminConfig {

    @MockBean
    public AmqpAdmin amqpAdmin;
}
