package test6.test6.PersistenceLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test6.test6.BusienessLayer.Recipe;

import java.util.List;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findByCategory(String category);

    List<Recipe> findByNameContaining(String name);

}
