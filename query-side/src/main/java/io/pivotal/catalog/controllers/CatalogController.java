package io.pivotal.catalog.controllers;


import io.pivotal.catalog.components.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

    private ConcurrentMap<String, String> data;

    @Autowired
    public CatalogController(EventProcessor processor) {
        this.data = processor.getDataMap();
    }

    @GetMapping("/view")
    public String getAll(Model model){
        LOG.info("Request for the Catalog VIEW");
        model.addAttribute("Id","Name");
        model.addAllAttributes(data);
        return "view";
    }
}
