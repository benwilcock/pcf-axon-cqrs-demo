package io.pivotal.catalog.components;

import cqrsdemo.events.ProductAddedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ProcessingGroup("amqpEvents")  // hook up this class as an event processor
public class EventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private static final ConcurrentMap<String, String> data = new ConcurrentHashMap<String, String>();

    @EventHandler // Mark this method as an Axon Event Handler
    public void on(ProductAddedEvent productAddedEvent) {
        LOG.info("A product was added! Id={} Name={}", productAddedEvent.getId(), productAddedEvent.getName());
        data.putIfAbsent(productAddedEvent.getId(), productAddedEvent.getName());
    }

    public ConcurrentMap<String, String> getDataMap() {
        return data;
    }
}
