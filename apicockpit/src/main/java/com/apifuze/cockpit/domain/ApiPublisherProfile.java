package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ApiPublisherProfile.
 */
@Entity
@Table(name = "api_publisher_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiPublisherProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "api_base_url", nullable = false)
    private String apiBaseUrl;

    @NotNull
    @Column(name = "api_doc_url", nullable = false)
    private String apiDocUrl;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiConsumerProfile> consumerProfiles = new HashSet<>();
    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiServiceGroup> serviceConfigs = new HashSet<>();
    @OneToOne(mappedBy = "apiPublisherProfile")
    @JsonIgnore
    private PlatformUser platformUser;

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

    public ApiPublisherProfile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiPublisherProfile active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public ApiPublisherProfile apiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        return this;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getApiDocUrl() {
        return apiDocUrl;
    }

    public ApiPublisherProfile apiDocUrl(String apiDocUrl) {
        this.apiDocUrl = apiDocUrl;
        return this;
    }

    public void setApiDocUrl(String apiDocUrl) {
        this.apiDocUrl = apiDocUrl;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public ApiPublisherProfile dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<ApiConsumerProfile> getConsumerProfiles() {
        return consumerProfiles;
    }

    public ApiPublisherProfile consumerProfiles(Set<ApiConsumerProfile> apiConsumerProfiles) {
        this.consumerProfiles = apiConsumerProfiles;
        return this;
    }

    public ApiPublisherProfile addConsumerProfiles(ApiConsumerProfile apiConsumerProfile) {
        this.consumerProfiles.add(apiConsumerProfile);
        apiConsumerProfile.setOwner(this);
        return this;
    }

    public ApiPublisherProfile removeConsumerProfiles(ApiConsumerProfile apiConsumerProfile) {
        this.consumerProfiles.remove(apiConsumerProfile);
        apiConsumerProfile.setOwner(null);
        return this;
    }

    public void setConsumerProfiles(Set<ApiConsumerProfile> apiConsumerProfiles) {
        this.consumerProfiles = apiConsumerProfiles;
    }

    public Set<ApiServiceGroup> getServiceConfigs() {
        return serviceConfigs;
    }

    public ApiPublisherProfile serviceConfigs(Set<ApiServiceGroup> apiServiceGroups) {
        this.serviceConfigs = apiServiceGroups;
        return this;
    }

    public ApiPublisherProfile addServiceConfig(ApiServiceGroup apiServiceGroup) {
        this.serviceConfigs.add(apiServiceGroup);
        apiServiceGroup.setOwner(this);
        return this;
    }

    public ApiPublisherProfile removeServiceConfig(ApiServiceGroup apiServiceGroup) {
        this.serviceConfigs.remove(apiServiceGroup);
        apiServiceGroup.setOwner(null);
        return this;
    }

    public void setServiceConfigs(Set<ApiServiceGroup> apiServiceGroups) {
        this.serviceConfigs = apiServiceGroups;
    }

    public PlatformUser getPlatformUser() {
        return platformUser;
    }

    public ApiPublisherProfile platformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
        return this;
    }

    public void setPlatformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
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
        ApiPublisherProfile apiPublisherProfile = (ApiPublisherProfile) o;
        if (apiPublisherProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiPublisherProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiPublisherProfile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", apiBaseUrl='" + getApiBaseUrl() + "'" +
            ", apiDocUrl='" + getApiDocUrl() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
