package com.cloud.orders.utils;

import com.cloud.orders.domain.Order;
import com.cloud.orders.dto.OrderQueryVO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Used to programmatically construct dynamic queries
 */
@Service
public class SpecificationsUtil {

    private SpecificationsUtil() {

    }

    public Specification constructOrderFilterQuery(OrderQueryVO orderQueryVO) {
        return Specifications.where(createDefaultSpecification())
                .and(createOrderStatusSpecification(orderQueryVO))
                .and(createGroupByProductCountSpecification(orderQueryVO))
                .and(createDiscountSpecification(orderQueryVO));
    }

    private Specification<Order> createDefaultSpecification() {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {
                criteriaQuery.distinct(true);
                return null;
            }
        };
    }

    private Specification<Order> createOrderStatusSpecification(OrderQueryVO orderQueryVO) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {

                if (!StringUtils.isEmpty(orderQueryVO.getOrderStatus())) {
                    return builder.equal(root.get("status"), orderQueryVO.getOrderStatus());
                }
                return null;
            }
        };
    }

    private Specification<Order> createGroupByProductCountSpecification(OrderQueryVO orderQueryVO) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {

                if (!StringUtils.isEmpty(orderQueryVO.getProductCount())) {
                    criteriaQuery.groupBy(root);
                    criteriaQuery.having(builder.gt(builder.count(root.join("products")), orderQueryVO.getProductCount()));
                }
                return null;
            }
        };
    }

    private Specification<Order> createDiscountSpecification(OrderQueryVO orderQueryVO) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder builder) {

                if (orderQueryVO.getDiscount() == null) {
                    return builder.isNotNull(root.get("discount"));
                }
                else {
                    return builder.equal(root.get("discount"), orderQueryVO.getDiscount());
                }
            }
        };
    }

}
