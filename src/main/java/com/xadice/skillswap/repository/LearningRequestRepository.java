package com.xadice.skillswap.repository;

import com.xadice.skillswap.model.LearningRequest;
import com.xadice.skillswap.model.LearningRequestStatus;
import com.xadice.skillswap.model.Skill;
import com.xadice.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningRequestRepository
        extends JpaRepository<LearningRequest, Long> {

    List<LearningRequest> findAllByRequester(User requester);

    List<LearningRequest> findAllBySkill(Skill skill);

    List<LearningRequest> findAllBySkillOwner(User owner);

    List<LearningRequest> findAllByStatus(
            LearningRequestStatus status
    );

    boolean existsByRequesterAndSkill(
            User requester,
            Skill skill
    );

    boolean existsByRequesterAndSkillOwnerAndStatus(
            User requester,
            User owner,
            LearningRequestStatus status
    );
}