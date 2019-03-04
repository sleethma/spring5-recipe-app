package guru.springframework.services;


import guru.springframework.command_objs.IngredientCommand;

import java.util.Set;

public interface IngredientService {

    IngredientCommand findIngredientCommandById(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    IngredientCommand deleteIngredient(Long recipeId, Long ingredientId);

}
