package org.example.university1.controller;

import org.example.university1.pojo.UniversityDTO;
import org.example.university1.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "university")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping(params = "countries")
    public ResponseEntity<List<UniversityDTO>> getAllCountries(@RequestParam("countries") List<String> countries) {
        return new ResponseEntity<>(universityService.getAllByCountries(countries), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<UniversityDTO[]> getAll() {
        return new ResponseEntity<>(universityService.getAll(), HttpStatus.OK);
    }
}
