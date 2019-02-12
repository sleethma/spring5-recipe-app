package guru.springframework.controllers;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import jdk.jfr.ContentType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by jt on 6/19/17.
 */
@RunWith(SpringRunner.class)
public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    Long recipeId = 1L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/" + recipeId + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    public void createNewRecipeForm() throws Exception{
        mockMvc.perform(get("/recipe/create-new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void postNewRecipe() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "string value here")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeCommand.getId() + "/show"));
    }

    @Test
    public void updateRecipe() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(recipeId);

        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/" + recipeId + "/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void deleteRecipe() throws Exception{

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteRecipeById(anyLong());

    }


}
