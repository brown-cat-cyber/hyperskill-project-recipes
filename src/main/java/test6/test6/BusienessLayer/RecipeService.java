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
import java.util.stream.Collectors;

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
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        return recipeDto;
    }

    public static Recipe toEntity(RecipeDto dto) {
        Recipe recipe = modelMapper.map(dto, Recipe.class);
        recipe.setDate(LocalDateTime.now());
        return recipe;
    }

    public Recipe saveRecipe(@Valid RecipeDto recipeDto) {
        Recipe recipe = toEntity(recipeDto);
        return repository.save(recipe);
    }

    public RecipeDto findRecipeByID (int id) {
        Optional<Recipe> recipeOptional = repository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDTO(recipe);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
    }

    public List<RecipeDto> findRecipeByCategory(String category) {
        List<Recipe> recipes = repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        List<RecipeDto> recipeDtos = recipes.stream().map((recipe -> toDTO(recipe))).collect(Collectors.toList());
        return recipeDtos;
    }

    public List<RecipeDto> findRecipeByNameContaining(String name) {
        List<Recipe> recipes = repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        List<RecipeDto> recipeDtos =  recipes.stream().map((recipe -> toDTO(recipe))).collect(Collectors.toList());
        return recipeDtos;
    }

    public void updateRecipeByID(int id, RecipeDto newRecipe) {
        Recipe recipe = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipe = toEntity(newRecipe);
        repository.save(recipe);
    }
}
