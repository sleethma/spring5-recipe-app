package guru.springframework.services;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipeObj;
import guru.springframework.converters.RecipeObjToRecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repos.RecipeRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepo recipeRepo;
    @Mock
    RecipeCommandToRecipeObj converterToRecipeObj;
    @Mock
    RecipeObjToRecipeCommand converterToRecipeCommand;

    Long recipeId = 1L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepo, converterToRecipeObj, converterToRecipeCommand);
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        //arrange
        when(recipeService.getRecipes()).thenReturn(recipeSet);
        //act
        Set<Recipe> recipes = recipeService.getRecipes();
        //assert
        assertEquals(recipes.size(),1);
        verify(recipeRepo, times(1)).findAll();

    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepo, times(1)).findById(anyLong());
        verify(recipeRepo, never()).findAll();
    }

    @Test
    public void deleteRecipeById(){

        recipeService.deleteRecipeById(recipeId);

        verify(recipeRepo, times(1)).deleteById(recipeId);
    }

    @Test
    public void findRecipeCommandByIdTest(){
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        //when
        when(converterToRecipeCommand.convert(any())).thenReturn(recipeCommand);
        RecipeCommand returnedRecipeCommand = recipeService.findRecipeCommandById(recipeId);
        //then
        assertEquals(recipeId, returnedRecipeCommand.getId());
    }

    @Test
    public void getIngredientsTest(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        recipe.addIngredients(ingredient);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);
        recipe.addIngredients(ingredient2);
        HashSet recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        //when
        when(recipeService.getRecipes()).thenReturn(recipeSet);
        int size = recipeService.getRecipes().iterator().next().getIngredients().size();

        //then
        assertEquals(size,2);
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeIdNotFoundException(){
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(3L);

        //should throw exception
    }
}