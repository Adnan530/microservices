package com.microservices.Rating.service;

import com.microservices.Rating.entity.Rating;
import com.microservices.Rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements  IRatingService{

    private final RatingRepository ratingRepository;

    // Constructor injection for UserRepository
    @Autowired
    public  RatingService(RatingRepository ratingRepository){
        this.ratingRepository=ratingRepository;
    }

    @Override
    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
}
