package com.apifuze.cockpit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apifuze.cockpit.service.GrovyProcConfigService;
import com.apifuze.cockpit.web.rest.errors.BadRequestAlertException;
import com.apifuze.cockpit.web.rest.util.HeaderUtil;
import com.apifuze.cockpit.service.dto.GrovyProcConfigDTO;
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
 * REST controller for managing GrovyProcConfig.
 */
@RestController
@RequestMapping("/api")
public class GrovyProcConfigResource {

    private final Logger log = LoggerFactory.getLogger(GrovyProcConfigResource.class);

    private static final String ENTITY_NAME = "grovyProcConfig";

    private final GrovyProcConfigService grovyProcConfigService;

    public GrovyProcConfigResource(GrovyProcConfigService grovyProcConfigService) {
        this.grovyProcConfigService = grovyProcConfigService;
    }

    /**
     * POST  /grovy-proc-configs : Create a new grovyProcConfig.
     *
     * @param grovyProcConfigDTO the grovyProcConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grovyProcConfigDTO, or with status 400 (Bad Request) if the grovyProcConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grovy-proc-configs")
    @Timed
    public ResponseEntity<GrovyProcConfigDTO> createGrovyProcConfig(@Valid @RequestBody GrovyProcConfigDTO grovyProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to save GrovyProcConfig : {}", grovyProcConfigDTO);
        if (grovyProcConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new grovyProcConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrovyProcConfigDTO result = grovyProcConfigService.save(grovyProcConfigDTO);
        return ResponseEntity.created(new URI("/api/grovy-proc-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grovy-proc-configs : Updates an existing grovyProcConfig.
     *
     * @param grovyProcConfigDTO the grovyProcConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grovyProcConfigDTO,
     * or with status 400 (Bad Request) if the grovyProcConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the grovyProcConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grovy-proc-configs")
    @Timed
    public ResponseEntity<GrovyProcConfigDTO> updateGrovyProcConfig(@Valid @RequestBody GrovyProcConfigDTO grovyProcConfigDTO) throws URISyntaxException {
        log.debug("REST request to update GrovyProcConfig : {}", grovyProcConfigDTO);
        if (grovyProcConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrovyProcConfigDTO result = grovyProcConfigService.save(grovyProcConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grovyProcConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grovy-proc-configs : get all the grovyProcConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of grovyProcConfigs in body
     */
    @GetMapping("/grovy-proc-configs")
    @Timed
    public List<GrovyProcConfigDTO> getAllGrovyProcConfigs() {
        log.debug("REST request to get all GrovyProcConfigs");
        return grovyProcConfigService.findAll();
    }

    /**
     * GET  /grovy-proc-configs/:id : get the "id" grovyProcConfig.
     *
     * @param id the id of the grovyProcConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grovyProcConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grovy-proc-configs/{id}")
    @Timed
    public ResponseEntity<GrovyProcConfigDTO> getGrovyProcConfig(@PathVariable Long id) {
        log.debug("REST request to get GrovyProcConfig : {}", id);
        Optional<GrovyProcConfigDTO> grovyProcConfigDTO = grovyProcConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grovyProcConfigDTO);
    }

    /**
     * DELETE  /grovy-proc-configs/:id : delete the "id" grovyProcConfig.
     *
     * @param id the id of the grovyProcConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grovy-proc-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrovyProcConfig(@PathVariable Long id) {
        log.debug("REST request to delete GrovyProcConfig : {}", id);
        grovyProcConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
