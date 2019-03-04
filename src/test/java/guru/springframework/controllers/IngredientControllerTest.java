package guru.springframework.controllers;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IngredientControllerTest {

//    ====Fields===
    Long recipeId = 1L;
    Long ingredientId = 2L;
    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService uomService;

    //Concrete Classes
    @InjectMocks
    IngredientController ingredientController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findRecipeCommandById(anyLong());
    }


    @Test
    public void showIngredientByRecipeAndIngredientId() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findIngredientCommandById(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));
    }

    @Test
    public void updateIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        //setup
        when(ingredientService.findIngredientCommandById(recipeId, ingredientId)).thenReturn(ingredientCommand);
        when(uomService.listAllUoms()).thenReturn(new HashSet<>());

        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient-form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void newIngredientTest() throws Exception{
        //given
        RecipeCommand newRecipeCommand = new RecipeCommand();

        //when
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(newRecipeCommand);
        when(uomService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredient/ingredient-form"));

        //then
        verify(recipeService, times(1)).findRecipeCommandById(anyLong());

    }

    @Test
    public void deleteIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        when(ingredientService.deleteIngredient(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        verify(ingredientService, times(1)).deleteIngredient(anyLong(), anyLong());

    }
}