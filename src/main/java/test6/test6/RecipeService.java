package test6.test6;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class RecipeService {
    @Autowired private final RecipeDaoRepository repository;

    public RecipeDAO saveRecipe(Recipe recipe) {
        RecipeDAO recipeDAO = new RecipeDAO(recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections());
        return repository.save(recipeDAO);
    }

    public Optional<RecipeDAO> findRecipeByID (int id) {
        return repository.findById(id);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
    }
}
