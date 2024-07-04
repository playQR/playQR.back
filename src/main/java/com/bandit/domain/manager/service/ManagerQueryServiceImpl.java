package com.bandit.domain.manager.service;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.ManagerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerQueryServiceImpl implements ManagerQueryService {

    private final ManagerRepository managerRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public List<Member> getManagers(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ManagerHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        List<Manager> managers = managerRepository.findByPromotion(promotion);
        return managers.stream().map(Manager::getMember).collect(Collectors.toList());
    }

}
