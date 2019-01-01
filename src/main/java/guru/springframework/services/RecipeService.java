package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();
}
