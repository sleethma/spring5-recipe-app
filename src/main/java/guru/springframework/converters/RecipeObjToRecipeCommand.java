package guru.springframework.converters;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeObjToRecipeCommand implements Converter<Recipe, RecipeCommand> {


    private final NotesObjToNotesCommand converterToNotesCommand;
    private final IngredientObjToIngredientCommand converterToIngredientCommand;
    private final CategoryObjToCategoryCommand converterToCategoryCommand;

    public RecipeObjToRecipeCommand(NotesObjToNotesCommand notesObjToNotesCommand, IngredientObjToIngredientCommand converterToIngredientCommand,
                                    CategoryObjToCategoryCommand converterToCategoryCommand) {
        this.converterToCategoryCommand = converterToCategoryCommand;
        this.converterToIngredientCommand = converterToIngredientCommand;
        this.converterToNotesCommand = notesObjToNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe == null) return null;

        RecipeCommand convertedRecipeCommand = new RecipeCommand();
        convertedRecipeCommand.setId(recipe.getId());
        convertedRecipeCommand.setDescription(recipe.getDescription());
        convertedRecipeCommand.setPrepTime(recipe.getPrepTime());
        convertedRecipeCommand.setCookTime(recipe.getCookTime());
        convertedRecipeCommand.setServings(recipe.getServings());
        convertedRecipeCommand.setSource(recipe.getSource());
        convertedRecipeCommand.setUrl(recipe.getUrl());
        convertedRecipeCommand.setDirections(recipe.getDirections());
        convertedRecipeCommand.setImages(recipe.getImage());
        convertedRecipeCommand.setDifficulty(recipe.getDifficulty());

        //convert
        convertedRecipeCommand.setNotes(converterToNotesCommand.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach( category -> convertedRecipeCommand.getCategories().add(converterToCategoryCommand.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> convertedRecipeCommand.getIngredients().add(converterToIngredientCommand.convert(ingredient)));
        }

        return convertedRecipeCommand;
    }
}
