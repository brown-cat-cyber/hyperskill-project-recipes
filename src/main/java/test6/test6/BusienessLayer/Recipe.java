package test6.test6.BusienessLayer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Comparable<Recipe> {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank(message = "Name shouldn't be null")
    private String name;

    @NotBlank(message = "Description shouldn't be null")
    private String description;

    @NotNull(message = "Ingredients shouldn't be null")
    @Size(min = 1, message = "size must greater than or equal 1")
    @ElementCollection
    private List<String> ingredients;

    @NotNull(message = "Directions shouldn't be null")
    @Size(min = 1, message = "size must greater than or equal 1")
    @ElementCollection
    private List<String> directions;

    @NotBlank(message = "category shouldn't be null")
    private String category;

    private LocalDateTime localDateTime;

    @PrePersist
    private void setLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
    }

    @PreUpdate void updateLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
    }

    public Recipe(String name, String description, List ingredients, List directions, String category) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
    }

    @Override
    public int compareTo(Recipe otherRecipe) {
        return this.localDateTime.compareTo(otherRecipe.localDateTime);
    }
}
