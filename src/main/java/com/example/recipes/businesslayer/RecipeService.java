package com.example.recipes.businesslayer;

import com.example.recipes.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
            recipeWithOutId = removeRecipeId.apply(recipeWithId);
            return Optional.of(recipeWithOutId);
        } else {
            return Optional.empty();
        }
    }

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    public Optional<Map<String, Object>> updateRecipe(long id, Recipe recipe) {
        Optional<Recipe> currentRecipe = recipeRepository.findById(id);
        currentRecipe.ifPresent(updateRecipe -> {
            updateRecipe.setName(recipe.getName());
            updateRecipe.setCategory(recipe.getCategory());
            updateRecipe.setDate(LocalDateTime.now());
            updateRecipe.setDescription(recipe.getDescription());
            updateRecipe.setIngredients(recipe.getIngredients());
            updateRecipe.setDirections(recipe.getDirections());
            recipeRepository.save(updateRecipe);
        });
        return Optional.of(removeRecipeId.apply(recipeRepository.findById(id)));
    }

    public Optional<List<Recipe>> searchByCategory(String category) {
        List<Recipe> recipeList = recipeRepository.searchByCategory(category);
        return Optional.ofNullable(recipeList);
    }

    public Optional<List<Recipe>> searchByName(String name) {
        List<Recipe> recipeList = recipeRepository.searchByName(name);
        return Optional.ofNullable(recipeList);
    }

    Function<Optional<Recipe>, Map<String, Object>> removeRecipeId = (recipeWithId) -> {
        Map<String, Object> recipeWithOutId = new HashMap<>();
        recipeWithOutId.put("name", recipeWithId.get().getName());
        recipeWithOutId.put("category", recipeWithId.get().getCategory());
        recipeWithOutId.put("date", recipeWithId.get().getDate());
        recipeWithOutId.put("description", recipeWithId.get().getDescription());
        recipeWithOutId.put("ingredients", recipeWithId.get().getIngredients());
        recipeWithOutId.put("directions", recipeWithId.get().getDirections());
        return recipeWithOutId;
    };
}
