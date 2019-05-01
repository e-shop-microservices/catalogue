package ojles.cursework.catalogue.controller;

import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import ojles.cursework.catalogue.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@WebMvcTest
@RunWith(SpringRunner.class)
@MockBean(ProductService.class)
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductController productController;

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
    public void testFindProductRequestShouldBeBindCorrectly() throws Exception {
        when(productController.getProducts(any(FindProductRequest.class), any(MultiValueMap.class)))
                .thenAnswer(invocation -> {
                    FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
                    assertThat(request.getSearchQuery(), equalTo("shoes"));
                    assertThat(request.getGroupId(), equalTo(913L));
                    assertThat(request.getPageIndex(), equalTo(2));
                    assertThat(request.getPageSize(), equalTo(25));
                    assertThat(request.getCustomProperties(), is(nullValue()));
                    return new FindProductResponse();
                });

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("searchQuery", "shoes")
                        .param("groupId", "913")
                        .param("pageIndex", "2")
                        .param("pageSize", "25")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
