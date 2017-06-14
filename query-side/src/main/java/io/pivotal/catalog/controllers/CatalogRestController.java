package io.pivotal.catalog.controllers;

import io.pivotal.catalog.components.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/catalog")
public class CatalogRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogRestController.class);

    private EventProcessor processor;

    public CatalogRestController(EventProcessor processor) {
        this.processor = processor;
    }

    @RequestMapping("/data")
    public Map<String, String> getData(){
        LOG.info("Request for the Catalog DATA ({})", processor.getDataMap().toString());
        return processor.getDataMap();
    }
}
