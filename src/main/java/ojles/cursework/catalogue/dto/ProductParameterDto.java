package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.ProductParameter;

@Getter
@Setter
public class ProductParameterDto {
    private long id;
    private String name;
    private String value;

    public static ProductParameterDto from(ProductParameter parameter) {
        if (parameter == null) {
            return null;
        }

        ProductParameterDto dto = new ProductParameterDto();
        dto.id = parameter.getId();
        dto.name = parameter.getName();
        dto.value = parameter.getValue();
        return dto;
    }
}