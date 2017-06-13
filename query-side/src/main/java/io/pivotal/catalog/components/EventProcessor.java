package io.pivotal.catalog.components;

import cqrsdemo.events.ProductAddedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("amqpEvents")  // hook up this class as an event processor
public class EventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    @EventHandler // Mark this method as an event handler
    public void on(ProductAddedEvent productAddedEvent) {
        LOG.info("A product was added! Id={} Name={}", productAddedEvent.getId(), productAddedEvent.getName());
    }
}
