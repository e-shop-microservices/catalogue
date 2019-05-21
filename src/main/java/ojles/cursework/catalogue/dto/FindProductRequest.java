package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
public class FindProductRequest {
    private List<Long> productIds;
    private String searchQuery;
    @PositiveOrZero
    private Long minPrice;
    @PositiveOrZero
    private Long maxPrice;
    private Long groupId;
    private Integer manufacturerId;
    private MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    @PositiveOrZero
    private int pageIndex = 0;
    @Range(min = 1, max = 200)
    private int pageSize = 50;
}
