package guru.springframework.converters;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IngredientObjToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureObjToUnitOfMeasureCommand converter;

    public IngredientObjToIngredientCommand(UnitOfMeasureObjToUnitOfMeasureCommand converter) {
        this.converter = converter;
    }


    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null) return null;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        if( ingredient.getRecipe() != null) {
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setUnitOfMeasure(converter.convert(ingredient.getUnit()));
        return ingredientCommand;
    }
}
