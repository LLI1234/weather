package com.example.search.service;

import org.example.university1.controller.UniversityController;
import org.example.university1.pojo.UniversityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SearchServiceImpl implements SearchService {

    private final RestTemplate restTemplate;
    private final UniversityController universityController;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate, UniversityController universityController) {
        this.restTemplate = restTemplate;
        this.universityController = universityController;
    }

    @Override
    @HysterixCommand(fallbackMethod = "default")
    public ResponseEntity<?> getDetails() {
        CompletableFuture<ResponseEntity<UniversityDTO[]>> universityFuture = CompletableFuture.supplyAsync(() -> universityController.getAll());
        CompletableFuture<String> detailsFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://details/port", String.class));

        CompletableFuture.allOf(universityFuture, detailsFuture).join();

        // I'm confused as to how we're merging data from the /details/port call with a previous homework service (university)
        // /details/port only returns the details of the port we're using, and the general response in the homework.txt is asking for code, timestamp, and data
        Map<String, Object> mergedData = new HashMap<>();
        try {
            mergedData.put("universities", universityFuture.get());
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
        try {
            mergedData.put("detailsPort", detailsFuture.get());
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }

        return mergedData;
    }

    @Override
    public ResonseEntity<?> default() {
        return
    }

}
