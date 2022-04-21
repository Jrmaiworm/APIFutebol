package br.com.jrmaiworm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jrmaiworm.IntegrationTest;
import br.com.jrmaiworm.domain.Time;
import br.com.jrmaiworm.domain.enumeration.EstadoOrigem;
import br.com.jrmaiworm.repository.TimeRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TimeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeResourceIT {

    private static final byte[] DEFAULT_EMBLEMA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EMBLEMA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EMBLEMA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EMBLEMA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_UNIFORME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UNIFORME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UNIFORME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UNIFORME_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME_CLUBE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CLUBE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TITULOS_BRASILEIRO = 1;
    private static final Integer UPDATED_TITULOS_BRASILEIRO = 2;

    private static final Integer DEFAULT_TITULOS_ESTADUAL = 1;
    private static final Integer UPDATED_TITULOS_ESTADUAL = 2;

    private static final Integer DEFAULT_TITULOS_LIBERTADORES = 1;
    private static final Integer UPDATED_TITULOS_LIBERTADORES = 2;

    private static final Integer DEFAULT_TITULOS_MUNDIAL = 1;
    private static final Integer UPDATED_TITULOS_MUNDIAL = 2;

    private static final String DEFAULT_MAIOR_ARTILHEIRO = "AAAAAAAAAA";
    private static final String UPDATED_MAIOR_ARTILHEIRO = "BBBBBBBBBB";

    private static final EstadoOrigem DEFAULT_ESTADO_ORIGEM = EstadoOrigem.RO;
    private static final EstadoOrigem UPDATED_ESTADO_ORIGEM = EstadoOrigem.AC;

    private static final String DEFAULT_TREINADOR = "AAAAAAAAAA";
    private static final String UPDATED_TREINADOR = "BBBBBBBBBB";

    private static final String DEFAULT_PRESIDENTE = "AAAAAAAAAA";
    private static final String UPDATED_PRESIDENTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANO_FUNDACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_FUNDACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/times";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeMockMvc;

    private Time time;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Time createEntity(EntityManager em) {
        Time time = new Time()
            .emblema(DEFAULT_EMBLEMA)
            .emblemaContentType(DEFAULT_EMBLEMA_CONTENT_TYPE)
            .uniforme(DEFAULT_UNIFORME)
            .uniformeContentType(DEFAULT_UNIFORME_CONTENT_TYPE)
            .nomeClube(DEFAULT_NOME_CLUBE)
            .titulosBrasileiro(DEFAULT_TITULOS_BRASILEIRO)
            .titulosEstadual(DEFAULT_TITULOS_ESTADUAL)
            .titulosLibertadores(DEFAULT_TITULOS_LIBERTADORES)
            .titulosMundial(DEFAULT_TITULOS_MUNDIAL)
            .maiorArtilheiro(DEFAULT_MAIOR_ARTILHEIRO)
            .estadoOrigem(DEFAULT_ESTADO_ORIGEM)
            .treinador(DEFAULT_TREINADOR)
            .presidente(DEFAULT_PRESIDENTE)
            .anoFundacao(DEFAULT_ANO_FUNDACAO);
        return time;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Time createUpdatedEntity(EntityManager em) {
        Time time = new Time()
            .emblema(UPDATED_EMBLEMA)
            .emblemaContentType(UPDATED_EMBLEMA_CONTENT_TYPE)
            .uniforme(UPDATED_UNIFORME)
            .uniformeContentType(UPDATED_UNIFORME_CONTENT_TYPE)
            .nomeClube(UPDATED_NOME_CLUBE)
            .titulosBrasileiro(UPDATED_TITULOS_BRASILEIRO)
            .titulosEstadual(UPDATED_TITULOS_ESTADUAL)
            .titulosLibertadores(UPDATED_TITULOS_LIBERTADORES)
            .titulosMundial(UPDATED_TITULOS_MUNDIAL)
            .maiorArtilheiro(UPDATED_MAIOR_ARTILHEIRO)
            .estadoOrigem(UPDATED_ESTADO_ORIGEM)
            .treinador(UPDATED_TREINADOR)
            .presidente(UPDATED_PRESIDENTE)
            .anoFundacao(UPDATED_ANO_FUNDACAO);
        return time;
    }

    @BeforeEach
    public void initTest() {
        time = createEntity(em);
    }

    @Test
    @Transactional
    void createTime() throws Exception {
        int databaseSizeBeforeCreate = timeRepository.findAll().size();
        // Create the Time
        restTimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isCreated());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate + 1);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getEmblema()).isEqualTo(DEFAULT_EMBLEMA);
        assertThat(testTime.getEmblemaContentType()).isEqualTo(DEFAULT_EMBLEMA_CONTENT_TYPE);
        assertThat(testTime.getUniforme()).isEqualTo(DEFAULT_UNIFORME);
        assertThat(testTime.getUniformeContentType()).isEqualTo(DEFAULT_UNIFORME_CONTENT_TYPE);
        assertThat(testTime.getNomeClube()).isEqualTo(DEFAULT_NOME_CLUBE);
        assertThat(testTime.getTitulosBrasileiro()).isEqualTo(DEFAULT_TITULOS_BRASILEIRO);
        assertThat(testTime.getTitulosEstadual()).isEqualTo(DEFAULT_TITULOS_ESTADUAL);
        assertThat(testTime.getTitulosLibertadores()).isEqualTo(DEFAULT_TITULOS_LIBERTADORES);
        assertThat(testTime.getTitulosMundial()).isEqualTo(DEFAULT_TITULOS_MUNDIAL);
        assertThat(testTime.getMaiorArtilheiro()).isEqualTo(DEFAULT_MAIOR_ARTILHEIRO);
        assertThat(testTime.getEstadoOrigem()).isEqualTo(DEFAULT_ESTADO_ORIGEM);
        assertThat(testTime.getTreinador()).isEqualTo(DEFAULT_TREINADOR);
        assertThat(testTime.getPresidente()).isEqualTo(DEFAULT_PRESIDENTE);
        assertThat(testTime.getAnoFundacao()).isEqualTo(DEFAULT_ANO_FUNDACAO);
    }

    @Test
    @Transactional
    void createTimeWithExistingId() throws Exception {
        // Create the Time with an existing ID
        time.setId(1L);

        int databaseSizeBeforeCreate = timeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimes() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get all the timeList
        restTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(time.getId().intValue())))
            .andExpect(jsonPath("$.[*].emblemaContentType").value(hasItem(DEFAULT_EMBLEMA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].emblema").value(hasItem(Base64Utils.encodeToString(DEFAULT_EMBLEMA))))
            .andExpect(jsonPath("$.[*].uniformeContentType").value(hasItem(DEFAULT_UNIFORME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uniforme").value(hasItem(Base64Utils.encodeToString(DEFAULT_UNIFORME))))
            .andExpect(jsonPath("$.[*].nomeClube").value(hasItem(DEFAULT_NOME_CLUBE)))
            .andExpect(jsonPath("$.[*].titulosBrasileiro").value(hasItem(DEFAULT_TITULOS_BRASILEIRO)))
            .andExpect(jsonPath("$.[*].titulosEstadual").value(hasItem(DEFAULT_TITULOS_ESTADUAL)))
            .andExpect(jsonPath("$.[*].titulosLibertadores").value(hasItem(DEFAULT_TITULOS_LIBERTADORES)))
            .andExpect(jsonPath("$.[*].titulosMundial").value(hasItem(DEFAULT_TITULOS_MUNDIAL)))
            .andExpect(jsonPath("$.[*].maiorArtilheiro").value(hasItem(DEFAULT_MAIOR_ARTILHEIRO)))
            .andExpect(jsonPath("$.[*].estadoOrigem").value(hasItem(DEFAULT_ESTADO_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].treinador").value(hasItem(DEFAULT_TREINADOR)))
            .andExpect(jsonPath("$.[*].presidente").value(hasItem(DEFAULT_PRESIDENTE)))
            .andExpect(jsonPath("$.[*].anoFundacao").value(hasItem(DEFAULT_ANO_FUNDACAO.toString())));
    }

    @Test
    @Transactional
    void getTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get the time
        restTimeMockMvc
            .perform(get(ENTITY_API_URL_ID, time.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(time.getId().intValue()))
            .andExpect(jsonPath("$.emblemaContentType").value(DEFAULT_EMBLEMA_CONTENT_TYPE))
            .andExpect(jsonPath("$.emblema").value(Base64Utils.encodeToString(DEFAULT_EMBLEMA)))
            .andExpect(jsonPath("$.uniformeContentType").value(DEFAULT_UNIFORME_CONTENT_TYPE))
            .andExpect(jsonPath("$.uniforme").value(Base64Utils.encodeToString(DEFAULT_UNIFORME)))
            .andExpect(jsonPath("$.nomeClube").value(DEFAULT_NOME_CLUBE))
            .andExpect(jsonPath("$.titulosBrasileiro").value(DEFAULT_TITULOS_BRASILEIRO))
            .andExpect(jsonPath("$.titulosEstadual").value(DEFAULT_TITULOS_ESTADUAL))
            .andExpect(jsonPath("$.titulosLibertadores").value(DEFAULT_TITULOS_LIBERTADORES))
            .andExpect(jsonPath("$.titulosMundial").value(DEFAULT_TITULOS_MUNDIAL))
            .andExpect(jsonPath("$.maiorArtilheiro").value(DEFAULT_MAIOR_ARTILHEIRO))
            .andExpect(jsonPath("$.estadoOrigem").value(DEFAULT_ESTADO_ORIGEM.toString()))
            .andExpect(jsonPath("$.treinador").value(DEFAULT_TREINADOR))
            .andExpect(jsonPath("$.presidente").value(DEFAULT_PRESIDENTE))
            .andExpect(jsonPath("$.anoFundacao").value(DEFAULT_ANO_FUNDACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTime() throws Exception {
        // Get the time
        restTimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Update the time
        Time updatedTime = timeRepository.findById(time.getId()).get();
        // Disconnect from session so that the updates on updatedTime are not directly saved in db
        em.detach(updatedTime);
        updatedTime
            .emblema(UPDATED_EMBLEMA)
            .emblemaContentType(UPDATED_EMBLEMA_CONTENT_TYPE)
            .uniforme(UPDATED_UNIFORME)
            .uniformeContentType(UPDATED_UNIFORME_CONTENT_TYPE)
            .nomeClube(UPDATED_NOME_CLUBE)
            .titulosBrasileiro(UPDATED_TITULOS_BRASILEIRO)
            .titulosEstadual(UPDATED_TITULOS_ESTADUAL)
            .titulosLibertadores(UPDATED_TITULOS_LIBERTADORES)
            .titulosMundial(UPDATED_TITULOS_MUNDIAL)
            .maiorArtilheiro(UPDATED_MAIOR_ARTILHEIRO)
            .estadoOrigem(UPDATED_ESTADO_ORIGEM)
            .treinador(UPDATED_TREINADOR)
            .presidente(UPDATED_PRESIDENTE)
            .anoFundacao(UPDATED_ANO_FUNDACAO);

        restTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTime.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTime))
            )
            .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getEmblema()).isEqualTo(UPDATED_EMBLEMA);
        assertThat(testTime.getEmblemaContentType()).isEqualTo(UPDATED_EMBLEMA_CONTENT_TYPE);
        assertThat(testTime.getUniforme()).isEqualTo(UPDATED_UNIFORME);
        assertThat(testTime.getUniformeContentType()).isEqualTo(UPDATED_UNIFORME_CONTENT_TYPE);
        assertThat(testTime.getNomeClube()).isEqualTo(UPDATED_NOME_CLUBE);
        assertThat(testTime.getTitulosBrasileiro()).isEqualTo(UPDATED_TITULOS_BRASILEIRO);
        assertThat(testTime.getTitulosEstadual()).isEqualTo(UPDATED_TITULOS_ESTADUAL);
        assertThat(testTime.getTitulosLibertadores()).isEqualTo(UPDATED_TITULOS_LIBERTADORES);
        assertThat(testTime.getTitulosMundial()).isEqualTo(UPDATED_TITULOS_MUNDIAL);
        assertThat(testTime.getMaiorArtilheiro()).isEqualTo(UPDATED_MAIOR_ARTILHEIRO);
        assertThat(testTime.getEstadoOrigem()).isEqualTo(UPDATED_ESTADO_ORIGEM);
        assertThat(testTime.getTreinador()).isEqualTo(UPDATED_TREINADOR);
        assertThat(testTime.getPresidente()).isEqualTo(UPDATED_PRESIDENTE);
        assertThat(testTime.getAnoFundacao()).isEqualTo(UPDATED_ANO_FUNDACAO);
    }

    @Test
    @Transactional
    void putNonExistingTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, time.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(time))
            )
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(time))
            )
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeWithPatch() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Update the time using partial update
        Time partialUpdatedTime = new Time();
        partialUpdatedTime.setId(time.getId());

        partialUpdatedTime
            .emblema(UPDATED_EMBLEMA)
            .emblemaContentType(UPDATED_EMBLEMA_CONTENT_TYPE)
            .nomeClube(UPDATED_NOME_CLUBE)
            .titulosBrasileiro(UPDATED_TITULOS_BRASILEIRO)
            .titulosEstadual(UPDATED_TITULOS_ESTADUAL)
            .titulosLibertadores(UPDATED_TITULOS_LIBERTADORES)
            .maiorArtilheiro(UPDATED_MAIOR_ARTILHEIRO)
            .estadoOrigem(UPDATED_ESTADO_ORIGEM)
            .treinador(UPDATED_TREINADOR)
            .presidente(UPDATED_PRESIDENTE)
            .anoFundacao(UPDATED_ANO_FUNDACAO);

        restTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTime))
            )
            .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getEmblema()).isEqualTo(UPDATED_EMBLEMA);
        assertThat(testTime.getEmblemaContentType()).isEqualTo(UPDATED_EMBLEMA_CONTENT_TYPE);
        assertThat(testTime.getUniforme()).isEqualTo(DEFAULT_UNIFORME);
        assertThat(testTime.getUniformeContentType()).isEqualTo(DEFAULT_UNIFORME_CONTENT_TYPE);
        assertThat(testTime.getNomeClube()).isEqualTo(UPDATED_NOME_CLUBE);
        assertThat(testTime.getTitulosBrasileiro()).isEqualTo(UPDATED_TITULOS_BRASILEIRO);
        assertThat(testTime.getTitulosEstadual()).isEqualTo(UPDATED_TITULOS_ESTADUAL);
        assertThat(testTime.getTitulosLibertadores()).isEqualTo(UPDATED_TITULOS_LIBERTADORES);
        assertThat(testTime.getTitulosMundial()).isEqualTo(DEFAULT_TITULOS_MUNDIAL);
        assertThat(testTime.getMaiorArtilheiro()).isEqualTo(UPDATED_MAIOR_ARTILHEIRO);
        assertThat(testTime.getEstadoOrigem()).isEqualTo(UPDATED_ESTADO_ORIGEM);
        assertThat(testTime.getTreinador()).isEqualTo(UPDATED_TREINADOR);
        assertThat(testTime.getPresidente()).isEqualTo(UPDATED_PRESIDENTE);
        assertThat(testTime.getAnoFundacao()).isEqualTo(UPDATED_ANO_FUNDACAO);
    }

    @Test
    @Transactional
    void fullUpdateTimeWithPatch() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeUpdate = timeRepository.findAll().size();

        // Update the time using partial update
        Time partialUpdatedTime = new Time();
        partialUpdatedTime.setId(time.getId());

        partialUpdatedTime
            .emblema(UPDATED_EMBLEMA)
            .emblemaContentType(UPDATED_EMBLEMA_CONTENT_TYPE)
            .uniforme(UPDATED_UNIFORME)
            .uniformeContentType(UPDATED_UNIFORME_CONTENT_TYPE)
            .nomeClube(UPDATED_NOME_CLUBE)
            .titulosBrasileiro(UPDATED_TITULOS_BRASILEIRO)
            .titulosEstadual(UPDATED_TITULOS_ESTADUAL)
            .titulosLibertadores(UPDATED_TITULOS_LIBERTADORES)
            .titulosMundial(UPDATED_TITULOS_MUNDIAL)
            .maiorArtilheiro(UPDATED_MAIOR_ARTILHEIRO)
            .estadoOrigem(UPDATED_ESTADO_ORIGEM)
            .treinador(UPDATED_TREINADOR)
            .presidente(UPDATED_PRESIDENTE)
            .anoFundacao(UPDATED_ANO_FUNDACAO);

        restTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTime))
            )
            .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
        Time testTime = timeList.get(timeList.size() - 1);
        assertThat(testTime.getEmblema()).isEqualTo(UPDATED_EMBLEMA);
        assertThat(testTime.getEmblemaContentType()).isEqualTo(UPDATED_EMBLEMA_CONTENT_TYPE);
        assertThat(testTime.getUniforme()).isEqualTo(UPDATED_UNIFORME);
        assertThat(testTime.getUniformeContentType()).isEqualTo(UPDATED_UNIFORME_CONTENT_TYPE);
        assertThat(testTime.getNomeClube()).isEqualTo(UPDATED_NOME_CLUBE);
        assertThat(testTime.getTitulosBrasileiro()).isEqualTo(UPDATED_TITULOS_BRASILEIRO);
        assertThat(testTime.getTitulosEstadual()).isEqualTo(UPDATED_TITULOS_ESTADUAL);
        assertThat(testTime.getTitulosLibertadores()).isEqualTo(UPDATED_TITULOS_LIBERTADORES);
        assertThat(testTime.getTitulosMundial()).isEqualTo(UPDATED_TITULOS_MUNDIAL);
        assertThat(testTime.getMaiorArtilheiro()).isEqualTo(UPDATED_MAIOR_ARTILHEIRO);
        assertThat(testTime.getEstadoOrigem()).isEqualTo(UPDATED_ESTADO_ORIGEM);
        assertThat(testTime.getTreinador()).isEqualTo(UPDATED_TREINADOR);
        assertThat(testTime.getPresidente()).isEqualTo(UPDATED_PRESIDENTE);
        assertThat(testTime.getAnoFundacao()).isEqualTo(UPDATED_ANO_FUNDACAO);
    }

    @Test
    @Transactional
    void patchNonExistingTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, time.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(time))
            )
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(time))
            )
            .andExpect(status().isBadRequest());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTime() throws Exception {
        int databaseSizeBeforeUpdate = timeRepository.findAll().size();
        time.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(time)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Time in the database
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        int databaseSizeBeforeDelete = timeRepository.findAll().size();

        // Delete the time
        restTimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, time.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Time> timeList = timeRepository.findAll();
        assertThat(timeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
