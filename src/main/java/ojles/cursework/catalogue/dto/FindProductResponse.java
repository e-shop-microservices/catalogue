package ojles.cursework.catalogue.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FindProductResponse {
    @Setter(AccessLevel.NONE)
    private List<ProductDto> products;
    @Setter(AccessLevel.NONE)
    private List<ProductGroupDto> childGroups;
    private Long totalAmount;

    public void setProducts(List<Product> products) {
        this.products = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void setChildGroups(List<ProductGroup> childGroups) {
        this.childGroups = childGroups.stream()
                .map(ProductGroupDto::from)
                .collect(Collectors.toList());
    }
}
