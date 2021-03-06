package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiProjectService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiProjectDTO;
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
 * REST controller for managing ApiProject.
 */
@RestController
@RequestMapping("/api")
public class ApiProjectResource {

    private final Logger log = LoggerFactory.getLogger(ApiProjectResource.class);

    private static final String ENTITY_NAME = "apiProject";

    private final ApiProjectService apiProjectService;

    public ApiProjectResource(ApiProjectService apiProjectService) {
        this.apiProjectService = apiProjectService;
    }

    /**
     * POST  /api-projects : Create a new apiProject.
     *
     * @param apiProjectDTO the apiProjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiProjectDTO, or with status 400 (Bad Request) if the apiProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-projects")
    @Timed
    public ResponseEntity<ApiProjectDTO> createApiProject(@Valid @RequestBody ApiProjectDTO apiProjectDTO) throws URISyntaxException {
        log.debug("REST request to save ApiProject : {}", apiProjectDTO);
        if (apiProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiProjectDTO result = apiProjectService.save(apiProjectDTO);
        return ResponseEntity.created(new URI("/api/api-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-projects : Updates an existing apiProject.
     *
     * @param apiProjectDTO the apiProjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiProjectDTO,
     * or with status 400 (Bad Request) if the apiProjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiProjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-projects")
    @Timed
    public ResponseEntity<ApiProjectDTO> updateApiProject(@Valid @RequestBody ApiProjectDTO apiProjectDTO) throws URISyntaxException {
        log.debug("REST request to update ApiProject : {}", apiProjectDTO);
        if (apiProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiProjectDTO result = apiProjectService.save(apiProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-projects : get all the apiProjects.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of apiProjects in body
     */
    @GetMapping("/api-projects")
    @Timed
    public ResponseEntity<List<ApiProjectDTO>> getAllApiProjects(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ApiProjects");
        Page<ApiProjectDTO> page;
        if (eagerload) {
            page = apiProjectService.findAllWithEagerRelationships(pageable);
        } else {
            page = apiProjectService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/api-projects?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-projects/:id : get the "id" apiProject.
     *
     * @param id the id of the apiProjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiProjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-projects/{id}")
    @Timed
    public ResponseEntity<ApiProjectDTO> getApiProject(@PathVariable Long id) {
        log.debug("REST request to get ApiProject : {}", id);
        Optional<ApiProjectDTO> apiProjectDTO = apiProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiProjectDTO);
    }

    /**
     * DELETE  /api-projects/:id : delete the "id" apiProject.
     *
     * @param id the id of the apiProjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiProject(@PathVariable Long id) {
        log.debug("REST request to delete ApiProject : {}", id);
        apiProjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
