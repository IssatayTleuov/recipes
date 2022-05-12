package com.example.recipes.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @NotBlank
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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private User user;
}
