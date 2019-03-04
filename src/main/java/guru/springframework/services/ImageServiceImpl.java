package guru.springframework.services;

import guru.springframework.command_objs.IngredientCommand;
import guru.springframework.command_objs.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredientObj;
import guru.springframework.converters.IngredientObjToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import guru.springframework.repos.UnitOfMeasureRepo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@Getter
@Setter
public class ImageServiceImpl implements ImageService {

    RecipeRepo recipeRepo;

    public ImageServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepo.findById(recipeId).get();
        try {
            int i = 0;
            Byte[] imageBytes = new Byte[file.getBytes().length];
            for(byte b : file.getBytes()){
                imageBytes[i++] = b;
            }
            recipe.setImage(imageBytes);
            recipeRepo.save(recipe);
            log.info("received a file with byte size: " + imageBytes.length);

        }catch (IOException e){
            //todo: handle exception
            e.printStackTrace();
        }
    }
}
