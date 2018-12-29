package guru.springframework.repos;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepo extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByUnit(String unit);
}
