package guru.springframework.converters;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredientObj implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasureObj converterToUofM;


    public IngredientCommandToIngredientObj( UnitOfMeasureCommandToUnitOfMeasureObj uofmConverter) {
        this.converterToUofM = uofmConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand command) {
        if(command == null) return null;

        Ingredient convertedIngredientObj = new Ingredient();
        convertedIngredientObj.setId(command.getId());
        convertedIngredientObj.setAmount(command.getAmount());
        convertedIngredientObj.setDescription(command.getDescription());
        convertedIngredientObj.setUnit(converterToUofM.convert(command.getUnitOfMeasure()));



        return convertedIngredientObj;
    }
}
