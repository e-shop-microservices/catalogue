package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private long price;
    private String imagePath;
    private long groupId;
    private int manufacturerId;
    private List<ProductParameterDto> parameters = new ArrayList<>();

    public static ProductDto withoutParameters(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto dto = new ProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        dto.imagePath = product.getImagePath();
        dto.groupId = product.getGroupId();
        dto.manufacturerId = product.getManufacturerId();
        return dto;
    }

    public static ProductDto withParameters(Product product) {
        ProductDto dto = withoutParameters(product);
        if (dto == null) {
            return null;
        }
        dto.parameters = product.getParameters().stream()
                .map(ProductParameterDto::from)
                .collect(Collectors.toList());
        return dto;
    }
}
