package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApiProjectAuthConfig entity.
 */
public class ApiProjectAuthConfigDTO implements Serializable {

    private Long id;

    private String clientId;

    private String clientSecret;

    private Boolean active;

    @NotNull
    private Instant dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = (ApiProjectAuthConfigDTO) o;
        if (apiProjectAuthConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiProjectAuthConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiProjectAuthConfigDTO{" +
            "id=" + getId() +
            ", clientId='" + getClientId() + "'" +
            ", clientSecret='" + getClientSecret() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
