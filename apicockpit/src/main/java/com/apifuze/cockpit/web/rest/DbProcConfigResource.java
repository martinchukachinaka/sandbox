package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.DbProcConfigService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.service.dto.DbProcConfigDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DbProcConfig.
 */
@RestController
@RequestMapping("/api")
public class DbProcConfigResource {

    private final Logger log = LoggerFactory.getLogger(DbProcConfigResource.class);

    private static final String ENTITY_NAME = "dbProcConfig";

    private final DbProcConfigService dbProcConfigService;

    public DbProcConfigResource(DbProcConfigService dbProcConfigService) {
        this.dbProcConfigService = dbProcConfigService;
    }

    /**
     * POST  /db-proc-configs : Create a new dbProcConfig.
     *
     * @param dbProcConfigDTO the dbProcConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dbProcConfigDTO, or with status 400 (Bad Request) if the dbProcConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/db-proc-configs")
    @Timed
    public ResponseEntity<DbProcConfigDTO> createDbProcConfig(@Valid @RequestBody DbProcConfigDTO dbProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to save DbProcConfig : {}", dbProcConfigDTO);
        if (dbProcConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new dbProcConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DbProcConfigDTO result = dbProcConfigService.save(dbProcConfigDTO);
        return ResponseEntity.created(new URI("/api/db-proc-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /db-proc-configs : Updates an existing dbProcConfig.
     *
     * @param dbProcConfigDTO the dbProcConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dbProcConfigDTO,
     * or with status 400 (Bad Request) if the dbProcConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the dbProcConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/db-proc-configs")
    @Timed
    public ResponseEntity<DbProcConfigDTO> updateDbProcConfig(@Valid @RequestBody DbProcConfigDTO dbProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to update DbProcConfig : {}", dbProcConfigDTO);
        if (dbProcConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DbProcConfigDTO result = dbProcConfigService.save(dbProcConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dbProcConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /db-proc-configs : get all the dbProcConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dbProcConfigs in body
     */
    @GetMapping("/db-proc-configs")
    @Timed
    public List<DbProcConfigDTO> getAllDbProcConfigs() {
        log.debug("REST request to get all DbProcConfigs");
        return dbProcConfigService.findAll();
    }

    /**
     * GET  /db-proc-configs/:id : get the "id" dbProcConfig.
     *
     * @param id the id of the dbProcConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dbProcConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/db-proc-configs/{id}")
    @Timed
    public ResponseEntity<DbProcConfigDTO> getDbProcConfig(@PathVariable Long id) {
        log.debug("REST request to get DbProcConfig : {}", id);
        Optional<DbProcConfigDTO> dbProcConfigDTO = dbProcConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dbProcConfigDTO);
    }

    /**
     * DELETE  /db-proc-configs/:id : delete the "id" dbProcConfig.
     *
     * @param id the id of the dbProcConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/db-proc-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDbProcConfig(@PathVariable Long id) {
        log.debug("REST request to delete DbProcConfig : {}", id);
        dbProcConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
