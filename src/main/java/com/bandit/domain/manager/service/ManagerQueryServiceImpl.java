package com.bandit.domain.manager.service;

import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.member.entity.Member;
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
        List<Manager> managers = managerRepository.findByPromotionId(promotionId);
        return managers.stream()
                .map(Manager::getMember)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isManager(Long promotionId, Member member) {
        return managerRepository.findByPromotionIdAndMember(promotionId, member).isPresent();
    }
}
