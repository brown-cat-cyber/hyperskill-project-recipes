package test6.test6.BusienessLayer;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test6.test6.PersistenceLayer.RecipeRepository;
import test6.test6.PersistenceLayer.UserRepository;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class RecipeService {
    @Autowired private final RecipeRepository repository;
    @Autowired private final UserRepository userRepository;
    @Autowired  private final ModelMapper modelMapper = new ModelMapper();
    @Autowired private final PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public RecipeDto toRecipeDTO(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        return recipeDto;
    }

    public Recipe toRecipeEntity(RecipeDto dto) {
        Recipe recipe = modelMapper.map(dto, Recipe.class);
        recipe.setDate(LocalDateTime.now());
        return recipe;
    }

    public void User toUserEntity(UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

    }


    public void saveUser(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            userRepository.save(toUserEntity(userDto));
        }
    }

    public Recipe saveRecipe(@Valid RecipeDto recipeDto) {
        Recipe recipe = toRecipeEntity(recipeDto);
        return repository.save(recipe);
    }

    public RecipeDto findRecipeByID (int id) {
        Optional<Recipe> recipeOptional = repository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toRecipeDTO(recipe);
    }

    public void deleteRecipeByID(int id) {
        repository.deleteById(id);
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

    public void updateRecipeByID(int id, RecipeDto newRecipe) {
        Recipe recipe = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipe = toRecipeEntity(newRecipe);
        repository.save(recipe);
    }
}
