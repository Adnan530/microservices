package com.microservices.Rating.controller;

import com.microservices.Rating.entity.Rating;
import com.microservices.Rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService=ratingService;
    }

    //create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    //get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRating());
    }

    //getRatingByUserId
    @GetMapping("users/{userId}")
    public  ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingsByUserId(userId));
    }


}
