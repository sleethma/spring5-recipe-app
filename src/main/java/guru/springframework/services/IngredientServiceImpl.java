package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredientObj;
import guru.springframework.converters.IngredientObjToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Getter
@Setter
public class IngredientServiceImpl implements IngredientService {

    RecipeRepo recipeRepo;
    IngredientObjToIngredientCommand converterToCommand;
    IngredientCommandToIngredientObj converterToObj;
    UnitOfMeasureRepo uomRepo;

    public IngredientServiceImpl(RecipeRepo recipeRepo, IngredientObjToIngredientCommand converterToCommand,
                                 IngredientCommandToIngredientObj converterToObj,  UnitOfMeasureRepo uomrRepo) {
        this.recipeRepo = recipeRepo;
        this.converterToCommand = converterToCommand;
        this.uomRepo = uomrRepo;
        this.converterToObj = converterToObj;
    }

    @Override
    public IngredientCommand findIngredientCommandById(Long recipeId, Long id) {
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);

        if (recipe == null) {
            throw new NullPointerException("Recipe for Ingredient is null");
        }

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> converterToCommand.convert(ingredient)).findFirst();

        UnitOfMeasureCommand uom = optionalIngredientCommand.get().getUnitOfMeasure();
        if (uom == null) {

            throw new NullPointerException("Unit of measure for Ingredient is null");
        }
        return optionalIngredientCommand.orElse(null);
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepo.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()) {
            //todo: toss error if not found
            log.error("Optional Recipe not found in repo from command!");
            return new IngredientCommand();
        }else {
            Optional<Ingredient> ingredientOptional = recipeOptional.get()
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                if(ingredientCommand.getUnitOfMeasure() != null) {
                    ingredientFound.setUnit(uomRepo.findById(ingredientCommand.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("UOM not found")));
                }
            } else {
                Ingredient ingredient = converterToObj.convert(ingredientCommand);
                ingredient.setRecipe(recipeOptional.get());
                recipeOptional.get().addIngredients(ingredient);

            }

            Recipe savedRecipe = recipeRepo.save(recipeOptional.get());

            Optional<Ingredient> savedIngredient = savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (!savedIngredient.isPresent()) {
                savedIngredient = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getUnit().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .findFirst();
            }

            return converterToCommand.convert(savedIngredient.get());
        }
    }

    @Transactional
    @Override
    public IngredientCommand deleteIngredient(Long recipeId, Long ingredientId){
        Optional<Recipe> optionalRecipe = recipeRepo.findById(recipeId);
        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();


            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredientId.equals(ingredient.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);

                //save recipe with new Ingredient set
                recipeRepo.save(recipe);
                return converterToCommand.convert(ingredientToDelete);

            }
        }
        log.error("Ingredient or Recipe not found");
    return null;
    }


}
