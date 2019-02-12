package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repos.RecipeRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    ImageService imageService;

    @Mock
    RecipeRepo recipeRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepo);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        Recipe recipe =new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional= Optional.of(recipe);

        MultipartFile file = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework Guru".getBytes());

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        when(recipeRepo.findById(anyLong())).thenReturn(recipeOptional);
        imageService.saveImageFile(1L, file);

        //then
        verify(recipeRepo, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }
}