package io.pivotal;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestControllerWillAddProductToCatalog {

    private static final Logger LOG = LoggerFactory.getLogger(TestControllerWillAddProductToCatalog.class);

    private String id;
    private String name;
    private ProductCatalogRestController api;

    @Mock
    private CommandGateway commandGateway;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        api = new ProductCatalogRestController(commandGateway);

        id = UUID.randomUUID().toString();
        name = "test-" + id;
    }


    @Test
    public void testApi() throws Exception {
        when(commandGateway.send(any())).thenReturn(CompletableFuture.supplyAsync(this::getId));

        //Act
        HashMap<String, String> map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        CompletableFuture<String> response = api.addProductToCatalog(map);

        //Assert
        verify(commandGateway).send(any());
        assertEquals(id, response.get().toString());
    }

    public String getId() {
        return id;
    }

}
