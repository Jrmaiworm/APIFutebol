package br.com.jrmaiworm.web.rest;

import br.com.jrmaiworm.domain.Time;
import br.com.jrmaiworm.repository.TimeRepository;
import br.com.jrmaiworm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jrmaiworm.domain.Time}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class);

    private static final String ENTITY_NAME = "time";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeRepository timeRepository;

    public TimeResource(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    /**
     * {@code POST  /times} : Create a new time.
     *
     * @param time the time to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new time, or with status {@code 400 (Bad Request)} if the time has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to save Time : {}", time);
        if (time.getId() != null) {
            throw new BadRequestAlertException("A new time cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Time result = timeRepository.save(time);
        return ResponseEntity
            .created(new URI("/api/times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /times/:id} : Updates an existing time.
     *
     * @param id the id of the time to save.
     * @param time the time to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated time,
     * or with status {@code 400 (Bad Request)} if the time is not valid,
     * or with status {@code 500 (Internal Server Error)} if the time couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/times/{id}")
    public ResponseEntity<Time> updateTime(@PathVariable(value = "id", required = false) final Long id, @RequestBody Time time)
        throws URISyntaxException {
        log.debug("REST request to update Time : {}, {}", id, time);
        if (time.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, time.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Time result = timeRepository.save(time);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, time.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /times/:id} : Partial updates given fields of an existing time, field will ignore if it is null
     *
     * @param id the id of the time to save.
     * @param time the time to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated time,
     * or with status {@code 400 (Bad Request)} if the time is not valid,
     * or with status {@code 404 (Not Found)} if the time is not found,
     * or with status {@code 500 (Internal Server Error)} if the time couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/times/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Time> partialUpdateTime(@PathVariable(value = "id", required = false) final Long id, @RequestBody Time time)
        throws URISyntaxException {
        log.debug("REST request to partial update Time partially : {}, {}", id, time);
        if (time.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, time.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Time> result = timeRepository
            .findById(time.getId())
            .map(existingTime -> {
                if (time.getEmblema() != null) {
                    existingTime.setEmblema(time.getEmblema());
                }
                if (time.getEmblemaContentType() != null) {
                    existingTime.setEmblemaContentType(time.getEmblemaContentType());
                }
                if (time.getUniforme() != null) {
                    existingTime.setUniforme(time.getUniforme());
                }
                if (time.getUniformeContentType() != null) {
                    existingTime.setUniformeContentType(time.getUniformeContentType());
                }
                if (time.getNomeClube() != null) {
                    existingTime.setNomeClube(time.getNomeClube());
                }
                if (time.getTitulosBrasileiro() != null) {
                    existingTime.setTitulosBrasileiro(time.getTitulosBrasileiro());
                }
                if (time.getTitulosEstadual() != null) {
                    existingTime.setTitulosEstadual(time.getTitulosEstadual());
                }
                if (time.getTitulosLibertadores() != null) {
                    existingTime.setTitulosLibertadores(time.getTitulosLibertadores());
                }
                if (time.getTitulosMundial() != null) {
                    existingTime.setTitulosMundial(time.getTitulosMundial());
                }
                if (time.getMaiorArtilheiro() != null) {
                    existingTime.setMaiorArtilheiro(time.getMaiorArtilheiro());
                }
                if (time.getEstadoOrigem() != null) {
                    existingTime.setEstadoOrigem(time.getEstadoOrigem());
                }
                if (time.getTreinador() != null) {
                    existingTime.setTreinador(time.getTreinador());
                }
                if (time.getPresidente() != null) {
                    existingTime.setPresidente(time.getPresidente());
                }
                if (time.getAnoFundacao() != null) {
                    existingTime.setAnoFundacao(time.getAnoFundacao());
                }

                return existingTime;
            })
            .map(timeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, time.getId().toString())
        );
    }

    /**
     * {@code GET  /times} : get all the times.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of times in body.
     */
    @GetMapping("/times")
    public List<Time> getAllTimes() {
        log.debug("REST request to get all Times");
        return timeRepository.findAll();
    }

    /**
     * {@code GET  /times/:id} : get the "id" time.
     *
     * @param id the id of the time to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the time, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/times/{id}")
    public ResponseEntity<Time> getTime(@PathVariable Long id) {
        log.debug("REST request to get Time : {}", id);
        Optional<Time> time = timeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(time);
    }

    /**
     * {@code DELETE  /times/:id} : delete the "id" time.
     *
     * @param id the id of the time to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        log.debug("REST request to delete Time : {}", id);
        timeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
