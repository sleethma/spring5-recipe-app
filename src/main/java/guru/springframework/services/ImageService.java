package guru.springframework.services;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


public interface ImageService {
void saveImageFile(Long imageId, MultipartFile file);
}
