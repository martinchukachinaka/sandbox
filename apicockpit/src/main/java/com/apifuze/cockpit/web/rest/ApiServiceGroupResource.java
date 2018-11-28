package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiServiceGroupService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiServiceGroupDTO;
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
 * REST controller for managing ApiServiceGroup.
 */
@RestController
@RequestMapping("/api")
public class ApiServiceGroupResource {

    private final Logger log = LoggerFactory.getLogger(ApiServiceGroupResource.class);

    private static final String ENTITY_NAME = "apiServiceGroup";

    private final ApiServiceGroupService apiServiceGroupService;

    public ApiServiceGroupResource(ApiServiceGroupService apiServiceGroupService) {
        this.apiServiceGroupService = apiServiceGroupService;
    }

    /**
     * POST  /api-service-groups : Create a new apiServiceGroup.
     *
     * @param apiServiceGroupDTO the apiServiceGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiServiceGroupDTO, or with status 400 (Bad Request) if the apiServiceGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-service-groups")
    @Timed
    public ResponseEntity<ApiServiceGroupDTO> createApiServiceGroup(@Valid @RequestBody ApiServiceGroupDTO apiServiceGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ApiServiceGroup : {}", apiServiceGroupDTO);
        if (apiServiceGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiServiceGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiServiceGroupDTO result = apiServiceGroupService.save(apiServiceGroupDTO);
        return ResponseEntity.created(new URI("/api/api-service-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-service-groups : Updates an existing apiServiceGroup.
     *
     * @param apiServiceGroupDTO the apiServiceGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiServiceGroupDTO,
     * or with status 400 (Bad Request) if the apiServiceGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiServiceGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-service-groups")
    @Timed
    public ResponseEntity<ApiServiceGroupDTO> updateApiServiceGroup(@Valid @RequestBody ApiServiceGroupDTO apiServiceGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ApiServiceGroup : {}", apiServiceGroupDTO);
        if (apiServiceGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiServiceGroupDTO result = apiServiceGroupService.save(apiServiceGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiServiceGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-service-groups : get all the apiServiceGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apiServiceGroups in body
     */
    @GetMapping("/api-service-groups")
    @Timed
    public ResponseEntity<List<ApiServiceGroupDTO>> getAllApiServiceGroups(Pageable pageable) {
        log.debug("REST request to get a page of ApiServiceGroups");
        Page<ApiServiceGroupDTO> page = apiServiceGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-service-groups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-service-groups/:id : get the "id" apiServiceGroup.
     *
     * @param id the id of the apiServiceGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiServiceGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-service-groups/{id}")
    @Timed
    public ResponseEntity<ApiServiceGroupDTO> getApiServiceGroup(@PathVariable Long id) {
        log.debug("REST request to get ApiServiceGroup : {}", id);
        Optional<ApiServiceGroupDTO> apiServiceGroupDTO = apiServiceGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiServiceGroupDTO);
    }

    /**
     * DELETE  /api-service-groups/:id : delete the "id" apiServiceGroup.
     *
     * @param id the id of the apiServiceGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-service-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiServiceGroup(@PathVariable Long id) {
        log.debug("REST request to delete ApiServiceGroup : {}", id);
        apiServiceGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
