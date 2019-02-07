package guru.springframework.controllers;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredientList(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredientByRecipeAndIngredientId(
            Model model, @PathVariable String recipeId,
            @PathVariable String id) {

        model.addAttribute("ingredient",
                ingredientService.findIngredientCommandById(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", uomService.listAllUoms());
        return "recipe/ingredient/ingredient-form";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String updateIngredientPost(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(command);
        log.debug("saved ingredient id: " + savedIngredientCommand.getId());
        log.debug("saved ingredient description: " + savedIngredientCommand.getDescription());
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/"
                + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String createNewIngredient(Model model, @PathVariable String recipeId){
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        if (recipeCommand == null) {
            //todo: throw exception here
        }
        IngredientCommand newIngredient = new IngredientCommand();
        newIngredient.setRecipeId(Long.valueOf(recipeId));
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setUnit("Each");
        newIngredient.setUnitOfMeasure(uomCommand);

        model.addAttribute("ingredient", newIngredient);
        model.addAttribute("uomList", uomService.listAllUoms());

        return "recipe/ingredient/ingredient-form";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable  String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
