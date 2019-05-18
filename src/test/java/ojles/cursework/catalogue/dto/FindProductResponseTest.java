package ojles.cursework.catalogue.dto;

import ojles.cursework.catalogue.dao.model.ParameterAvailableValues;
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

        ParameterAvailableValues parameter1 = new ParameterAvailableValues();
        parameter1.setName("name1");
        parameter1.setValues("value11,value12");
        ParameterAvailableValues parameter2 = new ParameterAvailableValues();
        parameter2.setName("name2");
        parameter2.setValues("value21,value22,value23");
        List<ParameterAvailableValues> parameters = new ArrayList<>();
        parameters.add(parameter1);
        parameters.add(parameter2);

        FindProductResponse response = FindProductResponse.products(entities, 23L, parameters);
        List<ProductParameterAvailableValuesDto> availableParameters = response.getAvailableParameters();
        assertThat(response.getProducts(), is(not(nullValue())));
        assertThat(response.getChildGroups(), is(nullValue()));
        assertThat(response.getTotalAmount(), is(equalTo(23L)));
        assertThat(availableParameters.size(), equalTo(2));
        for (ProductParameterAvailableValuesDto parameterValues : availableParameters) {
            if (parameterValues.getName().equals("name1")) {
                assertThat(parameterValues.getValues(), hasItems("value11", "value12"));
            } else if (parameterValues.getName().equals("name2")) {
                assertThat(parameterValues.getValues(), hasItems("value21", "value22", "value23"));
            } else {
                throw new RuntimeException("Invalid parameter name=" + parameterValues.getName());
            }
        }
    }
}
