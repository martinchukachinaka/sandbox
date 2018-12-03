package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiPublisherProfileService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiPublisherProfileDTO;
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
 * REST controller for managing ApiPublisherProfile.
 */
@RestController
@RequestMapping("/api")
public class ApiPublisherProfileResource {

    private final Logger log = LoggerFactory.getLogger(ApiPublisherProfileResource.class);

    private static final String ENTITY_NAME = "apiPublisherProfile";

    private final ApiPublisherProfileService apiPublisherProfileService;

    public ApiPublisherProfileResource(ApiPublisherProfileService apiPublisherProfileService) {
        this.apiPublisherProfileService = apiPublisherProfileService;
    }

    /**
     * POST  /api-publisher-profiles : Create a new apiPublisherProfile.
     *
     * @param apiPublisherProfileDTO the apiPublisherProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiPublisherProfileDTO, or with status 400 (Bad Request) if the apiPublisherProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-publisher-profiles")
    @Timed
    public ResponseEntity<ApiPublisherProfileDTO> createApiPublisherProfile(@Valid @RequestBody ApiPublisherProfileDTO apiPublisherProfileDTO) throws URISyntaxException {
        log.debug("REST request to save ApiPublisherProfile : {}", apiPublisherProfileDTO);
        if (apiPublisherProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiPublisherProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiPublisherProfileDTO result = apiPublisherProfileService.save(apiPublisherProfileDTO);
        return ResponseEntity.created(new URI("/api/api-publisher-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-publisher-profiles : Updates an existing apiPublisherProfile.
     *
     * @param apiPublisherProfileDTO the apiPublisherProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiPublisherProfileDTO,
     * or with status 400 (Bad Request) if the apiPublisherProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiPublisherProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-publisher-profiles")
    @Timed
    public ResponseEntity<ApiPublisherProfileDTO> updateApiPublisherProfile(@Valid @RequestBody ApiPublisherProfileDTO apiPublisherProfileDTO) throws URISyntaxException {
        log.debug("REST request to update ApiPublisherProfile : {}", apiPublisherProfileDTO);
        if (apiPublisherProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiPublisherProfileDTO result = apiPublisherProfileService.save(apiPublisherProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiPublisherProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-publisher-profiles : get all the apiPublisherProfiles.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of apiPublisherProfiles in body
     */
    @GetMapping("/api-publisher-profiles")
    @Timed
    public ResponseEntity<List<ApiPublisherProfileDTO>> getAllApiPublisherProfiles(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("platformuser-is-null".equals(filter)) {
            log.debug("REST request to get all ApiPublisherProfiles where platformUser is null");
            return new ResponseEntity<>(apiPublisherProfileService.findAllWherePlatformUserIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApiPublisherProfiles");
        Page<ApiPublisherProfileDTO> page = apiPublisherProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-publisher-profiles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-publisher-profiles/:id : get the "id" apiPublisherProfile.
     *
     * @param id the id of the apiPublisherProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiPublisherProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-publisher-profiles/{id}")
    @Timed
    public ResponseEntity<ApiPublisherProfileDTO> getApiPublisherProfile(@PathVariable Long id) {
        log.debug("REST request to get ApiPublisherProfile : {}", id);
        Optional<ApiPublisherProfileDTO> apiPublisherProfileDTO = apiPublisherProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiPublisherProfileDTO);
    }

    /**
     * DELETE  /api-publisher-profiles/:id : delete the "id" apiPublisherProfile.
     *
     * @param id the id of the apiPublisherProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-publisher-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiPublisherProfile(@PathVariable Long id) {
        log.debug("REST request to delete ApiPublisherProfile : {}", id);
        apiPublisherProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
