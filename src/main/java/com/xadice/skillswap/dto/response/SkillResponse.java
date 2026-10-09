package com.xadice.skillswap.dto.response;

import com.xadice.skillswap.model.SkillLevel;

public class SkillResponse {

    private Long id;
    private String title;
    private String category;
    private SkillLevel level;
    private String description;
    private Long ownerId;
    private String ownerName;

    public SkillResponse(
            Long id,
            String title,
            String category,
            SkillLevel level,
            String description,
            Long ownerId,
            String ownerName
    ) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.level = level;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}