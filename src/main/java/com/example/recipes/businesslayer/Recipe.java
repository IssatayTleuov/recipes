package com.example.recipes.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Size(min = 1)
    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients = new ArrayList<>();

    @NotBlank
    @Size(min = 1)
    @OneToMany
    @JoinColumn(name = "recipe_id")
    private List<Direction> directions = new ArrayList<>();

    @Transient
    private long recipeId = 0;

    public void increment() {
        recipeId++;
    }
}
