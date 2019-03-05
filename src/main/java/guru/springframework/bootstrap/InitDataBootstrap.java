package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.enums.Difficulty;
import guru.springframework.repos.CategoryRepo;
import guru.springframework.repos.RecipeRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Profile("default")
public class InitDataBootstrap implements ApplicationListener<ContextRefreshedEvent>{
    private final RecipeRepo recipeRepo;

    private final CategoryRepo categoryRepo;
    private final UnitOfMeasureRepo unitOfMeasureRepo;

    public InitDataBootstrap(RecipeRepo recipeRepo, CategoryRepo categoryRepo, UnitOfMeasureRepo unitOfMeasureRepo) {
        this.recipeRepo = recipeRepo;
        this.categoryRepo = categoryRepo;
        this.unitOfMeasureRepo = unitOfMeasureRepo;
    }


     @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepo.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }


    private List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>();

        //set up units of measure
        Optional<UnitOfMeasure> eachUoM = unitOfMeasureRepo.findByUnit("Each");
        Optional<UnitOfMeasure> tspnUoM = unitOfMeasureRepo.findByUnit("Tspn");
        Optional<UnitOfMeasure> tbspUoM = unitOfMeasureRepo.findByUnit("Tbsp");
        Optional<UnitOfMeasure> cupUoM = unitOfMeasureRepo.findByUnit("Cup");
        Optional<UnitOfMeasure> dashUoM = unitOfMeasureRepo.findByUnit("Dash");
        Optional<UnitOfMeasure> ounceUoM = unitOfMeasureRepo.findByUnit("Ounce");
        List<Optional<UnitOfMeasure>> units = new ArrayList<>();
        units.add(eachUoM);
        units.add(tspnUoM);
        units.add(tbspUoM);
        units.add(cupUoM);
        units.add(dashUoM);
        units.add(ounceUoM);

        for (Optional<UnitOfMeasure> measure : units) {
            if (!measure.isPresent()) {
                throw new RuntimeException("Expected unit of measure not found");
            }
        }

        //get optionals
        UnitOfMeasure eachU = eachUoM.get();
        UnitOfMeasure tbspU = tbspUoM.get();
        UnitOfMeasure tspnU = tspnUoM.get();
        UnitOfMeasure dashU = dashUoM.get();
        UnitOfMeasure ounceU = ounceUoM.get();
        UnitOfMeasure cupsU = cupUoM.get();

        //create categories
        Optional<Category> mexican = categoryRepo.findByDescription("Mexican");
        if(!mexican.isPresent()){
            throw new RuntimeException("Category not found");
        }
        Category mexicanCat = mexican.get();
        mexicanCat.setDescription("Mexican");

        Recipe grilledChickTacos = new Recipe();
        grilledChickTacos.setDescription("Spicy Grilled Tacos");
        grilledChickTacos.setCookTime(9);
        grilledChickTacos.setPrepTime(20);
        grilledChickTacos.setPrepTime(30);
        grilledChickTacos.setSource("Yummy Sources");
        grilledChickTacos.setServings(4);
        grilledChickTacos.setUrl("https://www.simplyrecipes.com/recipes/grilled-chicken-tacos/");
        grilledChickTacos.setDifficulty(Difficulty.MEDIUM);
        grilledChickTacos.setDirections("1 Prepare a gas or charcoal grill for " +
                "   medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle w" +
                " ith the thinned sour cream. Serve with lime wedges.");
        grilledChickTacos.getCategories().add(mexicanCat);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. " +
                "Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.\n");
        tacoNotes.setRecipe(grilledChickTacos);
        grilledChickTacos.addNote(tacoNotes);

        //create ingredients
        grilledChickTacos.addIngredients(new Ingredient("Ancho Chili Powder", new BigDecimal(2),tbspU,grilledChickTacos ));
        grilledChickTacos.addIngredients(new Ingredient("Dried Oregano", new BigDecimal(1),tspnU, grilledChickTacos ));
        grilledChickTacos.addIngredients(new Ingredient("Dried Cumin", new BigDecimal(1),tbspU, grilledChickTacos));
        grilledChickTacos.addIngredients(new Ingredient("Sugar", new BigDecimal(1),tspnU, grilledChickTacos));
        grilledChickTacos.addIngredients(new Ingredient("Salt", new BigDecimal(0.5),tspnU, grilledChickTacos));
        grilledChickTacos.addIngredients(new Ingredient("Clove Garlic", new BigDecimal(1),eachU, grilledChickTacos));
        grilledChickTacos.addIngredients(new Ingredient("Orange Zest", new BigDecimal(1),tbspU, grilledChickTacos));
        grilledChickTacos.addIngredients(new Ingredient("Chicken Thighs", new BigDecimal(5),eachU, grilledChickTacos));

        grilledChickTacos.getCategories().add(mexicanCat);

        //Second Recipe
        Recipe guacamole = new Recipe();
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("\n" +
                "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamole.setDescription("A Creamy and Delicious Guacamole");
        guacamole.setCookTime(10);
        guacamole.setPrepTime(30);
        guacamole.setServings(4);
        guacamole.setSource("Yummy Sources");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.getCategories().add(mexicanCat);
        guacamole.getIngredients().add(new Ingredient("ripe avacados", new BigDecimal(2),eachU , guacamole));
        guacamole.getIngredients().add(new Ingredient("Kosher Salt", new BigDecimal(0.5),tspnU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Lime Or Lemon Juice", new BigDecimal(1),tbspU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Red Onion", new BigDecimal(0.25),cupsU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2),tbspU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Pepper", new BigDecimal(1),dashU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Serrano Chiles", new BigDecimal(2),eachU, guacamole));
        guacamole.getIngredients().add(new Ingredient("Ripe Tomatoes", new BigDecimal(0.5),eachU, guacamole));
        guacamole.getCategories().add(mexicanCat);



        Notes guacNotes = new Notes();
        guacNotes.setRecipe(guacamole);
        guacNotes.setRecipeNotes("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro " +
                "and lime. Garnish with red radishes or jicama." +
                " Serve with tortilla chips. Watch how to make guacamole - it's easy!");
        guacamole.setNotes(guacNotes);
        recipeList.add(guacamole);
        recipeList.add(grilledChickTacos);

        return recipeList;
    }

}
