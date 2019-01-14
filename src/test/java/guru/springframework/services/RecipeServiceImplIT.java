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

import static org.junit.Assert.*;

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

    Long recipeId = 3L;

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

        //when

        //then
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