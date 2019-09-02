package com.starwars.app.domain;

import java.io.Serializable;

/**
 * This class is intended to create a POJO that can adequately capture response from swapi API
 * into a well defined Java object using restTemplate.getForObject() method.
 */
public class SearchResults implements Serializable {

    private long count;
    private String next;
    private String previous;
    private String results;

//    HTTP 200 OK
//    Allow: GET, HEAD, OPTIONS
//    Content-Type: application/json
//    Vary: Accept
//
//    {
//        "count": 1,
//            "next": null,
//            "previous": null,
//            "results": [
//        {
//            "name": "R2-D2",
//                "height": "96",
//                "mass": "32",
//                "hair_color": "n/a",
//                "skin_color": "white, blue",
//                "eye_color": "red",
//                "birth_year": "33BBY",
//                "gender": "n/a",
//                "homeworld": "https://swapi.co/api/planets/8/",
//                "films": [
//            "https://swapi.co/api/films/2/",
//                    "https://swapi.co/api/films/5/",
//                    "https://swapi.co/api/films/4/",
//                    "https://swapi.co/api/films/6/",
//                    "https://swapi.co/api/films/3/",
//                    "https://swapi.co/api/films/1/",
//                    "https://swapi.co/api/films/7/"
//            ],
//            "species": [
//            "https://swapi.co/api/species/2/"
//            ],
//            "vehicles": [],
//            "starships": [],
//            "created": "2014-12-10T15:11:50.376000Z",
//                "edited": "2014-12-20T21:17:50.311000Z",
//                "url": "https://swapi.co/api/people/3/"
//        }
//    ]
//    }

}
