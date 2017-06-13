package io.pivotal.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
public class PcfAxonCqrsCommandSideApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PcfAxonCqrsCommandSideApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PcfAxonCqrsCommandSideApplication.class, args);
        LOG.info("Starting the COMMAND-SIDE PCF Axon CQRS Demo using SpringBoot.");
    }

    @RefreshScope
    @Controller
    class DefaultController {

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

        @Value("${your.host.is:Aurora}")
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
