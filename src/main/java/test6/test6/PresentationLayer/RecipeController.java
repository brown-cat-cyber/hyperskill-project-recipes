package test6.test6.PresentationLayer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.BusienessLayer.Recipe;
import test6.test6.BusienessLayer.RecipeDto;
import test6.test6.BusienessLayer.RecipeService;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @PostMapping("/api/recipe/new")
    public JSONResponse postRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        JSONResponse response = new JSONResponse(recipeService.saveRecipe(recipeDto).getId());
        return response;
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        Optional<RecipeDto> recipeDtoOptional = recipeService.findRecipeByID(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return recipe;
    }

    @GetMapping("/api/recipe/search")
    // returned String for test
    public List<Recipe> searchRecipe(@RequestParam Map<String, String> allParams) {
        if (allParams.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            if (allParams.containsKey("category")) {
                return recipeService.findRecipeByCategory(allParams.get("category"));
            } else if (allParams.containsKey("name")) {
                return recipeService.findRecipeByNameContaining(allParams.get("name"));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe newRecipe)  {
        recipeService.updateRecipeByID(id, newRecipe);
    }

    @DeleteMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable int id ) {
        recipeService.findRecipeByID(id).ifPresentOrElse(
                (recipe) -> recipeService.deleteRecipeByID(recipe.getId()),
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
        );
    }


}
