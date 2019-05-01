package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;

import java.util.List;

@Getter
@Setter
public class FindProductResponse {
    private List<Product> products;
    private List<ProductGroup> childGroups;
    private int totalPages;
    private int totalAmount;
}
