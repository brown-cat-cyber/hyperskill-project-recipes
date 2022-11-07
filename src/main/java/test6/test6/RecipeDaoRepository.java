package test6.test6;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDaoRepository extends CrudRepository<RecipeDAO, Integer> {

}
