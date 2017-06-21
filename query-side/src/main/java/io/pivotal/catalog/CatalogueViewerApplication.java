package io.pivotal.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableRedisRepositories
@EnableJpaRepositories
public class CatalogueViewerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogueViewerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CatalogueViewerApplication.class, args);
        LOG.info("Starting the QUERY-SIDE PCF Axon CQRS Demo [The Catalog Viewer] with SpringBoot.");
    }

    @RefreshScope
    @Controller
    class MainController {

        @GetMapping("/")
        public String getIndex() {
            LOG.info("A request has been received for the Index page.");
            return "dash";
        }

        @GetMapping("/view")
        public String getView() {
            LOG.info("A request has been received for the Index page.");
            return "view";
        }

        @GetMapping("/stb")
        public String crash() {
            LOG.error("Had a crisis...   :(   ", new OutOfMemoryError("Threw a fake OutOfMemory error!!!"));
            System.exit(-1);
            return "dash";
        }
    }
}
