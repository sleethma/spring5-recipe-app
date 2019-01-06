package guru.springframework.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void startUp(){
       category = new Category();
    }

    @Test
    public void getId() {
        Long id = 13240L;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
        String testDescription = "test string 4343";
        category.setDescription(testDescription);
        assertEquals(testDescription, category.getDescription());
    }

    @Test
    @Ignore
    public void getRecipes() {
    }
}