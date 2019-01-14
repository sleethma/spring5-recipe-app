package guru.springframework.command_objs;


import guru.springframework.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {

        private Long id;
        private String unit;
}
