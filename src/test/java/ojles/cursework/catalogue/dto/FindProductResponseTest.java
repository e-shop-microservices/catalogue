package ojles.cursework.catalogue.dto;

import ojles.cursework.catalogue.domain.Manufacturer;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FindProductResponseTest {
    @Test
    public void testFactoryMethodChildGroups() {
        ProductGroup group = new ProductGroup();
        ProductGroup parentGroup = new ProductGroup();
        parentGroup.addChildGroup(group);
        List<ProductGroup> entities = new ArrayList<>();
        entities.add(group);

        FindProductResponse response = FindProductResponse.childGroups(entities);
        assertThat(response.getProducts(), is(nullValue()));
        assertThat(response.getChildGroups(), is(not(nullValue())));
        assertThat(response.getTotalAmount(), is(nullValue()));
    }

    @Test
    public void testFactoryMethodProducts() {
        Product product = new Product("name", "descr", 123L, "img", new Manufacturer());
        ProductGroup group = new ProductGroup();
        group.addProduct(product);
        List<Product> entities = new ArrayList<>();
        entities.add(product);

        FindProductResponse response = FindProductResponse.products(entities, 23L);
        assertThat(response.getProducts(), is(not(nullValue())));
        assertThat(response.getChildGroups(), is(nullValue()));
        assertThat(response.getTotalAmount(), is(equalTo(23L)));
    }
}
