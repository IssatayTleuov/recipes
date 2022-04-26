package com.example.recipes.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "directions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direction_id")
    private long directionId;

    @Column(name = "direction")
    private String direction;
}
