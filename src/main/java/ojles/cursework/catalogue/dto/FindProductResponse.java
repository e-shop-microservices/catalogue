package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FindProductResponse {
    private List<ProductDto> products;
    private List<ProductGroup> childGroups;
    private int totalPages;
    private int totalAmount;

    public void setProducts(List<Product> entities) {
        products = entities.stream()
                .map(entity -> new ProductDto(entity.getName(), entity.getPrice()))
                .collect(Collectors.toList());
    }
}
