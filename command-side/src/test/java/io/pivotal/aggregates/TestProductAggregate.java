package io.pivotal.aggregates;

import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.commands.AddProductToCatalog;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TestProductAggregate {

    private FixtureConfiguration fixture;
    private String id;
    private String name;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(ProductAggregate.class);
        id = UUID.randomUUID().toString();
        name = "test-"+id;
    }


    @Test
    public void testCommandAddProductToCatalogPasses() {
        fixture.given()
                .when(new AddProductToCatalog(id, name))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductAddedEvent(id, name));
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenIdNull() {
        fixture.given()
                .when(new AddProductToCatalog(null, name))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalog("", name))
                .expectException(IllegalArgumentException.class);
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenNameNull() {
        fixture.given()
                .when(new AddProductToCatalog(id, null))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalog(id, ""))
                .expectException(IllegalArgumentException.class);
    }


}
