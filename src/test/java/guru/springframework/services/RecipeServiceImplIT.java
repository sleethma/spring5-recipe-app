package guru.springframework.services;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipeObj;
import guru.springframework.converters.RecipeObjToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplIT {

    @Autowired
     RecipeRepo recipeRepo;
    @Autowired
     RecipeCommandToRecipeObj commandToRecipeObj;
    @Autowired
    RecipeObjToRecipeCommand recipeObjToCommand;
    @Autowired
    RecipeService recipeService;

    Long recipeId = 1L;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getRecipes() {
        //given

        //when

        //then
    }

    @Test
    public void findById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        //when
        Recipe returnedRecipe = recipeService.findById(recipeId);

        //then
        assertEquals(recipeId, returnedRecipe.getId());
    }

    @Test
    public void saveRecipeCommand() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        //when
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipeCommand);
        //then
        assertEquals(recipeId, savedRecipe.getId());
    }
}