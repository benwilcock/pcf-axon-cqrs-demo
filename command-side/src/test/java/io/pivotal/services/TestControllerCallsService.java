package io.pivotal.services;

import io.pivotal.commands.AddProductToCatalog;
import io.pivotal.controllers.ProductCatalogRestController;
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
    private ProductCatalogRestController controller;
    private AddProductToCatalog command;

    @Mock
    private CatalogService mockCatalogService;

    @Before
    public void init() {
        controller = new ProductCatalogRestController(mockCatalogService);
        id = UUID.randomUUID().toString();
        name = "test-" + id;
    }


    @Test
    public void testApi() throws Exception {

        //Arrange
        when(mockCatalogService.addProductToCatalog(any(AddProductToCatalog.class)))
                .thenReturn(CompletableFuture.supplyAsync(this::getId));

        //Act
        HashMap<String, String> map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        CompletableFuture<String> response = controller.addProductToCatalog(map);

        //Assert
        verify(mockCatalogService).addProductToCatalog(any(AddProductToCatalog.class));
        assertEquals(id, response.get().toString());
    }

    public String getId() {
        return id;
    }

}
