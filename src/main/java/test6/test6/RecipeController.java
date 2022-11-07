package test6.test6;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @PostMapping("/api/recipe/new")
    public JSONResponse postRecipe(@Valid @RequestBody Recipe recipe) {
        JSONResponse response = new JSONResponse(recipeService.saveRecipe(recipe).id);
        return response;
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        Optional<RecipeDAO> recipeDAOOptional = recipeService.findRecipeByID(id);
        if (recipeDAOOptional.isPresent()) {
            RecipeDAO recipeDAO = recipeDAOOptional.get();
            Recipe recipe = new Recipe(recipeDAO.name, recipeDAO.description, recipeDAO.ingredients, recipeDAO.directions);
            return recipe;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.findRecipeByID(id).ifPresentOrElse(
                (recipeDAO) -> recipeService.deleteRecipeByID(recipeDAO.id),
                () -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
        );
    }


}
