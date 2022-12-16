package test6.test6.BusienessLayer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.PersistenceLayer.RecipeRepository;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class RecipeService {
    @Autowired private final RecipeRepository repository;

    public Recipe saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return repository.save(recipe);
    }

    public Optional<Recipe> findRecipeByID (int id) {
        return repository.findById(id);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
    }

    public List<Recipe> findRecipeByCategory(String category) {
        List<Recipe> recipes = repository.findByCategoryIgnoreCase(category);
        Collections.sort(recipes, Collections.reverseOrder());
        return recipes;
    }

    public List<Recipe> findRecipeByNameContaining(String name) {
        List<Recipe> recipes = repository.findByNameContainingIgnoreCase(name);
        Collections.sort(recipes, Collections.reverseOrder());
        return recipes;
    }

    public void updateRecipeByID(int id, Recipe newRecipe) {
        Recipe recipe = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipe.setCategory(newRecipe.getCategory());
        recipe.setDescription(newRecipe.getDescription());
        recipe.setName(newRecipe.getName());
        recipe.setDirections(newRecipe.getDirections());
        recipe.setCategory(newRecipe.getCategory());
        recipe.setIngredients(newRecipe.getIngredients());
        recipe.setDate(LocalDateTime.now());
        repository.save(recipe);
    }
}
