package io.pivotal.services;

import io.pivotal.commands.AddProductToCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CatalogService {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogService.class);

    private final CommandGateway commandGateway;

    @Autowired
    public CatalogService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> addProductToCatalog(AddProductToCatalog command){
        LOG.debug("Processing AddProductToCatalog command id={} name={}.", command.getId(), command.getName());
        return this.commandGateway.send(command);
    }
}
