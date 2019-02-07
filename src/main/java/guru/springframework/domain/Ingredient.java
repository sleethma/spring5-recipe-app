package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;


    //unit of measure
    @OneToOne(fetch = FetchType.EAGER)//@fetch not necessary as is default
    private UnitOfMeasure unit;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(){

    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unit,Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.recipe = recipe;
    }


}
