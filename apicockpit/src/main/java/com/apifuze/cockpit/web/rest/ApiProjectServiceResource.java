package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiProjectServiceService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiProjectServiceDTO;
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
 * REST controller for managing ApiProjectService.
 */
@RestController
@RequestMapping("/api")
public class ApiProjectServiceResource {

    private final Logger log = LoggerFactory.getLogger(ApiProjectServiceResource.class);

    private static final String ENTITY_NAME = "apiProjectService";

    private final ApiProjectServiceService apiProjectServiceService;

    public ApiProjectServiceResource(ApiProjectServiceService apiProjectServiceService) {
        this.apiProjectServiceService = apiProjectServiceService;
    }

    /**
     * POST  /api-project-services : Create a new apiProjectService.
     *
     * @param apiProjectServiceDTO the apiProjectServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiProjectServiceDTO, or with status 400 (Bad Request) if the apiProjectService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-project-services")
    @Timed
    public ResponseEntity<ApiProjectServiceDTO> createApiProjectService(@Valid @RequestBody ApiProjectServiceDTO apiProjectServiceDTO) throws URISyntaxException {
        log.debug("REST request to save ApiProjectService : {}", apiProjectServiceDTO);
        if (apiProjectServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiProjectService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiProjectServiceDTO result = apiProjectServiceService.save(apiProjectServiceDTO);
        return ResponseEntity.created(new URI("/api/api-project-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-project-services : Updates an existing apiProjectService.
     *
     * @param apiProjectServiceDTO the apiProjectServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiProjectServiceDTO,
     * or with status 400 (Bad Request) if the apiProjectServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiProjectServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-project-services")
    @Timed
    public ResponseEntity<ApiProjectServiceDTO> updateApiProjectService(@Valid @RequestBody ApiProjectServiceDTO apiProjectServiceDTO) throws URISyntaxException {
        log.debug("REST request to update ApiProjectService : {}", apiProjectServiceDTO);
        if (apiProjectServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiProjectServiceDTO result = apiProjectServiceService.save(apiProjectServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiProjectServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-project-services : get all the apiProjectServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apiProjectServices in body
     */
    @GetMapping("/api-project-services")
    @Timed
    public ResponseEntity<List<ApiProjectServiceDTO>> getAllApiProjectServices(Pageable pageable) {
        log.debug("REST request to get a page of ApiProjectServices");
        Page<ApiProjectServiceDTO> page = apiProjectServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-project-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-project-services/:id : get the "id" apiProjectService.
     *
     * @param id the id of the apiProjectServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiProjectServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-project-services/{id}")
    @Timed
    public ResponseEntity<ApiProjectServiceDTO> getApiProjectService(@PathVariable Long id) {
        log.debug("REST request to get ApiProjectService : {}", id);
        Optional<ApiProjectServiceDTO> apiProjectServiceDTO = apiProjectServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiProjectServiceDTO);
    }

    /**
     * DELETE  /api-project-services/:id : delete the "id" apiProjectService.
     *
     * @param id the id of the apiProjectServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-project-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiProjectService(@PathVariable Long id) {
        log.debug("REST request to delete ApiProjectService : {}", id);
        apiProjectServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
