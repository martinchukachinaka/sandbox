package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ApiProjectService.
 */
@Entity
@Table(name = "api_project_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiProjectService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ApiServiceConfig serviceConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiProjectService active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public ApiProjectService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public ApiProjectService dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ApiServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public ApiProjectService serviceConfig(ApiServiceConfig apiServiceConfig) {
        this.serviceConfig = apiServiceConfig;
        return this;
    }

    public void setServiceConfig(ApiServiceConfig apiServiceConfig) {
        this.serviceConfig = apiServiceConfig;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiProjectService apiProjectService = (ApiProjectService) o;
        if (apiProjectService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiProjectService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiProjectService{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", name='" + getName() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
