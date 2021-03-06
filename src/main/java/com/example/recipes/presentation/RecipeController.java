package com.example.recipes.presentation;

import com.example.recipes.businesslayer.Recipe;
import com.example.recipes.businesslayer.RecipeId;
import com.example.recipes.businesslayer.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(@Autowired RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public RecipeId createRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        return recipeService.createRecipe(recipe, userDetails);
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe =  recipeService.findRecipe(id);
        if (recipe.isPresent()) {
            return recipe;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            recipeService.deleteRecipe(id, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            if (e.getMessage().contains("403")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            recipeService.updateRecipe(id, recipe, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            if (e.getMessage().contains("403")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/search")
    public Optional<List<Recipe>> searchByParam(@RequestParam Map<String, String> allParams) {
        if (allParams.size() == 1 && allParams.containsKey("category") || allParams.containsKey("name")) {
            if (allParams.containsKey("category")) {
                return recipeService.searchByCategory(allParams.get("category"));
            } else if (allParams.containsKey("name")) {
                return recipeService.searchByName(allParams.get("name"));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Optional.of(Collections.emptyList());
    }
}