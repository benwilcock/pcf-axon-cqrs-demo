package io.pivotal.catalog.components;

import cqrsdemo.events.ProductAddedEvent;
import io.pivotal.catalog.entities.Product;
import io.pivotal.catalog.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ProcessingGroup("amqpEvents")  // hook up this class as an event processor
public class EventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private final ProductRepository repo;

    public EventProcessor(ProductRepository repository) {
        this.repo = repository;
    }

    @EventHandler // Mark this method as an Axon Event Handler
    public void on(ProductAddedEvent productAddedEvent) {
        LOG.info("A product was added! Id={} Name={}", productAddedEvent.getId(), productAddedEvent.getName());
        repo.save(new Product(productAddedEvent.getId(), productAddedEvent.getName()));
    }
}
