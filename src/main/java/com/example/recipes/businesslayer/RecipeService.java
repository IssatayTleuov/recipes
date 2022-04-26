package com.example.recipes.businesslayer;

import com.example.recipes.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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

    public Optional<Map<String, Object>> findRecipe(long id) {
        Optional<Recipe> recipeWithId = recipeRepository.findById(id);
        Map<String, Object> recipeWithOutId = new HashMap<>();
        if (recipeWithId.isPresent()) {
            recipeWithOutId.put("name", recipeWithId.get().getName());
            recipeWithOutId.put("description", recipeWithId.get().getDescription());
            recipeWithOutId.put("ingredients", recipeWithId.get().getIngredients());
            recipeWithOutId.put("directions", recipeWithId.get().getDirections());
            return Optional.of(recipeWithOutId);
        } else {
            return Optional.empty();
        }
    }

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }
}
