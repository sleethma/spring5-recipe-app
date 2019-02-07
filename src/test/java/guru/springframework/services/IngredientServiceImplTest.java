package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredientObj;
import guru.springframework.converters.IngredientObjToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasureObj;
import guru.springframework.converters.UnitOfMeasureObjToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.RecipeRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepo recipeRepo;

    @Mock
    UnitOfMeasureRepo uomRepo;

    IngredientObjToIngredientCommand converterToCommand;

    IngredientCommandToIngredientObj converterToObject;

    IngredientService ingredientService;


    //concrete classes
    Recipe recipe;
    Optional<Recipe> recipeOptional;
    Ingredient ingredient;
    Ingredient ingredient2;
    UnitOfMeasure unitOfMeasure;

    public IngredientServiceImplTest() {
        this.converterToCommand = new IngredientObjToIngredientCommand(new UnitOfMeasureObjToUnitOfMeasureCommand());
        this.converterToObject = new IngredientCommandToIngredientObj(new UnitOfMeasureCommandToUnitOfMeasureObj());
    }


    Long recipeId = 1L;
    Long ingredientId = 2L;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepo, converterToCommand, converterToObject,  uomRepo);
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

    @Test
    public void saveIngredientCommand() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId(recipeId);

        Optional<UnitOfMeasure >uomCommand = Optional.of(new UnitOfMeasure());
        uomCommand.get().setId(3L);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        System.out.println("Ingredients of Recipe: " + recipeOptional.get().getIngredients().size());
        System.out.println("Ingredient 1 matches command ingredient: " + recipeOptional.get().getIngredients().iterator().next().getId().equals(ingredientCommand.getId()));

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredients(new Ingredient());
        Ingredient savedIngredient = savedRecipe.getIngredients().iterator().next();
        savedIngredient.setId(ingredientId);
        savedIngredient.setAmount(BigDecimal.TEN);
        savedIngredient.setDescription("test description");

        //when
        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);
        when(uomRepo.findById(any())).thenReturn(uomCommand);
        when(recipeRepo.save(any())).thenReturn(savedRecipe);
        System.out.println("Unit of measure has Id of: " + uomRepo.findById(1111L).get().getId());

        IngredientCommand returnedIngredient = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(ingredientCommand.getId(), returnedIngredient.getId());

    }

    @Test
    public void deleteIngredient() {
        //given
        recipeOptional = Optional.of(recipe);
        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);
        //when
        ingredientService.deleteIngredient(recipeId, ingredientId);

        //then
        verify(recipeRepo, times(1)).findById(anyLong());
        verify(recipeRepo, times(1)).save(any(Recipe.class));

    }
}
