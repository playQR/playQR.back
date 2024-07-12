package com.bandit.domain.manager.service;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.ManagerHandler;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ManagerCommandServiceImpl implements ManagerCommandService {

    private final ManagerRepository managerRepository;
    private final PromotionRepository promotionRepository;
    private final GuestRepository guestRepository;

    @Override
    public Long createManager(Long promotionId, Member member) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));

        boolean managerExists = managerRepository.existsByPromotionAndMember(promotion, member);
        if (managerExists) {
            throw new ManagerHandler(ErrorStatus.MANAGER_ALREADY_EXISTS);
        }

        Manager save = managerRepository.save(Manager.of(promotion, member));
        return save.getId();
    }

    @Override
    public void deleteManager(Long promotionId,  Member member) {
        Manager manager = managerRepository.findByPromotionIdAndMember(promotionId, member)
                .orElseThrow(() -> new ManagerHandler(ErrorStatus.MANAGER_NOT_FOUND));

        managerRepository.delete(manager);
    }

    @Override
    public void completeEntrance(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
        guest.entrance();
        guestRepository.save(guest);
    }

    @Override
    public void cancelReservation(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
//        guest.cancelReservation();
        guestRepository.save(guest);
    }

    @Override
    public void confirmReservation(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
        guest.approve();
        guestRepository.save(guest);
    }
}