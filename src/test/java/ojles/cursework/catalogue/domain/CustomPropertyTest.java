package ojles.cursework.catalogue.domain;

import ojles.cursework.catalogue.exception.ForbiddenActionException;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CustomPropertyTest {
    @Test
    public void testDefaultConstructorPresent() {
        assertThat(new CustomProperty(), notNullValue());
    }

    @Test
    public void testConstructorForNumberProperty() {
        CustomProperty numberProperty = new CustomProperty("number_property_name", 123L);
        assertThat(numberProperty.getName(), equalTo("number_property_name"));
        assertThat(numberProperty.getNumber(), equalTo(123L));
        assertThat(numberProperty.getType(), equalTo(CustomProperty.Type.NUMBER));
    }

    @Test
    public void testConstructorForStringProperty() {
        CustomProperty stringProperty = new CustomProperty("string_property_name", "123L string");
        assertThat(stringProperty.getName(), equalTo("string_property_name"));
        assertThat(stringProperty.getString(), equalTo("123L string"));
        assertThat(stringProperty.getType(), equalTo(CustomProperty.Type.STRING));
    }

    @Test
    public void testConstructorForOptionsProperty() {
        CustomProperty optionsProperty = new CustomProperty("options_property_name", asList("1", "2", "3", "L"));
        assertThat(optionsProperty.getName(), equalTo("options_property_name"));
        assertThat(optionsProperty.getOptions(), hasItems("1", "2", "3", "L"));
        assertThat(optionsProperty.getType(), equalTo(CustomProperty.Type.OPTIONS));
    }

    @Test(expected = DomainException.class)
    public void testFailWhenAccessWrongPropertyType() {
        new CustomProperty("number_property_name", 234L).getString();
    }

    @Test(expected = ForbiddenActionException.class)
    public void testFailWhenOptionsContainSpecialSymbol() {
        new CustomProperty("options_property_name", asList("1", "sdf:", "2"));
    }
}
