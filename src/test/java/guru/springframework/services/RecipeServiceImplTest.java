package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipeObj;
import guru.springframework.converters.RecipeObjToRecipeCommand;
import guru.springframework.domain.Recipe;
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
}