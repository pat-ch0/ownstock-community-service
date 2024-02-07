package org.aelion.community.communities.Impl;

import org.aelion.community.communities.Community;
import org.aelion.community.communities.CommunityRepository;
import org.aelion.community.communities.CommunityService;
import org.aelion.community.communities.dto.City;
import org.aelion.community.communities.dto.CommunityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    private final static String CITY_API = "http://localhost:4001/api/v1/";

    @Override
    public ResponseEntity<?> fetchCommunities() {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchById(String id) {
        Optional<Community> oCommunity = repository.findById(id);
        if (oCommunity.isPresent()) {
            String endpoint = CITY_API + "cities/" + oCommunity.get().getCityCode();
            City city = restTemplate.getForObject(endpoint, City.class);
            // Build a community response object
            CommunityResponse response = new CommunityResponse();
            response.setId(oCommunity.get().getId());
            response.setName(oCommunity.get().getName());
            response.setCity(city);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("No community was found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> createCommunity(Community community) {
        try {
            return new ResponseEntity<Community>(repository.save(community), HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>("Unable to save Community", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}