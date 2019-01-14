package guru.springframework.command_objs;


import guru.springframework.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {

        private Long id;
        private String recipeNotes;
        private Recipe recipe;
}
