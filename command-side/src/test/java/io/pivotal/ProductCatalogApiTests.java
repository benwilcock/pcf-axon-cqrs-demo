package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcfSpringBootApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCatalogApiTests {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCatalogApiTests.class);

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String id;
    private String addProductData;

    @Before
    public void init(){
        id = UUID.randomUUID().toString();
        addProductData = "{\"id\":\""+id+"\",\"name\":\"test-"+id+"\"}";
       LOG.info("Add Product Message is: {}", addProductData);
    }

    @Test
    public void testRestEndpoint(){
        String body = restTemplate.getForObject("/rest", String.class);
        assertThat(body).isEqualTo("{\"yourHostIs\":\"Unknown\",\"applicationName\":\"pcf-axon-cqrs-demo-command-side\"}");
        System.out.print(body);
    }

    @Test
    public void addProductToCatalogueTest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        HttpEntity<String> entity = new HttpEntity<String>(addProductData, headers);
        ResponseEntity<String> response = restTemplate.exchange("/add", HttpMethod.POST,entity,String.class);
        LOG.info("Response Body: {}", response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).isNotBlank();
    }
}
