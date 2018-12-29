package guru.springframework.repos;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepo extends CrudRepository<UnitOfMeasure, Long> {

}
