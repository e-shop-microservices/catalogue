package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FindProductRequest {
    private String searchQuery;
    @PositiveOrZero
    private Long minPrice;
    @PositiveOrZero
    private Long maxPrice;
    private Long groupId;
    private Integer manufacturerId;
    private Map<String, String> parameters = new HashMap<>();
    @PositiveOrZero
    private int pageIndex = 0;
    @Range(min = 1, max = 200)
    private int pageSize = 50;
}
