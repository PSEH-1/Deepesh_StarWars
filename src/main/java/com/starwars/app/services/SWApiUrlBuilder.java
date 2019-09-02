package com.starwars.app.services;

import org.springframework.stereotype.Component;

@Component
public class SWApiUrlBuilder {
	public static final String API_BASE_URL = "https://swapi.co/api";

    public String buildApiUrl(String type, String name) {
        return new StringBuffer(API_BASE_URL)
                .append("/")
                .append(type.toLowerCase())
                .append("/?search=")
                .append(name.toLowerCase())
                .toString();
    }

}
