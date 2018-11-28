package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiProjectAuthConfigService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiProjectAuthConfigDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ApiProjectAuthConfig.
 */
@RestController
@RequestMapping("/api")
public class ApiProjectAuthConfigResource {

    private final Logger log = LoggerFactory.getLogger(ApiProjectAuthConfigResource.class);

    private static final String ENTITY_NAME = "apiProjectAuthConfig";

    private final ApiProjectAuthConfigService apiProjectAuthConfigService;

    public ApiProjectAuthConfigResource(ApiProjectAuthConfigService apiProjectAuthConfigService) {
        this.apiProjectAuthConfigService = apiProjectAuthConfigService;
    }

    /**
     * POST  /api-project-auth-configs : Create a new apiProjectAuthConfig.
     *
     * @param apiProjectAuthConfigDTO the apiProjectAuthConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiProjectAuthConfigDTO, or with status 400 (Bad Request) if the apiProjectAuthConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-project-auth-configs")
    @Timed
    public ResponseEntity<ApiProjectAuthConfigDTO> createApiProjectAuthConfig(@Valid @RequestBody ApiProjectAuthConfigDTO apiProjectAuthConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ApiProjectAuthConfig : {}", apiProjectAuthConfigDTO);
        if (apiProjectAuthConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiProjectAuthConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiProjectAuthConfigDTO result = apiProjectAuthConfigService.save(apiProjectAuthConfigDTO);
        return ResponseEntity.created(new URI("/api/api-project-auth-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-project-auth-configs : Updates an existing apiProjectAuthConfig.
     *
     * @param apiProjectAuthConfigDTO the apiProjectAuthConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiProjectAuthConfigDTO,
     * or with status 400 (Bad Request) if the apiProjectAuthConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiProjectAuthConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-project-auth-configs")
    @Timed
    public ResponseEntity<ApiProjectAuthConfigDTO> updateApiProjectAuthConfig(@Valid @RequestBody ApiProjectAuthConfigDTO apiProjectAuthConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ApiProjectAuthConfig : {}", apiProjectAuthConfigDTO);
        if (apiProjectAuthConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiProjectAuthConfigDTO result = apiProjectAuthConfigService.save(apiProjectAuthConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiProjectAuthConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-project-auth-configs : get all the apiProjectAuthConfigs.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of apiProjectAuthConfigs in body
     */
    @GetMapping("/api-project-auth-configs")
    @Timed
    public ResponseEntity<List<ApiProjectAuthConfigDTO>> getAllApiProjectAuthConfigs(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("project-is-null".equals(filter)) {
            log.debug("REST request to get all ApiProjectAuthConfigs where project is null");
            return new ResponseEntity<>(apiProjectAuthConfigService.findAllWhereProjectIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApiProjectAuthConfigs");
        Page<ApiProjectAuthConfigDTO> page = apiProjectAuthConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-project-auth-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-project-auth-configs/:id : get the "id" apiProjectAuthConfig.
     *
     * @param id the id of the apiProjectAuthConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiProjectAuthConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-project-auth-configs/{id}")
    @Timed
    public ResponseEntity<ApiProjectAuthConfigDTO> getApiProjectAuthConfig(@PathVariable Long id) {
        log.debug("REST request to get ApiProjectAuthConfig : {}", id);
        Optional<ApiProjectAuthConfigDTO> apiProjectAuthConfigDTO = apiProjectAuthConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiProjectAuthConfigDTO);
    }

    /**
     * DELETE  /api-project-auth-configs/:id : delete the "id" apiProjectAuthConfig.
     *
     * @param id the id of the apiProjectAuthConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-project-auth-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiProjectAuthConfig(@PathVariable Long id) {
        log.debug("REST request to delete ApiProjectAuthConfig : {}", id);
        apiProjectAuthConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
