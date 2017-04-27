package io.pivotal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class PcfSpringBootApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PcfSpringBootApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(PcfSpringBootApplication.class, args);
        LOG.info("Starting the PCF SpringBoot Demo with Thymeleaf.");
    }

    @Controller
    class MainController {

        @GetMapping("/")
        public String index() {
            LOG.info("A request has been received for the Index page.");
            return "index";
        }
    }

    @RefreshScope
    @org.springframework.web.bind.annotation.RestController
    class RestController {

        @Value("${spring.application.name}")
        String appName;

        @Value("${your.host.is:Ben}")
        String hostName;

        @GetMapping("/rest")
        public Map<String, String> get() {
            LOG.info("A request has been received for the /rest endpoint.");
            Map<String, String> data = new HashMap<String, String>();
            data.put("applicationName", appName);
            data.put("yourHostIs", hostName);
            LOG.debug("Returning {}.", data.toString());
            return data;
        }
    }

    @Controller
    class StbController {

        @GetMapping("/stb")
        public String stb() {
            LOG.error("Had a crisis...   :(   ", new OutOfMemoryError("Threw a fake OutOfMemory error!!!"));
            System.exit(-1);
            return "index";
        }
    }
}
