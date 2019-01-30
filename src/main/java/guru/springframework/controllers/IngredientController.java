package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredientList(Model model, @PathVariable String id){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @GetMapping
            ("recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredientByRecipeAndIngredientId(
            Model model, @PathVariable String recipeId,
            @PathVariable String id) {

        model.addAttribute("ingredient",
                ingredientService.findIngredientCommandById(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }
}
