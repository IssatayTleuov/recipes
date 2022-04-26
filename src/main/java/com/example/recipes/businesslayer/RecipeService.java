package com.example.recipes.businesslayer;

import com.example.recipes.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(@Autowired RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeId createRecipe(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new RecipeId(savedRecipe.getId());
    }

    public Optional<Recipe> findRecipe(long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }
}
