package test6.test6.BusienessLayer;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.PersistenceLayer.RecipeRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
public class RecipeService {
    @Autowired private RecipeRepository repository;
    @Autowired  private ModelMapper modelMapper;





    public RecipeDto toRecipeDTO(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        return recipeDto;
    }

    public Recipe toRecipeEntity(RecipeDto dto) {
        Recipe recipe = modelMapper.map(dto, Recipe.class);
        recipe.setDate(LocalDateTime.now());
        return recipe;
    }

    public void toRecipeEntity(Recipe recipe, RecipeDto dto) {
        recipe.setCategory(dto.getCategory());
        recipe.setDescription(dto.getDescription());
        recipe.setName(dto.getName());
        recipe.setDirections(dto.getDirections());
        recipe.setCategory(dto.getCategory());
        recipe.setIngredients(dto.getIngredients());
        recipe.setDate(LocalDateTime.now());
    }



    public Recipe saveRecipe(@Valid RecipeDto recipeDto, User user) {
        Recipe recipe = toRecipeEntity(recipeDto);
        recipe.setUser(user);
        return repository.save(recipe);
    }

    public RecipeDto findRecipeByID (int id) {
        Optional<Recipe> recipeOptional = repository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toRecipeDTO(recipe);
    }


    public List<RecipeDto> findRecipeByCategory(String category) {
        List<Recipe> recipes = repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        List<RecipeDto> recipeDtos = recipes.stream().map((recipe -> toRecipeDTO(recipe))).collect(Collectors.toList());
        return recipeDtos;
    }

    public List<RecipeDto> findRecipeByNameContaining(String name) {
        List<Recipe> recipes = repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        List<RecipeDto> recipeDtos =  recipes.stream().map((recipe -> toRecipeDTO(recipe))).collect(Collectors.toList());
        return recipeDtos;
    }

    public void updateRecipeByID(int id, RecipeDto newRecipeDto, UserDetails userDetails) {
        Optional<Recipe> recipeOptional = repository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = recipe.getUser();
        if (user.getEmail() != userDetails.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        toRecipeEntity(recipe,newRecipeDto);
        recipe.setUser(user);
        repository.save(recipe);
    }

    public void deleteRecipeByID(int id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Recipe recipe = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = recipe.getUser();
        if (user.getEmail() != userDetails.getUsername()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        repository.deleteById(id);
    }

}
