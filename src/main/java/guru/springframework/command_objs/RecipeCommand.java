package guru.springframework.command_objs;


import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.enums.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

        private Long id;
        private String description;
        private Integer prepTime;
        private Integer cookTime;
        private Integer servings;
        private String source;
        private String url;
        private String directions;
        private Byte[] images;
        private NotesCommand notes;
        private Difficulty difficulty;



        private Set<IngredientCommand> ingredients = new HashSet<>();
        private Set<CategoryCommand> categories = new HashSet<>();




}
