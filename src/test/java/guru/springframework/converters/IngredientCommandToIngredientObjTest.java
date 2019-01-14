package guru.springframework.converters;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class IngredientCommandToIngredientObjTest {

    @Mock
    UnitOfMeasureCommandToUnitOfMeasureObj uOfMConverter;
    @Mock
    UnitOfMeasure unitOfMeasureObj;

    //objects
    Long ingredientId = 3L;
    IngredientCommand ingredientCommand;
    @InjectMocks
    IngredientCommandToIngredientObj ingredientConverter;

    @Before
    public void setUp() throws Exception {
        ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredientId);
    }

    @Test
    public void convert() {
        //given
        when(uOfMConverter.convert(any())).thenReturn(unitOfMeasureObj);
        //when
        Ingredient ingredientObj = ingredientConverter.convert(ingredientCommand);
        //then
        assertNotNull(ingredientObj);
        assertEquals(ingredientId ,ingredientObj.getId() );
        verify(uOfMConverter, times(1)).convert(any());
    }
}