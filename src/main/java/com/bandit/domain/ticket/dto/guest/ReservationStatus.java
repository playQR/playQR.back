package com.bandit.domain.ticket.dto.guest;

import com.bandit.domain.ticket.entity.Guest;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;

public enum ReservationStatus {
    BEFORE_CONFIRMATION, AFTER_CONFIRMATION, CHECKED_IN;

    public static ReservationStatus getStatus(Guest guest) {
        Boolean isEntered = guest.getIsEntered();
        Boolean isApproved = guest.getIsApproved();
        if (!isApproved && !isEntered) {
            return BEFORE_CONFIRMATION;
        } else if (isApproved && !isEntered) {
            return AFTER_CONFIRMATION;
        } else if (isApproved && isEntered) {
            return CHECKED_IN;
        }
        throw new GuestHandler(ErrorStatus.GUEST_RESERVATION_TYPE_IS_NOT_AVAILABLE);
    }
}
