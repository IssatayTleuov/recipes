package com.example.recipes.persistence;

import com.example.recipes.businesslayer.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(value = "SELECT r.name, " +
            "r.category, " +
            "r.date, " +
            "r.description, " +
            "r.ingredients, " +
            "r.directions FROM Recipe r WHERE r.category = :category")
    List<Recipe> searchByCategory(@Param("category") String category);

    @Query(value = "SELECT r.name, " +
            "r.category, " +
            "r.date, " +
            "r.description, " +
            "r.ingredients, " +
            "r.directions FROM Recipe r WHERE r.category = :name")
    List<Recipe> searchByName(@Param("name") String name);
}
