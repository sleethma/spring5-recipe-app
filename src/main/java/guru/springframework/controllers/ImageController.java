package guru.springframework.controllers;

import guru.springframework.command_objs.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class ImageController {

    private RecipeService recipeService;
    private ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{id}/get-image")
    public String getImageForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/image-upload-form.html";
    }

    @RequestMapping("/recipe/{id}/image")
    @PostMapping
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand command = recipeService.findRecipeCommandById(Long.valueOf(id));
        Byte[] test = command.getImage();

        if(command.getImage() !=null) {
            byte[] bytes = new byte[command.getImage().length];
            int i = 0;
            for (Byte wrappedByte : command.getImage()) {
                bytes[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytes);
            IOUtils.copy(is, response.getOutputStream());
        }else{
            log.debug("null image in recipe id: " + id);
        }
    }

}
