package org.example.university1.service;

import org.example.university1.pojo.UniversityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Value("${university.url}")
    private String url;
    private final RestTemplate restTemplate;
    private final ExecutorService threadPool;

    @Autowired
    public UniversityServiceImpl(RestTemplate restTemplate, ExecutorService threadPool) {
        this.restTemplate = restTemplate;
        this.threadPool = threadPool;
    }

    @Override
    public UniversityDTO[] getAll() {
        return restTemplate.getForObject(url, UniversityDTO[].class);
    }

    @Override
    public List<UniversityDTO> getAllByCountries(List<String> countries) {
        List<CompletableFuture<UniversityDTO[]>> futures = new ArrayList<>();
        for(String country: countries) {
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> restTemplate.getForObject(url + "?country=" + country, UniversityDTO[].class),
                            threadPool
                    )
            );
        }
        List<UniversityDTO> res = new ArrayList<>();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenAccept(VOID -> futures.stream().map(CompletableFuture::join).forEach(arr -> res.addAll(Arrays.asList(arr))))
                .join();
        return res;
    }
}
