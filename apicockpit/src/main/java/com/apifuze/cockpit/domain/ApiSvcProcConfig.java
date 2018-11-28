package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.apifuze.cockpit.domain.enumeration.ProcessorType;

/**
 * A ApiSvcProcConfig.
 */
@Entity
@Table(name = "api_svc_proc_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiSvcProcConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "processor_type", nullable = false)
    private ProcessorType processorType;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties("actions")
    private ApiServiceConfig serviceConfig;

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

    public ApiSvcProcConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiSvcProcConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiSvcProcConfig active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public ApiSvcProcConfig dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public ApiSvcProcConfig processorType(ProcessorType processorType) {
        this.processorType = processorType;
        return this;
    }

    public void setProcessorType(ProcessorType processorType) {
        this.processorType = processorType;
    }

    public Integer getOrder() {
        return order;
    }

    public ApiSvcProcConfig order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ApiServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public ApiSvcProcConfig serviceConfig(ApiServiceConfig apiServiceConfig) {
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
        ApiSvcProcConfig apiSvcProcConfig = (ApiSvcProcConfig) o;
        if (apiSvcProcConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiSvcProcConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiSvcProcConfig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", processorType='" + getProcessorType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
