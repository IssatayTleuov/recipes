package com.example.recipes.businesslayer;

import com.example.recipes.persistence.RecipeRepository;
import com.example.recipes.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(@Autowired RecipeRepository recipeRepository, @Autowired UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public RecipeId createRecipe(Recipe recipe, UserDetails userDetails) {
        User currentUser = userRepository.findUserByEmail(userDetails.getUsername());
        recipe.setUser(currentUser);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new RecipeId(savedRecipe.getId());
    }

    public Optional<Recipe> findRecipe(long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipe(long id, UserDetails userDetails) throws Exception {
        Optional<Recipe> currentRecipe = recipeRepository.findById(id);
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        if (currentRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (currentRecipe.get().getUser().getId() == user.getId()) {
            recipeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public void updateRecipe(long id, Recipe recipe, UserDetails userDetails) throws Exception {
        Optional<Recipe> currentRecipe = recipeRepository.findById(id);
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        if (currentRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (currentRecipe.get().getUser().getId() == user.getId()) {
            currentRecipe.ifPresent(updateRecipe -> {
                updateRecipe.setName(recipe.getName());
                updateRecipe.setCategory(recipe.getCategory());
                updateRecipe.setDate(LocalDateTime.now());
                updateRecipe.setDescription(recipe.getDescription());
                updateRecipe.setIngredients(recipe.getIngredients());
                updateRecipe.setDirections(recipe.getDirections());
                recipeRepository.save(updateRecipe);
            });
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public Optional<List<Recipe>> searchByCategory(String category) {
        List<Recipe> recipeList = recipeRepository.searchByCategory(category);
        return Optional.ofNullable(recipeList);
    }

    public Optional<List<Recipe>> searchByName(String name) {
        List<Recipe> recipeList = recipeRepository.searchByName(name);
        return Optional.ofNullable(recipeList);
    }

    @Deprecated
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
