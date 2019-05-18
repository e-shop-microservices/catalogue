package ojles.cursework.catalogue.dao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ParameterAvailableValues {
    private static final String VALUES_SEPARATOR = ",";

    private String name;
    // values separated by ','
    private String values;

    public List<String> valuesToList() {
        return Arrays.asList(values.split(VALUES_SEPARATOR));
    }
}
