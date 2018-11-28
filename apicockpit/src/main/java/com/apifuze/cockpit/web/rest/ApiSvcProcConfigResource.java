package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiSvcProcConfigService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.service.dto.ApiSvcProcConfigDTO;
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
 * REST controller for managing ApiSvcProcConfig.
 */
@RestController
@RequestMapping("/api")
public class ApiSvcProcConfigResource {

    private final Logger log = LoggerFactory.getLogger(ApiSvcProcConfigResource.class);

    private static final String ENTITY_NAME = "apiSvcProcConfig";

    private final ApiSvcProcConfigService apiSvcProcConfigService;

    public ApiSvcProcConfigResource(ApiSvcProcConfigService apiSvcProcConfigService) {
        this.apiSvcProcConfigService = apiSvcProcConfigService;
    }

    /**
     * POST  /api-svc-proc-configs : Create a new apiSvcProcConfig.
     *
     * @param apiSvcProcConfigDTO the apiSvcProcConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiSvcProcConfigDTO, or with status 400 (Bad Request) if the apiSvcProcConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-svc-proc-configs")
    @Timed
    public ResponseEntity<ApiSvcProcConfigDTO> createApiSvcProcConfig(@Valid @RequestBody ApiSvcProcConfigDTO apiSvcProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ApiSvcProcConfig : {}", apiSvcProcConfigDTO);
        if (apiSvcProcConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiSvcProcConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiSvcProcConfigDTO result = apiSvcProcConfigService.save(apiSvcProcConfigDTO);
        return ResponseEntity.created(new URI("/api/api-svc-proc-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-svc-proc-configs : Updates an existing apiSvcProcConfig.
     *
     * @param apiSvcProcConfigDTO the apiSvcProcConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiSvcProcConfigDTO,
     * or with status 400 (Bad Request) if the apiSvcProcConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiSvcProcConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-svc-proc-configs")
    @Timed
    public ResponseEntity<ApiSvcProcConfigDTO> updateApiSvcProcConfig(@Valid @RequestBody ApiSvcProcConfigDTO apiSvcProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ApiSvcProcConfig : {}", apiSvcProcConfigDTO);
        if (apiSvcProcConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiSvcProcConfigDTO result = apiSvcProcConfigService.save(apiSvcProcConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiSvcProcConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-svc-proc-configs : get all the apiSvcProcConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apiSvcProcConfigs in body
     */
    @GetMapping("/api-svc-proc-configs")
    @Timed
    public List<ApiSvcProcConfigDTO> getAllApiSvcProcConfigs() {
        log.debug("REST request to get all ApiSvcProcConfigs");
        return apiSvcProcConfigService.findAll();
    }

    /**
     * GET  /api-svc-proc-configs/:id : get the "id" apiSvcProcConfig.
     *
     * @param id the id of the apiSvcProcConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiSvcProcConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-svc-proc-configs/{id}")
    @Timed
    public ResponseEntity<ApiSvcProcConfigDTO> getApiSvcProcConfig(@PathVariable Long id) {
        log.debug("REST request to get ApiSvcProcConfig : {}", id);
        Optional<ApiSvcProcConfigDTO> apiSvcProcConfigDTO = apiSvcProcConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiSvcProcConfigDTO);
    }

    /**
     * DELETE  /api-svc-proc-configs/:id : delete the "id" apiSvcProcConfig.
     *
     * @param id the id of the apiSvcProcConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-svc-proc-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiSvcProcConfig(@PathVariable Long id) {
        log.debug("REST request to delete ApiSvcProcConfig : {}", id);
        apiSvcProcConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
