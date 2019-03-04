package guru.springframework.controllers;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Controller
public class RecipeController {

    RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipeById(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/create-new")
    public String createNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipe-form";
    }

    //update recipe
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(id));
        return "recipe/recipe-form";
    }

    @PostMapping(name = "/recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult result) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
            return "recipe/recipe-form";
        }
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        log.debug("Deleted id#" + id);
        return "redirect:/";
    }

    //picks up response status from @findById method in @RecipeServiceImpl.class
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showErrorNotFoundPage(Exception exception) {
        log.error("Page Not Found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
