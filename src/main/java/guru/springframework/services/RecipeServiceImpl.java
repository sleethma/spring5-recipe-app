package guru.springframework.services;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipeObj;
import guru.springframework.converters.RecipeObjToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepo recipeRepo;
    private final RecipeCommandToRecipeObj commandToRecipeObj;
    private final RecipeObjToRecipeCommand recipeObjToCommand;

    public RecipeServiceImpl(RecipeRepo recipeRepo, RecipeCommandToRecipeObj commandToRecipeObj,
                             RecipeObjToRecipeCommand recipeObjToCommand) {
        this.recipeRepo = recipeRepo;
        this.commandToRecipeObj = commandToRecipeObj;
        this.recipeObjToCommand = recipeObjToCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepo.findAll().iterator().forEachRemaining(recipeSet::add);
        int size = recipeSet.size();
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if(!recipe.isPresent()) throw new RuntimeException("recipe not found by id!");
        return recipe.orElse(null);
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe savedRecipe = recipeRepo.save(commandToRecipeObj.convert(recipeCommand));
        return recipeObjToCommand.convert(savedRecipe);
    }
}
