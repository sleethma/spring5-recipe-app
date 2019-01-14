package guru.springframework.converters;

import guru.springframework.command_objs.NotesCommand;
import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.enums.Difficulty;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeCommandToRecipeObj implements Converter<RecipeCommand, Recipe> {


    private final NotesCommandToNotesObj converterToNotesObj;
    private final IngredientCommandToIngredientObj converterToIngredientObj;
    private final CategoryCommandToCategoryObj converterToCategoryObj;

    public RecipeCommandToRecipeObj(NotesCommandToNotesObj converterToNotesObj,
                                    IngredientCommandToIngredientObj converterToIngredientObj,
                                    CategoryCommandToCategoryObj converterToCategoryObj) {
        this.converterToNotesObj = converterToNotesObj;
        this.converterToIngredientObj = converterToIngredientObj;
        this.converterToCategoryObj = converterToCategoryObj;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand == null) return null;

        Recipe convertedRecipeObj = new Recipe();
        convertedRecipeObj.setId(recipeCommand.getId());
        convertedRecipeObj.setDescription(recipeCommand.getDescription());
        convertedRecipeObj.setPrepTime(recipeCommand.getPrepTime());
        convertedRecipeObj.setCookTime(recipeCommand.getCookTime());
        convertedRecipeObj.setServings(recipeCommand.getServings());
        convertedRecipeObj.setSource(recipeCommand.getSource());
        convertedRecipeObj.setUrl(recipeCommand.getUrl());
        convertedRecipeObj.setDirections(recipeCommand.getDirections());
        convertedRecipeObj.setImages(recipeCommand.getImages());
        convertedRecipeObj.setDifficulty(recipeCommand.getDifficulty());

        //convert
        convertedRecipeObj.setNotes(converterToNotesObj.convert(recipeCommand.getNotes()));

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0){
            recipeCommand.getCategories()
                    .forEach( category -> convertedRecipeObj.getCategories().add(converterToCategoryObj.convert(category)));
        }

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients()
                    .forEach(ingredient -> convertedRecipeObj.getIngredients().add(converterToIngredientObj.convert(ingredient)));
        }

        return convertedRecipeObj;
    }
}
