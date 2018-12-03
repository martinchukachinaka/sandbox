package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApiPublisherProfile entity.
 */
public class ApiPublisherProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean active;

    @NotNull
    private String apiBaseUrl;

    @NotNull
    private String apiDocUrl;

    @NotNull
    private Instant dateCreated;

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

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getApiDocUrl() {
        return apiDocUrl;
    }

    public void setApiDocUrl(String apiDocUrl) {
        this.apiDocUrl = apiDocUrl;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiPublisherProfileDTO apiPublisherProfileDTO = (ApiPublisherProfileDTO) o;
        if (apiPublisherProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiPublisherProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiPublisherProfileDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", apiBaseUrl='" + getApiBaseUrl() + "'" +
            ", apiDocUrl='" + getApiDocUrl() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
