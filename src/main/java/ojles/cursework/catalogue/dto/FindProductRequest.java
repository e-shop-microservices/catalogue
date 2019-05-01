package ojles.cursework.catalogue.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class FindProductRequest {
    private String searchQuery;
    private Long groupId;
    private MultiValueMap<String, String> customProperties;
    @PositiveOrZero
    private int pageIndex = 0;
    @Range(min = 1, max = 200)
    private int pageSize = 50;
}
