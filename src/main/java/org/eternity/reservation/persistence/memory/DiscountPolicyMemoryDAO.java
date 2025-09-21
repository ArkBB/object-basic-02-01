package org.eternity.reservation.persistence.memory;

import java.util.List;
import org.eternity.reservation.domain.DiscountCondition;
import org.eternity.reservation.domain.DiscountPolicy;
import org.eternity.reservation.persistence.DiscountConditionDAO;
import org.eternity.reservation.persistence.DiscountPolicyDAO;

public class DiscountPolicyMemoryDAO extends InMemoryDAO<DiscountPolicy> implements DiscountPolicyDAO {

    private DiscountConditionDAO discountConditionDAO;

    public DiscountPolicyMemoryDAO(DiscountConditionDAO discountConditionDAO) {
        this.discountConditionDAO = discountConditionDAO;
    }

    @Override
    public DiscountPolicy selectDiscountPolicy(Long movieId) {
        DiscountPolicy discountPolicy = findOne(policy -> policy.getMovieId().equals(movieId));

        if(discountPolicy == null) {
            return null;
        }

        List<DiscountCondition> conditions = discountConditionDAO.selectDiscountConditions(discountPolicy.getId());
        discountPolicy.setDiscountConditions(conditions);
        return discountPolicy;
    }
}
