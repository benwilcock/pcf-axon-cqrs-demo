package io.pivotal.catalog.components;

import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.catalog.entities.Product;
import io.pivotal.catalog.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("amqpEvents")  // hook up this class up to an Axon Event Processor
public class EventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private final ProductRepository repo;

    public EventProcessor(ProductRepository repository) {
        this.repo = repository;
    }

    @EventHandler // Mark this method as an Axon Event Handler
    public Product on(ProductAddedEvent productAddedEvent) {
        Product prod = repo.save(new Product(productAddedEvent.getId(), productAddedEvent.getName()));
        LOG.info("A product was added! Id={} Name={}", productAddedEvent.getId(), productAddedEvent.getName());
        return prod;
    }
}
