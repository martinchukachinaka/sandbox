package com.apifuze.cockpit.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the ApiProject entity.
 */
public class ApiProjectDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean active;


    private Instant dateCreated;

    private Long apiKeyId;

    private String apiKeyClientId;

    private Set<ApiProjectServiceDTO> apis = new HashSet<>();

    private Set<Long> selectedApiList = new HashSet<>();

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

    public Long getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(Long apiProjectAuthConfigId) {
        this.apiKeyId = apiProjectAuthConfigId;
    }

    public String getApiKeyClientId() {
        return apiKeyClientId;
    }

    public void setApiKeyClientId(String apiProjectAuthConfigClientId) {
        this.apiKeyClientId = apiProjectAuthConfigClientId;
    }

    public Set<ApiProjectServiceDTO> getApis() {
        return apis;
    }

    public void setApis(Set<ApiProjectServiceDTO> apiProjectServices) {
        this.apis = apiProjectServices;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long apiConsumerProfileId) {
        this.ownerId = apiConsumerProfileId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String apiConsumerProfileName) {
        this.ownerName = apiConsumerProfileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiProjectDTO apiProjectDTO = (ApiProjectDTO) o;
        if (apiProjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiProjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", apiKey=" + getApiKeyId() +
            ", apiKey='" + getApiKeyClientId() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerName() + "'" +
            "}";
    }

    public Set<Long> getSelectedApiList() {
        return selectedApiList;
    }

    public void setSelectedApiList(Set<Long> selectedApiList) {
        this.selectedApiList = selectedApiList;
    }
}
