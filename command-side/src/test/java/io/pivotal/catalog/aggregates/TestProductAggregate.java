package io.pivotal.catalog.aggregates;

import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.catalog.commands.AddProductToCatalogCommand;
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
                .when(new AddProductToCatalogCommand(id, name))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductAddedEvent(id, name));
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenIdNull() {
        fixture.given()
                .when(new AddProductToCatalogCommand(null, name))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalogCommand("", name))
                .expectException(IllegalArgumentException.class);
    }

    @Test
    public void testCommandAddProductToCatalogFailsWhenNameNull() {
        fixture.given()
                .when(new AddProductToCatalogCommand(id, null))
                .expectException(IllegalArgumentException.class);

        fixture.given()
                .when(new AddProductToCatalogCommand(id, ""))
                .expectException(IllegalArgumentException.class);
    }


}
