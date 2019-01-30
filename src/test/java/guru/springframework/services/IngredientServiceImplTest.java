package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientObjToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureObjToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.RecipeRepo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepo recipeRepo;

    IngredientObjToIngredientCommand converter;

    IngredientService ingredientService;


    //concrete classes
    Recipe recipe;
    Optional<Recipe> recipeOptional;
    Set<Ingredient> ingredientSet;
    Ingredient ingredient;
    Ingredient ingredient2;
    UnitOfMeasure unitOfMeasure;

    public IngredientServiceImplTest() {
        this.converter = new IngredientObjToIngredientCommand(new UnitOfMeasureObjToUnitOfMeasureCommand());
    }


    Long recipeId = 1L;
    Long ingredientId = 2L;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepo, converter);
        recipe = new Recipe();
        recipe.setId(recipeId);
        ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUnit("tspn");
        ingredient.setUnit(unitOfMeasure);
        ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setUnit(unitOfMeasure);
        recipe.addIngredients(ingredient);
        recipe.addIngredients(ingredient2);
    }

    @Test
    public void findIngredientCommandById() throws Exception {
        //given
        recipeOptional = Optional.of(recipe);
        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);
        //when
        IngredientCommand ingredientCommandReturned = ingredientService.findIngredientCommandById(recipeId, ingredientId);
        //then
        assertEquals(Long.valueOf(ingredientId), ingredientCommandReturned.getId());
    }

}
