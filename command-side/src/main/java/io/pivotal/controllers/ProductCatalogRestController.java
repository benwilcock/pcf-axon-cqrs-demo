package io.pivotal.controllers;

import io.pivotal.commands.AddProductToCatalog;
import io.pivotal.services.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
public class ProductCatalogRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCatalogRestController.class);

    private final CatalogService catalogService;

    @Autowired
    public ProductCatalogRestController(CatalogService commandGateway) {
        this.catalogService = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addProductToCatalog(@RequestBody Map<String, String> request) {

        LOG.info("Adding Product to Catalog: {}, {}", request.get("id"), request.get("name"));

        AddProductToCatalog addProductToCatalogCommand = new AddProductToCatalog(
                request.get("id"),
                request.get("name")
        );

        return catalogService.addProductToCatalog(addProductToCatalogCommand);
    }
}

