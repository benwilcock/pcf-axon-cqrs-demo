package io.pivotal;


import org.junit.Before;

import java.util.UUID;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = PcfSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITAddProductToCatalog {

    private String id;
    private String name;
    private String addProductJson;

    @Before
    public void init() {
        id = UUID.randomUUID().toString();
        name = "test-" + id;
        addProductJson = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
    }

//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
    //
//    @Test
//    public void testRestEndpoint() {
//        String body = restTemplate.getForObject("/rest", String.class);
//        assertThat(body).isEqualTo("{\"yourHostIs\":\"Unknown\",\"applicationName\":\"pcf-axon-cqrs-demo-command-side\"}");
//        System.out.print(body);
//    }
//
//    @Test
//    public void addProductToCatalogueTest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(contentType);
//        HttpEntity<String> entity = new HttpEntity<String>(addProductJson, headers);
//        ResponseEntity<String> response = restTemplate.exchange("/add", HttpMethod.POST, entity, String.class);
//        LOG.info("Response Body: {}", response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotEmpty();
//        assertThat(response.getBody()).isNotBlank();
//    }


}
