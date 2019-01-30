package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientObjToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.RecipeRepo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Getter
@Setter
public class IngredientServiceImpl implements IngredientService {

    RecipeRepo recipeRepo;
    IngredientObjToIngredientCommand converter;

    public IngredientServiceImpl(RecipeRepo recipeRepo, IngredientObjToIngredientCommand converter) {
        this.recipeRepo = recipeRepo;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findIngredientCommandById(Long recipeId, Long id) {
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);

        if (recipe == null) {
            throw new NullPointerException("Recipe for Ingredient is null");
        }

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> converter.convert(ingredient)).findFirst();

        UnitOfMeasureCommand uom = optionalIngredientCommand.get().getUnitOfMeasure();
        if (uom == null) {

            throw new NullPointerException("Unit of measure for Ingredient is null");
        }
        return optionalIngredientCommand.orElse(null);
    }
}
