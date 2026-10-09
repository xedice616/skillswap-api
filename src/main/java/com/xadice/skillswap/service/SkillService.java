package com.xadice.skillswap.service;

import com.xadice.skillswap.dto.request.SkillRequest;
import com.xadice.skillswap.dto.response.SkillResponse;
import com.xadice.skillswap.exception.ResourceNotFoundException;
import com.xadice.skillswap.model.Skill;
import com.xadice.skillswap.model.User;
import com.xadice.skillswap.repository.SkillRepository;
import com.xadice.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillService(
            SkillRepository skillRepository,
            UserRepository userRepository
    ) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    public SkillResponse createSkill(SkillRequest request) {

        User owner = userRepository
                .findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getOwnerId()
                        )
                );

        Skill skill = new Skill();

        skill.setTitle(request.getTitle());
        skill.setCategory(request.getCategory());
        skill.setLevel(request.getLevel());
        skill.setDescription(request.getDescription());
        skill.setOwner(owner);

        return mapToResponse(
                skillRepository.save(skill)
        );
    }

    public List<SkillResponse> getAllSkills() {

        return skillRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SkillResponse getSkillById(Long id) {

        Skill skill = skillRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found with id: " + id
                        )
                );

        return mapToResponse(skill);
    }

    public List<SkillResponse> getSkillsByOwner(
            Long ownerId
    ) {

        User owner = userRepository
                .findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + ownerId
                        )
                );

        return skillRepository
                .findAllByOwner(owner)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<SkillResponse> getSkillsByCategory(
            String category
    ) {

        return skillRepository
                .findAllByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SkillResponse mapToResponse(Skill skill) {

        return new SkillResponse(
                skill.getId(),
                skill.getTitle(),
                skill.getCategory(),
                skill.getLevel(),
                skill.getDescription(),
                skill.getOwner().getId(),
                skill.getOwner().getName()
        );
    }
}