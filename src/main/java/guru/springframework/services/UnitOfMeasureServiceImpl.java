package guru.springframework.services;

import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureObjToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.UnitOfMeasureRepo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

private final UnitOfMeasureRepo repo;
private final UnitOfMeasureObjToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepo repo, UnitOfMeasureObjToUnitOfMeasureCommand converter) {
        this.repo = repo;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasureCommand> uomCommandSet  = StreamSupport.stream(repo.findAll()
                .spliterator(), false)
                .map(converter :: convert)
                .collect(Collectors.toSet());
        return uomCommandSet;
    }
}
