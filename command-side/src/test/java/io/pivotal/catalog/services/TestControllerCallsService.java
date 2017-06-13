package io.pivotal.catalog.services;

import io.pivotal.catalog.api.CatalogApi;
import io.pivotal.catalog.commands.AddProductToCatalogCommand;
import io.pivotal.catalog.controllers.CatalogApiController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestControllerCallsService {

    private static final Logger LOG = LoggerFactory.getLogger(TestControllerCallsService.class);

    private String id;
    private String name;
    private CatalogApi controller;
    private AddProductToCatalogCommand command;

    @Mock
    private CatalogService mockCatalogService;

    @Before
    public void init() {
        controller = new CatalogApiController(mockCatalogService);
        id = UUID.randomUUID().toString();
        name = "test-" + id;
    }


    @Test
    public void testApi() throws Exception {

        //Arrange
        when(mockCatalogService.addProductToCatalog(any(AddProductToCatalogCommand.class)))
                .thenReturn(CompletableFuture.supplyAsync(this::getId));

        //Act
        HashMap<String, String> map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        CompletableFuture<String> response = controller.addProductToCatalog(map);

        //Assert
        verify(mockCatalogService).addProductToCatalog(any(AddProductToCatalogCommand.class));
        assertEquals(id, response.get().toString());
    }

    public String getId() {
        return id;
    }

}
