package io.pivotal;

import io.pivotal.commands.AddProductToCatalog;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ProductCatalogRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCatalogRestController.class);

    private final CommandGateway commandGateway;

    @Autowired
    public ProductCatalogRestController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addProductToCatalog(@RequestBody Map<String, String> request) {

        LOG.info("Adding Product to Catalog: {}, {}", request.get("id"), request.get("name"));

        AddProductToCatalog addProductToCatalogCommand = new AddProductToCatalog(
                request.get("id"),
                request.get("name")
        );

        return this.commandGateway.send(addProductToCatalogCommand);
    }
}

