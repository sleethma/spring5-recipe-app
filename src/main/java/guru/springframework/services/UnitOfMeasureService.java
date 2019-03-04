package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
