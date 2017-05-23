package io.pivotal;

import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.commands.AddProductToCatalog;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

//@EnableDiscoveryClient
@SpringBootApplication
public class PcfSpringBootApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PcfSpringBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PcfSpringBootApplication.class, args);
        LOG.info("Starting the COMMAND-SIDE PCF Axon CQRS Demo with SpringBoot.");
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

    @RestController
    static class ProductCatalogueAPI {

        private final CommandGateway commandGateway;

        @Autowired
        public ProductCatalogueAPI(CommandGateway commandGateway) {
            this.commandGateway = commandGateway;
        }

        @PostMapping("/add")
        public CompletableFuture<String> addProductToCatalogue(@RequestBody Map<String, String> request) {

            LOG.info("Adding Product to Catalog: {}, {}", request.get("id"), request.get("name"));

            AddProductToCatalog addProductToCatalogCommand = new AddProductToCatalog(
                    request.get("id"),
                    request.get("name")
            );

            return this.commandGateway.send(addProductToCatalogCommand);
        }
    }

    @Aggregate
    public static class Product {

        @AggregateIdentifier
        String id;
        String name;

        public Product() {
        }

        @CommandHandler
        public Product(AddProductToCatalog cmd) {
            LOG.debug("Handling {} command: {}, {}", cmd.getClass().getSimpleName(), cmd.getId(), cmd.getName());
            Assert.hasLength(cmd.getId(), "ID should NOT be blank.");
            Assert.hasLength(cmd.getName(), "Name should NOT be blank.");
            apply(new ProductAddedEvent(cmd.getId(), cmd.getName()));
            LOG.trace("Done handling {} command: {}, {}", cmd.getClass().getSimpleName(), cmd.getId(), cmd.getName());
        }

        @EventSourcingHandler
        public void on(ProductAddedEvent evnt) {
            LOG.debug("Handling {} event: {}, {}", evnt.getClass().getSimpleName(), evnt.getId(), evnt.getName());
            this.id = evnt.getId();
            this.name = evnt.getName();
            LOG.trace("Done handling {} event: {}, {}", evnt.getClass().getSimpleName(), evnt.getId(), evnt.getName());
        }
    }
}
