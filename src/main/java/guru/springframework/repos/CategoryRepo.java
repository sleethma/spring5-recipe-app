package guru.springframework.repos;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
    
}
