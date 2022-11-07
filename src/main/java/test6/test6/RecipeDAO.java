package test6.test6;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recipes_db")
@Data
public class RecipeDAO {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank
    @Column
    String name;
    @NotBlank
    @Column
    String description;
    @Size(min = 1)
    @Column
    String[] ingredients;
    @Size(min = 1)
    @Column
    String[] directions;

    public RecipeDAO(String name, String description, String[] ingredients, String[] directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

}
