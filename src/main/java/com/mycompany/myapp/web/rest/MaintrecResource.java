package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Maintrec;

import com.mycompany.myapp.repository.MaintrecRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Maintrec.
 */
@RestController
@RequestMapping("/api")
public class MaintrecResource {

    private final Logger log = LoggerFactory.getLogger(MaintrecResource.class);
        
    @Inject
    private MaintrecRepository maintrecRepository;

    /**
     * POST  /maintrecs : Create a new maintrec.
     *
     * @param maintrec the maintrec to create
     * @return the ResponseEntity with status 201 (Created) and with body the new maintrec, or with status 400 (Bad Request) if the maintrec has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maintrecs")
    @Timed
    public ResponseEntity<Maintrec> createMaintrec(@RequestBody Maintrec maintrec) throws URISyntaxException {
        log.debug("REST request to save Maintrec : {}", maintrec);
        if (maintrec.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("maintrec", "idexists", "A new maintrec cannot already have an ID")).body(null);
        }
        Maintrec result = maintrecRepository.save(maintrec);
        return ResponseEntity.created(new URI("/api/maintrecs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("maintrec", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maintrecs : Updates an existing maintrec.
     *
     * @param maintrec the maintrec to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated maintrec,
     * or with status 400 (Bad Request) if the maintrec is not valid,
     * or with status 500 (Internal Server Error) if the maintrec couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maintrecs")
    @Timed
    public ResponseEntity<Maintrec> updateMaintrec(@RequestBody Maintrec maintrec) throws URISyntaxException {
        log.debug("REST request to update Maintrec : {}", maintrec);
        if (maintrec.getId() == null) {
            return createMaintrec(maintrec);
        }
        Maintrec result = maintrecRepository.save(maintrec);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("maintrec", maintrec.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maintrecs : get all the maintrecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of maintrecs in body
     */
    @GetMapping("/maintrecs")
    @Timed
    public List<Maintrec> getAllMaintrecs() {
        log.debug("REST request to get all Maintrecs");
        List<Maintrec> maintrecs = maintrecRepository.findAll();
        return maintrecs;
    }

    /**
     * GET  /maintrecs/:id : get the "id" maintrec.
     *
     * @param id the id of the maintrec to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the maintrec, or with status 404 (Not Found)
     */
    @GetMapping("/maintrecs/{id}")
    @Timed
    public ResponseEntity<Maintrec> getMaintrec(@PathVariable Long id) {
        log.debug("REST request to get Maintrec : {}", id);
        Maintrec maintrec = maintrecRepository.findOne(id);
        return Optional.ofNullable(maintrec)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /maintrecs/:id : delete the "id" maintrec.
     *
     * @param id the id of the maintrec to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maintrecs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaintrec(@PathVariable Long id) {
        log.debug("REST request to delete Maintrec : {}", id);
        maintrecRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("maintrec", id.toString())).build();
    }

}
