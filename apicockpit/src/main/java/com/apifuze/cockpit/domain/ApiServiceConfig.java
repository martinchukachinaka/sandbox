package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A ApiServiceConfig.
 */
@Entity
@Table(name = "api_service_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiServiceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "service_url", nullable = false)
    private String serviceUrl;

    @NotNull
    @Column(name = "service_doc_url", nullable = false)
    private String serviceDocUrl;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @ManyToOne
    @JsonIgnoreProperties("availableServices")
    private ApiServiceGroup serviceGroup;

    @OneToMany(mappedBy = "serviceConfig")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiSvcProcConfig> actions = new HashSet<>();
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

    public ApiServiceConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ApiServiceConfig code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ApiServiceConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public ApiServiceConfig serviceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceDocUrl() {
        return serviceDocUrl;
    }

    public ApiServiceConfig serviceDocUrl(String serviceDocUrl) {
        this.serviceDocUrl = serviceDocUrl;
        return this;
    }

    public void setServiceDocUrl(String serviceDocUrl) {
        this.serviceDocUrl = serviceDocUrl;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiServiceConfig active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public ApiServiceConfig dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ApiServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public ApiServiceConfig serviceGroup(ApiServiceGroup apiServiceGroup) {
        this.serviceGroup = apiServiceGroup;
        return this;
    }

    public void setServiceGroup(ApiServiceGroup apiServiceGroup) {
        this.serviceGroup = apiServiceGroup;
    }

    public Set<ApiSvcProcConfig> getActions() {
        return actions;
    }

    public ApiServiceConfig actions(Set<ApiSvcProcConfig> apiSvcProcConfigs) {
        this.actions = apiSvcProcConfigs;
        return this;
    }

    public ApiServiceConfig addActions(ApiSvcProcConfig apiSvcProcConfig) {
        this.actions.add(apiSvcProcConfig);
        apiSvcProcConfig.setServiceConfig(this);
        return this;
    }

    public ApiServiceConfig removeActions(ApiSvcProcConfig apiSvcProcConfig) {
        this.actions.remove(apiSvcProcConfig);
        apiSvcProcConfig.setServiceConfig(null);
        return this;
    }

    public void setActions(Set<ApiSvcProcConfig> apiSvcProcConfigs) {
        this.actions = apiSvcProcConfigs;
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
        ApiServiceConfig apiServiceConfig = (ApiServiceConfig) o;
        if (apiServiceConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiServiceConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiServiceConfig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", serviceUrl='" + getServiceUrl() + "'" +
            ", serviceDocUrl='" + getServiceDocUrl() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
