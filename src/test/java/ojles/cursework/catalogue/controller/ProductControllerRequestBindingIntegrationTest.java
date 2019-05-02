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

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@WebMvcTest
@RunWith(SpringRunner.class)
@MockBean(ProductService.class)
public class ProductControllerRequestBindingIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductController productController;

    @Test
    public void testFindProductRequestShouldBindCorrectly() throws Exception {
        when(productController.getProducts(any(FindProductRequest.class), any(MultiValueMap.class)))
                .thenAnswer(invocation -> {
                    FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
                    assertThat(request.getSearchQuery(), equalTo("shoes"));
                    assertThat(request.getGroupId(), equalTo(913L));
                    assertThat(request.getPageIndex(), equalTo(2));
                    assertThat(request.getPageSize(), equalTo(25));
                    assertThat(request.getMinPrice(), equalTo(1234L));
                    assertThat(request.getMaxPrice(), equalTo(2345L));
                    assertThat(request.getManufacturerId(), equalTo(384));
                    assertThat(request.getParameters(), is(nullValue()));
                    return new FindProductResponse();
                });

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("searchQuery", "shoes")
                        .param("groupId", "913")
                        .param("pageIndex", "2")
                        .param("pageSize", "25")
                        .param("minPrice", "1234")
                        .param("maxPrice", "2345")
                        .param("manufacturerId", "384")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
