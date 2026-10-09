package com.xadice.skillswap.controller;

import com.xadice.skillswap.dto.request.SkillRequest;
import com.xadice.skillswap.dto.response.SkillResponse;
import com.xadice.skillswap.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(
            @Valid @RequestBody SkillRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(skillService.createSkill(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills(
            @RequestParam(required = false) String category
    ) {

        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(
                    skillService.getSkillsByCategory(category)
            );
        }

        return ResponseEntity.ok(
                skillService.getAllSkills()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                skillService.getSkillById(id)
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SkillResponse>> getSkillsByOwner(
            @PathVariable Long ownerId
    ) {

        return ResponseEntity.ok(
                skillService.getSkillsByOwner(ownerId)
        );
    }
}