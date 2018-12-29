package guru.springframework.repos;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {
    
}
