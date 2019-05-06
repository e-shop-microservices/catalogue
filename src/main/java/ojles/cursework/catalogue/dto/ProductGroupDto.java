package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.ProductGroup;

@Getter
@Setter
public class ProductGroupDto {
    private long id;
    private String name;
    private String imagePath;
    private long parentGroupId;

    public static ProductGroupDto from(ProductGroup group) {
        if (group == null) {
            return null;
        }

        ProductGroupDto dto = new ProductGroupDto();
        dto.id = group.getId();
        dto.name = group.getName();
        dto.imagePath = group.getImagePath();
        dto.parentGroupId = group.getParentGroupId();
        return dto;
    }
}
