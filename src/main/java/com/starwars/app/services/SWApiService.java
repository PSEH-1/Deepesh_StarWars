package com.starwars.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("swapiService")
public class SWApiService implements IProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SWApiUrlBuilder urlBuilder;

    @Override
    public String fetch(String type, String name) {
        LOGGER.info("Received request to find information on: type: {}, name: {}", type, name);
        String apiResult = "";
        ResponseEntity<String> response = fetchAPIResponse(type, name);
        apiResult = parseTopicJSON(type, name, apiResult, response);
        LOGGER.info("Response from SWAPI: {}", apiResult);

        return apiResult;
    }

    private String parseTopicJSON(String type, String name, String apiResult, ResponseEntity<String> response) {
        ObjectMapper mapper = new ObjectMapper();
        int count;
        List<String> films = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            count = root.path("count").asInt();
            JsonNode results = root.path("results");
            
            for (int i = 0; i < results.size(); i++) {
                JsonNode result = results.get(i);
                if (result.path("name").asText().equalsIgnoreCase(name)) {
                    JsonNode n = result.path("films");
                    if(n.isArray()) {
                    	for (final JsonNode objNode : n) {
                            films.add(objNode.textValue());
                        }
                    }
                }
            }
            JsonNode rootNode = mapper.createObjectNode();
            ((ObjectNode)rootNode).put("Type", type);
            ((ObjectNode)rootNode).put("Count", count);
            ((ObjectNode)rootNode).put("Name", name);
            ArrayNode arrayNode = (ArrayNode) rootNode.withArray("Films");
            for (String item : films) {
                arrayNode.add(item);
            }
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            return jsonString;
        } catch (IOException e) {
            LOGGER.error("Encountered an exception while querying for the Star Wars topic! " + e.getMessage());
        }
        
        
        return apiResult;
    }

    @Retryable(
            value = {RestClientException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 5000))
    private ResponseEntity<String> fetchAPIResponse(String type, String name) {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        String apiUrl = urlBuilder.buildApiUrl(type, name);
        LOGGER.info("URL is {}", apiUrl);
        return restTemplate.getForEntity(apiUrl, String.class);
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

}
