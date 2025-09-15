package tj.alimov.gorcerystoreinventory.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tj.alimov.gorcerystoreinventory.controller.CategoryController;
import tj.alimov.gorcerystoreinventory.dto.CategoryRequestDto;
import tj.alimov.gorcerystoreinventory.dto.CategoryResponseDto;
import tj.alimov.gorcerystoreinventory.service.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {


    private MockMvc mockMvc;
    @MockitoBean
    private CategoryService categoryService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext context){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_ShouldReturn201AndCategory() throws Exception{
        CategoryRequestDto request = new CategoryRequestDto("Fruits", "Fresh fruits");
        CategoryResponseDto response = new CategoryResponseDto(1L, "Fruits", "Fresh fruits");

        Mockito.when(categoryService.create(any(CategoryRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fruits"));
    }

    @Test
    void getById_ShouldReturnCategory() throws Exception{
        CategoryResponseDto response = new CategoryResponseDto(1L, "Fruits", "Fresh fruits");

        Mockito.when(categoryService.getById(any(Long.class))).thenReturn(response);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fruits"));
    }




}
