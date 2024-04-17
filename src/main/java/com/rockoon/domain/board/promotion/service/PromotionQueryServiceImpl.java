package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PromotionQueryServiceImpl implements PromotionQueryService{

    private final PromotionRepository promotionRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    @Override
    public List<Promotion> getAllByLatest() {
        return promotionRepository.findPromotionsByOrderByCreatedDateDesc();
    }

    @Override
    public Page<Promotion> getPaginationPromotion(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public List<Promotion> getByTeam(Long teamId) {
        return promotionRepository.findPromotionsByTeamId(teamId);
    }

    @Override
    public List<Promotion> getByMemberBelongToTeam(Member member, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("not found team"));
        validateReaderIsTeamMember(member, team);
        return promotionRepository.findPromotionsByTeamId(teamId);
    }

    private static void validateReaderIsTeamMember(Member member, Team team) {
        if (team.getTeamMembers().stream().noneMatch(teamMember -> teamMember.getMember().equals(member))) {
            throw new RuntimeException("cannot read team Promotion");
        }
    }
}
