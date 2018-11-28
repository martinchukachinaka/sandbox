package com.apifuze.cockpit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApiServiceGroup entity.
 */
public class ApiServiceGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Long ownerId;

    private String ownerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long apiPublisherProfileId) {
        this.ownerId = apiPublisherProfileId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String apiPublisherProfileName) {
        this.ownerName = apiPublisherProfileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiServiceGroupDTO apiServiceGroupDTO = (ApiServiceGroupDTO) o;
        if (apiServiceGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiServiceGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiServiceGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerName() + "'" +
            "}";
    }
}
