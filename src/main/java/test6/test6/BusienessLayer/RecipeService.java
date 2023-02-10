package test6.test6.BusienessLayer;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.PersistenceLayer.RecipeRepository;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class RecipeService {
    @Autowired private final RecipeRepository repository;
    @Autowired  private static final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    public static RecipeDto toDTO(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDto.class);
    }

    public static Recipe toEntity(RecipeDto dto) {
        return modelMapper.map(dto, Recipe.class);
    }

    public Recipe saveRecipe(@Valid RecipeDto recipeDto) {
        Recipe recipe = toEntity(recipeDto);
        recipe.setDate();
        return repository.save();
    }

    public Optional<Recipe> findRecipeByID (int id) {
        return repository.findById(id);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
    }

    public List<Recipe> findRecipeByCategory(String category) {
        List<Recipe> recipes = repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
         return recipes;
    }

    public List<Recipe> findRecipeByNameContaining(String name) {
        List<Recipe> recipes = repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        return recipes;
    }

    public void updateRecipeByID(int id, Recipe newRecipe) {
        Recipe recipe = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipe = toEntity(newRecipe);
        repository.save(recipe);
    }
}
