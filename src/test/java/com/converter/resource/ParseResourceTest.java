package com.converter.resource;

import com.converter.config.JerseyConfig;
import com.converter.model.DocumentResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ JerseyConfig.class })
class ParseResourceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void parseHtml() {
        String url = "http://localhost:" + randomServerPort;
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/parse/html")
                .queryParam("path", "https://www.lawinsider.com/contracts/bAIq6013Isi")
                .build()
                .toUri();
        ResponseEntity<DocumentResponse> forEntity = restTemplate.getForEntity(uri, DocumentResponse.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertNotNull(forEntity.getBody());
    }
}