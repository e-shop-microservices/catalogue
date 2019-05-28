package ojles.coursework.catalogue.controller;

import lombok.RequiredArgsConstructor;
import ojles.coursework.catalogue.service.ProductService;
import ojles.coursework.catalogue.domain.Product;
import ojles.coursework.catalogue.dto.FindProductRequest;
import ojles.coursework.catalogue.dto.FindProductResponse;
import ojles.coursework.catalogue.dto.ProductDto;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public FindProductResponse getProducts(
            @RequestParam(name = "id", required = false) List<Long> productIds,
            @Valid FindProductRequest request,
            @RequestParam MultiValueMap<String, String> parameters) {
        parameters.remove("searchQuery");
        parameters.remove("groupId");
        parameters.remove("pageIndex");
        parameters.remove("pageSize");
        parameters.remove("minPrice");
        parameters.remove("maxPrice");
        parameters.remove("manufacturerId");
        request.setParameters(parameters);
        request.setProductIds(productIds);
        return productService.findProducts(request);
    }

    @GetMapping("/{productId}")
    public ProductDto getById(@PathVariable("productId") long productId) {
        Product product = productService.findById(productId);
        return ProductDto.withParameters(product);
    }
}
