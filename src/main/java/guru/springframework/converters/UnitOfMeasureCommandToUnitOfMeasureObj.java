package guru.springframework.converters;

import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasureObj implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {


    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand command) {
        if(command == null) return null;

        UnitOfMeasure unitOfMeasureObj = new UnitOfMeasure();
        unitOfMeasureObj.setId(command.getId());
        unitOfMeasureObj.setUnit(command.getUnit());
        return unitOfMeasureObj;
    }
}
