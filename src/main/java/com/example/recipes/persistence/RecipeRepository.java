package com.example.recipes.persistence;

import com.example.recipes.businesslayer.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(value = "SELECT r.name, r.category, r.date, r.description, r.ingredients, r.directions FROM Recipe r WHERE r.category = ?1")
    List<Recipe> searchByCategory();
}
