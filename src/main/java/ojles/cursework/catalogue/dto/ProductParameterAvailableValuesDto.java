package ojles.cursework.catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductParameterAvailableValuesDto {
    private String name;
    private List<String> values;
}
