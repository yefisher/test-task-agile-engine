package com.efisher.testtaskagileengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
 * Class that implements API token retrieval and updating.
 *
 * */

@Component
public class ScheduledTokenRenewal {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTokenRenewal.class);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static String token;

    public final RestTemplate restTemplate;

    @Autowired
    public ScheduledTokenRenewal(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * Updating API token once per day.
     *
     * */
    @Scheduled(fixedDelay = 86_400_000)
    public void getToken() {
        LOG.info(String.format("%s. Updating API token...", SIMPLE_DATE_FORMAT.format(new Date())));
        final String URI = "http://interview.agileengine.com/auth";

        Map<String, String> params = new HashMap<>();
        Properties props = null;
        try {
            props = PropertiesUtil.getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        params.put("apiKey", props.getProperty("apiKey"));

        String tokenString = restTemplate.postForObject(URI, params, String.class);

        if (tokenString == null || tokenString.isEmpty()) {
            LOG.info("Something went wrong while authorization to resource. Please, check an api key.");
            throw new RuntimeException("Failed to retrieve token string.");
        }

        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        try {
            map = mapper.readValue(tokenString, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOG.info("API token has been updated.");
        token = (String) map.get("token");
    }

}
