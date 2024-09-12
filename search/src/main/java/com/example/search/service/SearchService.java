package com.example.search.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    @HystrixCommand(fallBackMethod = "default");
    public ResponseEntity<?> getDetails();
    public ResponseEntity<?> default();
}
