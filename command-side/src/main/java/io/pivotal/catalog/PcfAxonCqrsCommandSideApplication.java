package io.pivotal.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@EnableDiscoveryClient
@SpringBootApplication
public class PcfAxonCqrsCommandSideApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PcfAxonCqrsCommandSideApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PcfAxonCqrsCommandSideApplication.class, args);
        LOG.info("Starting the COMMAND-SIDE PCF Axon CQRS Demo using SpringBoot.");
    }

    @Controller
    class MainController {

        @GetMapping("/")
        public String index() {
            LOG.info("A request has been received for the Index page.");
            return "dash";
        }
    }
}
