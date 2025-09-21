package org.eternity.reservation.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.eternity.generic.TimeInterval;

public class DiscountCondition {

    public enum ConditionType { PERIOD_CONDITION, SEQUENCE_CONDITION, COMBINED_CONDITION }

    private Long id;
    private Long policyId;
    private ConditionType conditionType;
    private DayOfWeek dayOfWeek;
    private TimeInterval timeInterval;
    private Integer sequence;

    public DiscountCondition() {
    }

    public DiscountCondition(Long policyId, ConditionType conditionType, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Integer sequence) {
        this(null, policyId, conditionType, dayOfWeek, TimeInterval.of(startTime, endTime), sequence);
    }

    public DiscountCondition(Long id, Long policyId, ConditionType conditionType, DayOfWeek dayOfWeek, TimeInterval timeInterval, Integer sequence) {
        this.id = id;
        this.policyId = policyId;
        this.conditionType = conditionType;
        this.dayOfWeek = dayOfWeek;
        this.timeInterval = timeInterval;
        this.sequence = sequence;
    }

    public boolean isSatisfiedBy(Screening screening) {
        if (isPeriodCondition()) {
            if (screening.isPlayedIn(dayOfWeek, timeInterval.getStartTime(), timeInterval.getEndTime())) {
                return true;
            }
        } else if (isSequenceCondition()){
            if (sequence.equals(screening.getSequence())) {
                return true;
            }
        } else if (isCombinedCondition()) {
            if (screening.isPlayedIn(dayOfWeek, timeInterval.getStartTime(), timeInterval.getEndTime()) &&
                    sequence.equals(screening.getSequence())) {
                return true;
            }
        }

        return false;
    }


    public Long getPolicyId() {
        return policyId;
    }


    private boolean isPeriodCondition() {
        return ConditionType.PERIOD_CONDITION.equals(conditionType);
    }

    private boolean isSequenceCondition() {
        return ConditionType.SEQUENCE_CONDITION.equals(conditionType);
    }

    private boolean isCombinedCondition() {
        return ConditionType.COMBINED_CONDITION.equals(conditionType);
    }


}
