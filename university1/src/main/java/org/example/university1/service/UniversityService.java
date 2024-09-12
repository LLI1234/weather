package org.example.university1.service;

import org.example.university1.pojo.UniversityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UniversityService {
    UniversityDTO[] getAll();
    List<UniversityDTO> getAllByCountries(List<String> countries);
}
