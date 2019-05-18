package ojles.cursework.catalogue.dto;

import lombok.Getter;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public final class FindProductResponse {
    private List<ProductDto> products;
    private List<ProductGroupDto> childGroups;
    private Long totalAmount;

    private FindProductResponse() {
    }

    public static FindProductResponse childGroups(List<ProductGroup> childGroups) {
        FindProductResponse response = new FindProductResponse();
        response.childGroups = childGroups.stream()
                .map(ProductGroupDto::from)
                .collect(Collectors.toList());
        return response;
    }

    public static FindProductResponse products(List<Product> products, long totalAmount) {
        FindProductResponse response = new FindProductResponse();
        response.products = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        response.totalAmount = totalAmount;
        return response;
    }
}
