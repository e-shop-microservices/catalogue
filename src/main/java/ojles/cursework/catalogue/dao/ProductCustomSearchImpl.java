package ojles.cursework.catalogue.dao;

import lombok.RequiredArgsConstructor;
import ojles.cursework.catalogue.dao.model.ParameterAvailableValues;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.dto.FindProductRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductCustomSearchImpl implements ProductCustomSearch {
    private final EntityManager em;

    @Override
    public List<Product> findProducts(FindProductRequest request) {
        Query query = em.createNativeQuery(buildSearchQuery(request), Product.class);
        return query.getResultList();
    }

    @Override
    public long countProducts(FindProductRequest request) {
        Query query = em.createNativeQuery(buildCountQuery(request));
        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public List<ParameterAvailableValues> findAllParameters(FindProductRequest request) {
        Query query = em.createNativeQuery(buildParameterSearchQuery(request));
        List<ParameterAvailableValues> parameters = new ArrayList<>();
        for (Object result : query.getResultList()) {
            Object[] object = (Object[]) result;
            ParameterAvailableValues parameter = new ParameterAvailableValues();
            parameter.setName((String) object[0]);
            parameter.setValues((String) object[1]);
            parameters.add(parameter);
        }
        return parameters;
    }

    private String buildSearchQuery(FindProductRequest request) {
        StringBuilder queryBuilder = new StringBuilder();

        // build select
        queryBuilder.append("select p.* from product as p ");
        if (!request.getParameters().isEmpty()) {
            queryBuilder.append("inner join product_parameter as pp on p.id = pp.product_id ");
        }

        List<String> predicates = buildPredicates(request);
        if (predicates.isEmpty()) {
            return queryBuilder.toString();
        }

        // build where clause
        queryBuilder.append("where ")
                .append(predicates.get(0))
                .append(" ");
        for (int i = 1; i < predicates.size(); i++) {
            queryBuilder.append("and ")
                    .append(predicates.get(i))
                    .append(" ");
        }

        if (!request.getParameters().isEmpty()) {
            queryBuilder.append("group by p.id having count(p.id) = ")
                    .append(request.getParameters().size());
        }

        // build pagination
        queryBuilder.append(" limit ")
                .append(request.getPageIndex() * request.getPageSize())
                .append(",")
                .append(request.getPageSize());

        return queryBuilder.toString();
    }

    private String buildCountQuery(FindProductRequest request) {
        StringBuilder queryBuilder = new StringBuilder();

        // build select
        queryBuilder.append("select count(*) from (select 1 from product as p ");
        if (!request.getParameters().isEmpty()) {
            queryBuilder.append("inner join product_parameter as pp on p.id = pp.product_id ");
        }

        List<String> predicates = buildPredicates(request);
        if (predicates.isEmpty()) {
            return queryBuilder
                    .append(") as p")
                    .toString();
        }

        // build where clause
        queryBuilder.append("where ")
                .append(predicates.get(0))
                .append(" ");
        for (int i = 1; i < predicates.size(); i++) {
            queryBuilder.append("and ")
                    .append(predicates.get(i))
                    .append(" ");
        }

        if (!request.getParameters().isEmpty()) {
            queryBuilder.append("group by p.id having count(p.id) = ")
                    .append(request.getParameters().size());
        }

        queryBuilder.append(") as p");

        return queryBuilder.toString();
    }

    private String buildParameterSearchQuery(FindProductRequest request) {
        StringBuilder queryBuilder = new StringBuilder();

        // build select
        queryBuilder.append("select pp.name, group_concat(distinct(pp.value) separator ',') ");
        queryBuilder.append("from product as p inner join product_parameter as pp on p.id = pp.product_id ");

        List<String> predicates = buildPredicatesWithoutParameters(request);
        if (predicates.isEmpty()) {
            return queryBuilder.toString();
        }

        // build where clause
        queryBuilder.append("where ")
                .append(predicates.get(0))
                .append(" ");
        for (int i = 1; i < predicates.size(); i++) {
            queryBuilder.append("and ")
                    .append(predicates.get(i))
                    .append(" ");
        }

        // add grouping
        queryBuilder.append("group by pp.name");

        return queryBuilder.toString();
    }

    private List<String> buildPredicatesWithoutParameters(FindProductRequest findRequest) {
        List<String> predicates = new ArrayList<>();
        if (findRequest.getSearchQuery() != null) {
            predicates.add(buildSearchQueryPredicate(findRequest.getSearchQuery()));
        }

        if (findRequest.getMinPrice() != null || findRequest.getMaxPrice() != null) {
            predicates.add(buildPricePredicate(
                    findRequest.getMinPrice(),
                    findRequest.getMaxPrice()
            ));
        }

        if (findRequest.getGroupId() != null) {
            predicates.add(buildGroupIdPredicate(findRequest.getGroupId()));
        }

        if (findRequest.getManufacturerId() != null) {
            predicates.add(buildManufacturerId(findRequest.getManufacturerId()));
        }

        return predicates;
    }

    private List<String> buildPredicates(FindProductRequest findRequest) {
        List<String> predicates = buildPredicatesWithoutParameters(findRequest);

        if (!findRequest.getParameters().isEmpty()) {
            predicates.add(buildParametersPredicate(findRequest.getParameters()));
        }

        return predicates;
    }

    private String buildSearchQueryPredicate(String searchQuery) {
        return String.format("(" +
                        "lower(p.name) like '%%%1$s%%' " +
                        "or lower(p.description) like '%%%1$s%%'" +
                        ")",
                searchQuery.toLowerCase());
    }

    private String buildPricePredicate(Long minPrice, Long maxPrice) {
        if (minPrice != null || maxPrice != null) {
            if (minPrice == null) {
                return "p.price <= " + maxPrice;
            } else if (maxPrice == null) {
                return "p.price >= " + minPrice;
            } else {
                return "p.price between " + minPrice + " and " + maxPrice;
            }
        } else {
            return null;
        }
    }

    private String buildGroupIdPredicate(Long groupId) {
        return "p.group_id = " + groupId;
    }

    private String buildManufacturerId(Integer manufacturerId) {
        return "p.manufacturer_id = " + manufacturerId;
    }

    private String buildParametersPredicate(Map<String, List<String>> parameters) {
        String predicate = parameters.entrySet().stream()
                .map(entry -> {
                    String parameterValues = entry.getValue().stream()
                            .map(v -> "'" + v + "'")
                            .collect(Collectors.joining(","));
                    return String.format("(pp.name='%s' and pp.value in (%s))", entry.getKey(), parameterValues);
                })
                .collect(Collectors.joining(" or "));
        return String.format("(%s)", predicate);
    }
}
