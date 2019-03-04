package guru.springframework.command_objs;


import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

        private Long id;
        private String description;
        private Long recipeId;
        private BigDecimal amount;
        private UnitOfMeasureCommand unitOfMeasure;
}
