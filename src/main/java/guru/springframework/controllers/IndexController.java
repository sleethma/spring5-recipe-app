package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.CategoryRepo;
import guru.springframework.repos.RecipeRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes" , recipeService.getRecipes());
        Set<Recipe> recipeSet = recipeService.getRecipes();
        recipeSet.size();
        for (Recipe recipe : recipeSet) {
            System.out.println(recipe.getDescription());
        }
        return "index";
    }

}
