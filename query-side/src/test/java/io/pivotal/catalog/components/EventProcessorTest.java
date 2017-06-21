package io.pivotal.catalog.components;


import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.catalog.entities.Product;
import io.pivotal.catalog.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class EventProcessorTest {

    @MockBean
    ProductRepository repo;

    ProductAddedEvent event;
    EventProcessor processor;

    String uuid;
    String name;

    @Before
    public void init(){
        uuid = UUID.randomUUID().toString();
        name = "test-"+uuid;
        event = new ProductAddedEvent(uuid, name);
        processor = new EventProcessor(repo);
    }

    @Test
    public void testOn(){
        when(repo.save(any(Product.class)))
                .thenAnswer(i -> {
                    Product prod = i.getArgumentAt(0, Product.class);
                    return prod;
                });

        Product myProduct = processor.on(event);
        verify(repo, atLeastOnce()).save(any(Product.class));
        verify(repo, atMost(1)).save(any(Product.class));
        verifyNoMoreInteractions(repo);

        Assert.assertEquals(myProduct.getId(), uuid);
        Assert.assertEquals(myProduct.getName(), name);

    }
}
