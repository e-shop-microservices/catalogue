package ojles.coursework.catalogue.controller;

import ojles.coursework.catalogue.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest
@RunWith(SpringRunner.class)
@MockBean(ProductService.class)
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShouldFailWhenPageIndexIsNegative() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("pageIndex", "-1")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testShouldFailWhenPageSizeOutOfRange() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("pageSize", "300")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("pageSize", "0")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testShouldFailWhenMinPriceIsLessThenZero() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("minPrice", "-20")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testShouldFailWhenMaxPriceIsLessThenZero() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("maxPrice", "-30")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
