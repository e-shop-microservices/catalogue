package ojles.coursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.coursework.catalogue.domain.ProductParameter;

@Getter
@Setter
public class ProductParameterDto {
    private String name;
    private String value;

    public static ProductParameterDto from(ProductParameter parameter) {
        if (parameter == null) {
            return null;
        }

        ProductParameterDto dto = new ProductParameterDto();
        dto.name = parameter.getName();
        dto.value = parameter.getValue();
        return dto;
    }
}
