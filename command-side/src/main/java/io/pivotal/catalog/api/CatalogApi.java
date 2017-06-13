package io.pivotal.catalog.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CatalogApi {
    @PostMapping("/add")
    CompletableFuture<String> addProductToCatalog(@RequestBody Map<String, String> request);
}
