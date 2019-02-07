package guru.springframework.services;

import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureObjToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.UnitOfMeasureRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureImplTest {

    @Mock
    UnitOfMeasureRepo uomRepo;

    UnitOfMeasureObjToUnitOfMeasureCommand converter;
    UnitOfMeasureService uomService;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        converter = new UnitOfMeasureObjToUnitOfMeasureCommand();
        uomService = new UnitOfMeasureServiceImpl(uomRepo, converter);
    }

    @Test
    public void listAllUomsTest(){
        //given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasureSet.add(unitOfMeasure);
        when(uomRepo.findAll()).thenReturn(unitOfMeasureSet);
        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = uomService.listAllUoms();
        //then
        verify(uomRepo, times(1)).findAll();
        assertEquals(1, unitOfMeasureCommands.size());
    }
}
