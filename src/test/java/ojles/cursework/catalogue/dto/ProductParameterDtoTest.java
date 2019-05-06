package ojles.cursework.catalogue.dto;

import ojles.cursework.catalogue.domain.ProductParameter;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductParameterDtoTest {
    @Test
    public void testFromShouldReturnNullWhenPassedNull() {
        ProductParameterDto dto = ProductParameterDto.from(null);
        assertThat(dto, is(nullValue()));
    }

    @Test
    public void testFormShouldMapNotNullObjectCorrectly() {
        ProductParameter productParameter = new ProductParameter("somename", "somevalue");
        ReflectionTestUtils.setField(productParameter, "id", 123L);

        ProductParameterDto dto = ProductParameterDto.from(productParameter);
        assertThat(dto.getId(), equalTo(123L));
        assertThat(dto.getName(), equalTo("somename"));
        assertThat(dto.getValue(), equalTo("somevalue"));
    }
}
