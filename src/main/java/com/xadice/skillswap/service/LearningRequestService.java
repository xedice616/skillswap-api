package com.xadice.skillswap.service;

import com.xadice.skillswap.dto.request.LearningRequestRequest;
import com.xadice.skillswap.dto.response.LearningRequestResponse;
import com.xadice.skillswap.exception.DuplicateResourceException;
import com.xadice.skillswap.exception.InvalidOperationException;
import com.xadice.skillswap.exception.ResourceNotFoundException;
import com.xadice.skillswap.model.LearningRequest;
import com.xadice.skillswap.model.LearningRequestStatus;
import com.xadice.skillswap.model.Skill;
import com.xadice.skillswap.model.User;
import com.xadice.skillswap.repository.LearningRequestRepository;
import com.xadice.skillswap.repository.SkillRepository;
import com.xadice.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningRequestService {

    private final LearningRequestRepository learningRequestRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public LearningRequestService(
            LearningRequestRepository learningRequestRepository,
            UserRepository userRepository,
            SkillRepository skillRepository
    ) {
        this.learningRequestRepository = learningRequestRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public LearningRequestResponse createRequest(
            Long skillId,
            LearningRequestRequest request
    ) {

        Skill skill = skillRepository
                .findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + skillId
                        )
                );

        User requester = userRepository
                .findById(request.getRequesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getRequesterId()
                        )
                );

        if (skill.getOwner().getId().equals(requester.getId())) {
            throw new InvalidOperationException(
                    "You cannot send a learning request to your own skill"
            );
        }

        if (learningRequestRepository
                .existsByRequesterAndSkill(requester, skill)) {

            throw new DuplicateResourceException(
                    "You already sent a request for this skill"
            );
        }

        LearningRequest learningRequest = new LearningRequest();

        learningRequest.setMessage(request.getMessage());
        learningRequest.setRequester(requester);
        learningRequest.setSkill(skill);
        learningRequest.setStatus(
                LearningRequestStatus.PENDING
        );

        LearningRequest savedRequest =
                learningRequestRepository.save(learningRequest);

        return mapToResponse(savedRequest);
    }

    public List<LearningRequestResponse> getSentRequests(
            Long userId
    ) {

        User user = findUser(userId);

        return learningRequestRepository
                .findAllByRequester(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<LearningRequestResponse> getReceivedRequests(
            Long ownerId
    ) {

        User owner = findUser(ownerId);

        return learningRequestRepository
                .findAllBySkillOwner(owner)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public LearningRequestResponse acceptRequest(
            Long requestId,
            Long ownerId
    ) {

        LearningRequest request =
                findRequest(requestId);

        validateOwner(request, ownerId);

        if (request.getStatus()
                != LearningRequestStatus.PENDING) {

            throw new InvalidOperationException(
                    "Only pending requests can be accepted"
            );
        }

        request.setStatus(
                LearningRequestStatus.ACCEPTED
        );

        return mapToResponse(
                learningRequestRepository.save(request)
        );
    }

    public LearningRequestResponse rejectRequest(
            Long requestId,
            Long ownerId
    ) {

        LearningRequest request =
                findRequest(requestId);

        validateOwner(request, ownerId);

        if (request.getStatus()
                != LearningRequestStatus.PENDING) {

            throw new InvalidOperationException(
                    "Only pending requests can be rejected"
            );
        }

        request.setStatus(
                LearningRequestStatus.REJECTED
        );

        return mapToResponse(
                learningRequestRepository.save(request)
        );
    }

    public LearningRequestResponse completeRequest(
            Long requestId,
            Long ownerId
    ) {

        LearningRequest request =
                findRequest(requestId);

        validateOwner(request, ownerId);

        if (request.getStatus()
                != LearningRequestStatus.ACCEPTED) {

            throw new InvalidOperationException(
                    "Only accepted requests can be completed"
            );
        }

        request.setStatus(
                LearningRequestStatus.COMPLETED
        );

        return mapToResponse(
                learningRequestRepository.save(request)
        );
    }

    private User findUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    private LearningRequest findRequest(Long id) {
        return learningRequestRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Learning request not found with id: "
                                        + id
                        )
                );
    }

    private void validateOwner(
            LearningRequest request,
            Long ownerId
    ) {

        Long actualOwnerId =
                request
                        .getSkill()
                        .getOwner()
                        .getId();

        if (!actualOwnerId.equals(ownerId)) {
            throw new InvalidOperationException(
                    "Only the skill owner can perform this action"
            );
        }
    }

    private LearningRequestResponse mapToResponse(
            LearningRequest request
    ) {

        return new LearningRequestResponse(
                request.getId(),
                request.getMessage(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getRequester().getId(),
                request.getRequester().getName(),
                request.getSkill().getId(),
                request.getSkill().getTitle()
        );
    }
}