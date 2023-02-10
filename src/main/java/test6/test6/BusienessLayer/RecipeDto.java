package test6.test6.BusienessLayer;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link Recipe} entity
 */
@Data
public class RecipeDto implements Serializable {
    @NotBlank(message = "Name shouldn't be null")
    private final String name;
    @NotBlank(message = "Description shouldn't be null")
    private final String description;
    @NotNull(message = "Ingredients shouldn't be null")
    @Size(min = 1, message = "size must greater than or equal 1")
    private final List<String> ingredients;
    @NotNull(message = "Directions shouldn't be null")
    @Size(min = 1, message = "size must greater than or equal 1")
    private final List<String> directions;
    @NotBlank(message = "category shouldn't be null")
    private final String category;
    private final LocalDateTime date;
}