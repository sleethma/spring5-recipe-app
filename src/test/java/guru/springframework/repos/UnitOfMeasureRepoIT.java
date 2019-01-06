package guru.springframework.repos;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepoIT {

    @Autowired
    UnitOfMeasureRepo unitOfMeasureRepo;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUnit() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepo.findByUnit("Tspn");

        assertEquals("Tspn", unitOfMeasureOptional.get().getUnit());
    }
}