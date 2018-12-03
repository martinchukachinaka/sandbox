package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiServiceConfigService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiServiceConfigDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApiServiceConfig.
 */
@RestController
@RequestMapping("/api")
public class ApiServiceConfigResource {

    private final Logger log = LoggerFactory.getLogger(ApiServiceConfigResource.class);

    private static final String ENTITY_NAME = "apiServiceConfig";

    private final ApiServiceConfigService apiServiceConfigService;

    public ApiServiceConfigResource(ApiServiceConfigService apiServiceConfigService) {
        this.apiServiceConfigService = apiServiceConfigService;
    }

    /**
     * POST  /api-service-configs : Create a new apiServiceConfig.
     *
     * @param apiServiceConfigDTO the apiServiceConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiServiceConfigDTO, or with status 400 (Bad Request) if the apiServiceConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-service-configs")
    @Timed
    public ResponseEntity<ApiServiceConfigDTO> createApiServiceConfig(@Valid @RequestBody ApiServiceConfigDTO apiServiceConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ApiServiceConfig : {}", apiServiceConfigDTO);
        if (apiServiceConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiServiceConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiServiceConfigDTO result = apiServiceConfigService.save(apiServiceConfigDTO);
        return ResponseEntity.created(new URI("/api/api-service-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-service-configs : Updates an existing apiServiceConfig.
     *
     * @param apiServiceConfigDTO the apiServiceConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiServiceConfigDTO,
     * or with status 400 (Bad Request) if the apiServiceConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiServiceConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-service-configs")
    @Timed
    public ResponseEntity<ApiServiceConfigDTO> updateApiServiceConfig(@Valid @RequestBody ApiServiceConfigDTO apiServiceConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ApiServiceConfig : {}", apiServiceConfigDTO);
        if (apiServiceConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiServiceConfigDTO result = apiServiceConfigService.save(apiServiceConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiServiceConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-service-configs : get all the apiServiceConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apiServiceConfigs in body
     */
    @GetMapping("/api-service-configs")
    @Timed
    public ResponseEntity<List<ApiServiceConfigDTO>> getAllApiServiceConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ApiServiceConfigs");
        Page<ApiServiceConfigDTO> page = apiServiceConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-service-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-service-configs/:id : get the "id" apiServiceConfig.
     *
     * @param id the id of the apiServiceConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiServiceConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-service-configs/{id}")
    @Timed
    public ResponseEntity<ApiServiceConfigDTO> getApiServiceConfig(@PathVariable Long id) {
        log.debug("REST request to get ApiServiceConfig : {}", id);
        Optional<ApiServiceConfigDTO> apiServiceConfigDTO = apiServiceConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiServiceConfigDTO);
    }

    /**
     * DELETE  /api-service-configs/:id : delete the "id" apiServiceConfig.
     *
     * @param id the id of the apiServiceConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-service-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiServiceConfig(@PathVariable Long id) {
        log.debug("REST request to delete ApiServiceConfig : {}", id);
        apiServiceConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
