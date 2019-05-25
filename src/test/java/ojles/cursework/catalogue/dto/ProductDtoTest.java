package ojles.cursework.catalogue.dto;

import ojles.cursework.catalogue.domain.Manufacturer;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;
import ojles.cursework.catalogue.domain.ProductParameter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ProductDtoTest {
    @Test
    public void testWithoutParametersShouldReturnNullOnNull() {
        ProductDto dto = ProductDto.withoutParameters(null);
        assertThat(dto, is(nullValue()));
    }

    @Test
    public void testWithPropertiesShouldReturnNullOnNull() {
        ProductDto dto = ProductDto.withParameters(null);
        assertThat(dto, is(nullValue()));
    }


    @Test
    public void testWithoutParameters() {
        Manufacturer manufacturer = new Manufacturer();
        setField(manufacturer, "id", 1);

        ProductGroup productGroup = new ProductGroup();
        setField(productGroup, "id", 2L);

        Product product = new Product("name", "descr", 123L, "imgPath", manufacturer);
        setField(product, "id", 3L);
        setField(product, "group", productGroup);
        product.getParameters().add(new ProductParameter("par_name", "par_descr"));

        ProductDto dto = ProductDto.withoutParameters(product);
        assertThat(dto.getId(), equalTo(3L));
        assertThat(dto.getName(), equalTo("name"));
        assertThat(dto.getDescription(), equalTo("descr"));
        assertThat(dto.getPrice(), equalTo(123L));
        assertThat(dto.getImagePath(), equalTo("imgPath"));
        assertThat(dto.getGroupId(), equalTo(2L));
        assertThat(dto.getManufacturerId(), equalTo(1));
        assertThat(dto.getParameters().size(), equalTo(0));
    }

    @Test
    public void testWithParameters() {
        Manufacturer manufacturer = new Manufacturer();
        setField(manufacturer, "id", 1);

        ProductGroup productGroup = new ProductGroup();
        setField(productGroup, "id", 2L);

        Product product = new Product("name", "descr", 123L, "imgPath", manufacturer);
        setField(product, "id", 3L);
        setField(product, "group", productGroup);
        product.getParameters().add(new ProductParameter("par_name", "par_descr"));

        ProductDto dto = ProductDto.withParameters(product);
        assertThat(dto.getId(), equalTo(3L));
        assertThat(dto.getName(), equalTo("name"));
        assertThat(dto.getDescription(), equalTo("descr"));
        assertThat(dto.getPrice(), equalTo(123L));
        assertThat(dto.getImagePath(), equalTo("imgPath"));
        assertThat(dto.getGroupId(), equalTo(2L));
        assertThat(dto.getManufacturerId(), equalTo(1));
        assertThat(dto.getParameters().size(), equalTo(1));
        assertThat(dto.getParameters().get(0).getName(), equalTo("par_name"));
        assertThat(dto.getParameters().get(0).getValue(), equalTo("par_descr"));
    }
}
