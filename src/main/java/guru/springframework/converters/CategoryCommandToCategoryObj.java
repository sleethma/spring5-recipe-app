package guru.springframework.converters;

import guru.springframework.command_objs.CategoryCommand;
import guru.springframework.domain.Category;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategoryObj implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand command) {
        if(command == null){
            return null;
        }

        Category category = new Category();
        category.setId(command.getId());
        category.setDescription(command.getDescription());
        return category;
    }
}
