package com.microservices.Rating.service;

import com.microservices.Rating.entity.Rating;

import java.util.List;

public interface IRatingService {

    Rating create(Rating rating);

    //get All Ratings
    List<Rating> getAllRating();

    //get All Ratings by User ID

    List<Rating> getRatingsByUserId(Long userId);

}
