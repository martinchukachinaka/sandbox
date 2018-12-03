package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.PlatformUserService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.service.dto.PlatformUserDTO;
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
 * REST controller for managing PlatformUser.
 */
@RestController
@RequestMapping("/api")
public class PlatformUserResource {

    private final Logger log = LoggerFactory.getLogger(PlatformUserResource.class);

    private static final String ENTITY_NAME = "platformUser";

    private final PlatformUserService platformUserService;

    public PlatformUserResource(PlatformUserService platformUserService) {
        this.platformUserService = platformUserService;
    }

    /**
     * POST  /platform-users : Create a new platformUser.
     *
     * @param platformUserDTO the platformUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new platformUserDTO, or with status 400 (Bad Request) if the platformUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/platform-users")
    @Timed
    public ResponseEntity<PlatformUserDTO> createPlatformUser(@Valid @RequestBody PlatformUserDTO platformUserDTO) throws URISyntaxException {
        log.debug("REST request to save PlatformUser : {}", platformUserDTO);
        if (platformUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new platformUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatformUserDTO result = platformUserService.save(platformUserDTO);
        return ResponseEntity.created(new URI("/api/platform-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /platform-users : Updates an existing platformUser.
     *
     * @param platformUserDTO the platformUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated platformUserDTO,
     * or with status 400 (Bad Request) if the platformUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the platformUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/platform-users")
    @Timed
    public ResponseEntity<PlatformUserDTO> updatePlatformUser(@Valid @RequestBody PlatformUserDTO platformUserDTO) throws URISyntaxException {
        log.debug("REST request to update PlatformUser : {}", platformUserDTO);
        if (platformUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlatformUserDTO result = platformUserService.save(platformUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, platformUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /platform-users : get all the platformUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of platformUsers in body
     */
    @GetMapping("/platform-users")
    @Timed
    public List<PlatformUserDTO> getAllPlatformUsers() {
        log.debug("REST request to get all PlatformUsers");
        return platformUserService.findAll();
    }

    /**
     * GET  /platform-users/:id : get the "id" platformUser.
     *
     * @param id the id of the platformUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the platformUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/platform-users/{id}")
    @Timed
    public ResponseEntity<PlatformUserDTO> getPlatformUser(@PathVariable Long id) {
        log.debug("REST request to get PlatformUser : {}", id);
        Optional<PlatformUserDTO> platformUserDTO = platformUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(platformUserDTO);
    }

    /**
     * DELETE  /platform-users/:id : delete the "id" platformUser.
     *
     * @param id the id of the platformUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/platform-users/{id}")
    @Timed
    public ResponseEntity<Void> deletePlatformUser(@PathVariable Long id) {
        log.debug("REST request to delete PlatformUser : {}", id);
        platformUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
