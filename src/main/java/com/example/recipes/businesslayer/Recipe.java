package com.example.recipes.businesslayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @Column(name = "ingredients")
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<String> ingredients = new ArrayList<>();

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @Column(name = "directions")
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<String> directions = new ArrayList<>();
}
