package ojles.cursework.catalogue.controller;

import lombok.RequiredArgsConstructor;
import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import ojles.cursework.catalogue.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public FindProductResponse getProducts(
            @Valid FindProductRequest request,
            @RequestParam Map<String, String> customProperties) {
        customProperties.remove("searchQuery");
        customProperties.remove("groupId");
        customProperties.remove("pageIndex");
        customProperties.remove("pageSize");
        customProperties.remove("minPrice");
        customProperties.remove("maxPrice");
        customProperties.remove("manufacturerId");
        request.setParameters(customProperties);
        return productService.findProducts(request);
    }
}
