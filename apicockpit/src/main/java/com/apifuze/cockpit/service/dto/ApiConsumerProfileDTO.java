package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApiConsumerProfile entity.
 */
public class ApiConsumerProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean active;

    @NotNull
    private Instant dateCreated;

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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
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

        ApiConsumerProfileDTO apiConsumerProfileDTO = (ApiConsumerProfileDTO) o;
        if (apiConsumerProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiConsumerProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiConsumerProfileDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerName() + "'" +
            "}";
    }
}
