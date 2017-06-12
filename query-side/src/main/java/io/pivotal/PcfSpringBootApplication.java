package io.pivotal;

import com.rabbitmq.client.Channel;
import cqrsdemo.events.ProductAddedEvent;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
@ProcessingGroup("amqpEvents")
public class PcfSpringBootApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PcfSpringBootApplication.class);

    @Value("${axon.amqp.exchange:CatalogEvents}")
    String exchangeName;

    public static void main(String[] args) {
        SpringApplication.run(PcfSpringBootApplication.class, args);
        LOG.info("Starting the QUERY-SIDE PCF Axon CQRS Demo with SpringBoot.");
    }

    @Bean
    public SpringAMQPMessageSource complaintEvents(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

            @RabbitListener(queues = "CatalogEvents")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                LOG.info("I Heard: {}", message.getBody().toString());
                super.onMessage(message, channel);
            }
        };
    }

    @EventHandler
    public void on(ProductAddedEvent productAddedEvent) {
        LOG.info("A product was added! {}", productAddedEvent);
    }

    @RefreshScope
    @Controller
    class MainController {

        @GetMapping("/")
        public String index() {
            LOG.info("A request has been received for the Index page.");
            return "index";
        }

        @GetMapping("/dash")
        public String dash() {
            LOG.info("A request has been received for the Application Dashboard page.");
            return "dash";
        }

        @GetMapping("/stb")
        public String stb() {
            LOG.error("Had a crisis...   :(   ", new OutOfMemoryError("Threw a fake OutOfMemory error!!!"));
            System.exit(-1);
            return "index";
        }
    }


    /**
     * The @RestController annotation tells Spring to render the resulting string directly back to the caller.
     *
     * @return
     */
    @RefreshScope
    @RestController
    class TestRestController {

        @Value("${spring.application.name}")
        String appName;

        @Value("${your.host.is:Ben}")
        String hostName;

        /**
         * The @RequestMapping annotation provides “routing” information.
         *
         * @return
         */
        @RequestMapping("/rest")
        public Map<String, String> get() {
            LOG.info("A request has been received for the /rest endpoint.");
            Map<String, String> data = new HashMap<String, String>();
            data.put("applicationName", appName);
            data.put("yourHostIs", hostName);
            LOG.debug("Returning {}.", data.toString());
            return data;
        }
    }
}
