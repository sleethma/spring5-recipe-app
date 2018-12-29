package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repos.CategoryRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

    private UnitOfMeasureRepo unitOfMeasureRepo;
    private CategoryRepo categoryRepo;

    public IndexController(UnitOfMeasureRepo unitOfMeasureRepo, CategoryRepo categoryRepo) {
        this.unitOfMeasureRepo = unitOfMeasureRepo;
        this.categoryRepo = categoryRepo;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){
        Optional<Category> categoryOptional = categoryRepo.findByDescription("American");
        Optional<UnitOfMeasure> UOMOptional = unitOfMeasureRepo.findByUnit("Cup");

        System.out.println("Cat Id=" + categoryOptional.get().getId());
        System.out.println("UoM Id=" + UOMOptional.get().getId());
        return "index";
    }
}
