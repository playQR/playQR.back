package com.bandit.domain.manager.service;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.member.entity.Member;
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

    @Override
    public void createManager(Long promotionId, Member member) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));

        managerRepository.findByPromotionAndMember(promotion, member)
                .ifPresent(manager -> {
                    throw new ManagerHandler(ErrorStatus.MANAGER_ALREADY_EXISTS);
                });

        Manager manager = Manager.of(promotion, member);
        managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Long promotionId,  Member member) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));

        Manager manager = managerRepository.findByPromotionAndMember(promotion, member)
                .orElseThrow(() -> new ManagerHandler(ErrorStatus.MANAGER_NOT_FOUND));

        managerRepository.delete(manager);
    }
}