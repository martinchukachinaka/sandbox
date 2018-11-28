package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Service API defination template
 */
@ApiModel(description = "Service API defination template")
@Entity
@Table(name = "api_service_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiServiceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "serviceGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiServiceConfig> availableServices = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("serviceConfigs")
    private ApiPublisherProfile owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApiServiceGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiServiceGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ApiServiceConfig> getAvailableServices() {
        return availableServices;
    }

    public ApiServiceGroup availableServices(Set<ApiServiceConfig> apiServiceConfigs) {
        this.availableServices = apiServiceConfigs;
        return this;
    }

    public ApiServiceGroup addAvailableServices(ApiServiceConfig apiServiceConfig) {
        this.availableServices.add(apiServiceConfig);
        apiServiceConfig.setServiceGroup(this);
        return this;
    }

    public ApiServiceGroup removeAvailableServices(ApiServiceConfig apiServiceConfig) {
        this.availableServices.remove(apiServiceConfig);
        apiServiceConfig.setServiceGroup(null);
        return this;
    }

    public void setAvailableServices(Set<ApiServiceConfig> apiServiceConfigs) {
        this.availableServices = apiServiceConfigs;
    }

    public ApiPublisherProfile getOwner() {
        return owner;
    }

    public ApiServiceGroup owner(ApiPublisherProfile apiPublisherProfile) {
        this.owner = apiPublisherProfile;
        return this;
    }

    public void setOwner(ApiPublisherProfile apiPublisherProfile) {
        this.owner = apiPublisherProfile;
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
        ApiServiceGroup apiServiceGroup = (ApiServiceGroup) o;
        if (apiServiceGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiServiceGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiServiceGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
