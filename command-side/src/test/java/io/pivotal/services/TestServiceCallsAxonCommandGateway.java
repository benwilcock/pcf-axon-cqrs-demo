package io.pivotal.services;

import io.pivotal.commands.AddProductToCatalog;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestServiceCallsAxonCommandGateway {

    private static final Logger LOG = LoggerFactory.getLogger(TestServiceCallsAxonCommandGateway.class);

    private String id;
    private String name;
    private AddProductToCatalog command;

    @MockBean
    private CommandGateway commandGateway;

    private CatalogService service;


    @Before
    public void init() {
        id = UUID.randomUUID().toString();
        name = "test-" + id;
        command = new AddProductToCatalog(id, name);
        service = new CatalogService(commandGateway);
    }

    @Test
    public void testApi()throws Exception {
        //Arrange
        when(commandGateway.send(any())).thenReturn(CompletableFuture.supplyAsync(this::getId));

        //Act
        CompletableFuture<String> response = service.addProductToCatalog(command);

        //Assert
        verify(commandGateway).send(any());
        assertEquals(id, response.get().toString());
    }

    public String getId() {
        return id;
    }

}
