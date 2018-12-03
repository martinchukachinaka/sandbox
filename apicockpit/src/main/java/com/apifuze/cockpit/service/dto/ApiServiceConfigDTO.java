package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApiServiceConfig entity.
 */
public class ApiServiceConfigDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String code;

    private String description;

    @NotNull
    private String serviceUrl;

    @NotNull
    private String serviceDocUrl;

    private Boolean active;

    @NotNull
    private Instant dateCreated;

    private Long serviceGroupId;

    private String serviceGroupName;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceDocUrl() {
        return serviceDocUrl;
    }

    public void setServiceDocUrl(String serviceDocUrl) {
        this.serviceDocUrl = serviceDocUrl;
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

    public Long getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Long apiServiceGroupId) {
        this.serviceGroupId = apiServiceGroupId;
    }

    public String getServiceGroupName() {
        return serviceGroupName;
    }

    public void setServiceGroupName(String apiServiceGroupName) {
        this.serviceGroupName = apiServiceGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiServiceConfigDTO apiServiceConfigDTO = (ApiServiceConfigDTO) o;
        if (apiServiceConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiServiceConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiServiceConfigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", serviceUrl='" + getServiceUrl() + "'" +
            ", serviceDocUrl='" + getServiceDocUrl() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", serviceGroup=" + getServiceGroupId() +
            ", serviceGroup='" + getServiceGroupName() + "'" +
            "}";
    }
}
