package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.ApiConsumerProfileService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.web.rest.util.PaginationUtil;
import com.apifuze.cockpit.service.dto.ApiConsumerProfileDTO;
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
 * REST controller for managing ApiConsumerProfile.
 */
@RestController
@RequestMapping("/api")
public class ApiConsumerProfileResource {

    private final Logger log = LoggerFactory.getLogger(ApiConsumerProfileResource.class);

    private static final String ENTITY_NAME = "apiConsumerProfile";

    private final ApiConsumerProfileService apiConsumerProfileService;

    public ApiConsumerProfileResource(ApiConsumerProfileService apiConsumerProfileService) {
        this.apiConsumerProfileService = apiConsumerProfileService;
    }

    /**
     * POST  /api-consumer-profiles : Create a new apiConsumerProfile.
     *
     * @param apiConsumerProfileDTO the apiConsumerProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiConsumerProfileDTO, or with status 400 (Bad Request) if the apiConsumerProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-consumer-profiles")
    @Timed
    public ResponseEntity<ApiConsumerProfileDTO> createApiConsumerProfile(@Valid @RequestBody ApiConsumerProfileDTO apiConsumerProfileDTO) throws URISyntaxException {
        log.debug("REST request to save ApiConsumerProfile : {}", apiConsumerProfileDTO);
        if (apiConsumerProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiConsumerProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiConsumerProfileDTO result = apiConsumerProfileService.save(apiConsumerProfileDTO);
        return ResponseEntity.created(new URI("/api/api-consumer-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-consumer-profiles : Updates an existing apiConsumerProfile.
     *
     * @param apiConsumerProfileDTO the apiConsumerProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiConsumerProfileDTO,
     * or with status 400 (Bad Request) if the apiConsumerProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiConsumerProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-consumer-profiles")
    @Timed
    public ResponseEntity<ApiConsumerProfileDTO> updateApiConsumerProfile(@Valid @RequestBody ApiConsumerProfileDTO apiConsumerProfileDTO) throws URISyntaxException {
        log.debug("REST request to update ApiConsumerProfile : {}", apiConsumerProfileDTO);
        if (apiConsumerProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiConsumerProfileDTO result = apiConsumerProfileService.save(apiConsumerProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiConsumerProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-consumer-profiles : get all the apiConsumerProfiles.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of apiConsumerProfiles in body
     */
    @GetMapping("/api-consumer-profiles")
    @Timed
    public ResponseEntity<List<ApiConsumerProfileDTO>> getAllApiConsumerProfiles(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("platformuser-is-null".equals(filter)) {
            log.debug("REST request to get all ApiConsumerProfiles where platformUser is null");
            return new ResponseEntity<>(apiConsumerProfileService.findAllWherePlatformUserIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApiConsumerProfiles");
        Page<ApiConsumerProfileDTO> page = apiConsumerProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-consumer-profiles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /api-consumer-profiles/:id : get the "id" apiConsumerProfile.
     *
     * @param id the id of the apiConsumerProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiConsumerProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/api-consumer-profiles/{id}")
    @Timed
    public ResponseEntity<ApiConsumerProfileDTO> getApiConsumerProfile(@PathVariable Long id) {
        log.debug("REST request to get ApiConsumerProfile : {}", id);
        Optional<ApiConsumerProfileDTO> apiConsumerProfileDTO = apiConsumerProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiConsumerProfileDTO);
    }

    /**
     * DELETE  /api-consumer-profiles/:id : delete the "id" apiConsumerProfile.
     *
     * @param id the id of the apiConsumerProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-consumer-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiConsumerProfile(@PathVariable Long id) {
        log.debug("REST request to delete ApiConsumerProfile : {}", id);
        apiConsumerProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
