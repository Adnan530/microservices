package com.microservices.Rating.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="name")
    private Long id;

    @Column(name ="user_id")
    private Long userId;

    @Column(name ="rating")
    private Integer rating;

    @Column(name ="feedback")
    private String feedback;
}
