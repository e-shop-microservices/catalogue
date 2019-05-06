package ojles.cursework.catalogue.dto;

import ojles.cursework.catalogue.domain.ProductGroup;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductGroupDtoTest {
    @Test
    public void testFromShouldReturnNullWhenPassedNull() {
        ProductGroupDto dto = ProductGroupDto.from(null);
        assertThat(dto, is(nullValue()));
    }

    @Test
    public void testFormShouldMapNotNullObjectCorrectly() {
        ProductGroup parentGroup = new ProductGroup();
        ReflectionTestUtils.setField(parentGroup, "id", 1L);

        ProductGroup group = new ProductGroup("groupName", "imagePath");
        ReflectionTestUtils.setField(group, "id", 2L);
        ReflectionTestUtils.setField(group, "parentGroup", parentGroup);

        ProductGroupDto dto = ProductGroupDto.from(group);
        assertThat(dto.getId(), equalTo(2L));
        assertThat(dto.getName(), equalTo("groupName"));
        assertThat(dto.getImagePath(), equalTo("imagePath"));
        assertThat(dto.getParentGroupId(), equalTo(1L));
    }
}
