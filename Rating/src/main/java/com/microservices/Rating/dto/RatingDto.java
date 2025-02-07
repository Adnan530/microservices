package com.microservices.Rating.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RatingDto {

    private Long id;

    private Long userId;

    private Integer rating;

    private String feedback;

}
