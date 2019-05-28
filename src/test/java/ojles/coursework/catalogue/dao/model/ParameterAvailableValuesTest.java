package ojles.coursework.catalogue.dao.model;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParameterAvailableValuesTest {
    @Test
    public void testParametersToList() {
        String valuesSeparator = (String) ReflectionTestUtils
                .getField(ParameterAvailableValues.class, "VALUES_SEPARATOR");
        String valuesString = "one" + valuesSeparator + "two" + valuesSeparator + "three";

        ParameterAvailableValues availableValues = new ParameterAvailableValues();
        availableValues.setValues(valuesString);

        assertThat(availableValues.valuesToList().size(), equalTo(3));
        assertThat(availableValues.valuesToList(), hasItems("one", "two", "three"));
    }
}
