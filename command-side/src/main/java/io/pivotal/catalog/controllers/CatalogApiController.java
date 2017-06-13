package io.pivotal.catalog.controllers;

import io.pivotal.catalog.api.CatalogApi;
import io.pivotal.catalog.commands.AddProductToCatalogCommand;
import io.pivotal.catalog.services.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
public class CatalogApiController implements CatalogApi {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogApiController.class);

    private final CatalogService catalogService;

    @Autowired
    public CatalogApiController(CatalogService commandGateway) {
        this.catalogService = commandGateway;
    }

    @Override
    @PostMapping("/add")
    public CompletableFuture<String> addProductToCatalog(@RequestBody Map<String, String> request) {

        LOG.info("Adding Product to Catalog: {}, {}", request.get("id"), request.get("name"));

        return catalogService.addProductToCatalog(new AddProductToCatalogCommand(
                request.get("id"),
                request.get("name")
        ));
    }
}

