package com.example.recipes.persistence;

import com.example.recipes.businesslayer.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(value = "select r.id, " +
            "r.name, " +
            "r.category, " +
            "r.date, " +
            "r.description, " +
            "string_agg(i.ingredients, ', '), " +
            "string_agg(d.directions, ', ') " +
            "from recipes r " +
            "join ingredients i on r.id = i.recipe_id " +
            "join directions d on r.id = d.recipe_id " +
            "where r.category ilike :category " +
            "group by r.id " +
            "order by r.date desc;",
    nativeQuery = true)
    List<Recipe> searchByCategory(@Param("category") String category);

    @Query(value = "select r.id, " +
            "r.name, " +
            "r.category, " +
            "r.date, " +
            "r.description, " +
            "string_agg(i.ingredients, ', '), " +
            "string_agg(d.directions, ', ') " +
            "from recipes r " +
            "join ingredients i on r.id = i.recipe_id " +
            "join directions d on r.id = d.recipe_id " +
            "where r.name ilike %:name% " +
            "group by r.id " +
            "order by r.date desc;",
            nativeQuery = true)
    List<Recipe> searchByName(@Param("name") String name);
}
