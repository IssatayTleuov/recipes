package com.example.recipes.presentation;

import com.example.recipes.businesslayer.Recipe;
import com.example.recipes.businesslayer.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(@Autowired RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public void createRecipe(@Valid @RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getRecipe(@PathVariable long id) {
        return recipeService.findRecipe(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable long id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}