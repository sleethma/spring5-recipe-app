package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }


    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepo.findAll().iterator().forEachRemaining(recipeSet::add);
        int size = recipeSet.size();
        return recipeSet;
    }
}
