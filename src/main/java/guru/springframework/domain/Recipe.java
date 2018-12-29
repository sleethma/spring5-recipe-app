package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Recipe {

    //@GenType: supports autogen for id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe") // @cascade: allows Recipe class to own (parent) the Ingredients
    private Set<Ingredient> ingredients = new HashSet<>();
    //todo: add difficulty

    @Lob //enables large object field
    private Byte[] images;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
}
