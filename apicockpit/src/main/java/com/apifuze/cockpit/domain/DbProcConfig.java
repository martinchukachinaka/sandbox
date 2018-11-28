package com.apifuze.cockpit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DbProcConfig.
 */
@Entity
@Table(name = "db_proc_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DbProcConfig implements Serializable {

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
    @Column(name = "p_script", nullable = false)
    private String pScript;

    @OneToOne    @JoinColumn(unique = true)
    private ApiSvcProcConfig config;

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

    public DbProcConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DbProcConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public DbProcConfig active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public DbProcConfig dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getpScript() {
        return pScript;
    }

    public DbProcConfig pScript(String pScript) {
        this.pScript = pScript;
        return this;
    }

    public void setpScript(String pScript) {
        this.pScript = pScript;
    }

    public ApiSvcProcConfig getConfig() {
        return config;
    }

    public DbProcConfig config(ApiSvcProcConfig apiSvcProcConfig) {
        this.config = apiSvcProcConfig;
        return this;
    }

    public void setConfig(ApiSvcProcConfig apiSvcProcConfig) {
        this.config = apiSvcProcConfig;
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
        DbProcConfig dbProcConfig = (DbProcConfig) o;
        if (dbProcConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dbProcConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DbProcConfig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", pScript='" + getpScript() + "'" +
            "}";
    }
}
