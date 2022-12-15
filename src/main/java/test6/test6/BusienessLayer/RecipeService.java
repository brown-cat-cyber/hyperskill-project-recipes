package test6.test6.BusienessLayer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test6.test6.PersistenceLayer.RecipeRepository;


import java.util.Optional;

@Service
@Data
public class RecipeService {
    @Autowired private final RecipeRepository repository;

    public Recipe saveRecipe(Recipe recipe) {
        return repository.save(recipe);
    }

    public Optional<Recipe> findRecipeByID (int id) {
        return repository.findById(id);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
    }
}
