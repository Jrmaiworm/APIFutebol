package br.com.jrmaiworm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jrmaiworm.IntegrationTest;
import br.com.jrmaiworm.domain.Jogador;
import br.com.jrmaiworm.domain.enumeration.Posicao;
import br.com.jrmaiworm.repository.JogadorRepository;
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
 * Integration tests for the {@link JogadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JogadorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_IDADE = 1;
    private static final Integer UPDATED_IDADE = 2;

    private static final Posicao DEFAULT_POSICAO = Posicao.Goleiro;
    private static final Posicao UPDATED_POSICAO = Posicao.Zagueiro;

    private static final Integer DEFAULT_CAMISA = 1;
    private static final Integer UPDATED_CAMISA = 2;

    private static final Integer DEFAULT_NUMERODE_GOLS = 1;
    private static final Integer UPDATED_NUMERODE_GOLS = 2;

    private static final String ENTITY_API_URL = "/api/jogadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJogadorMockMvc;

    private Jogador jogador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jogador createEntity(EntityManager em) {
        Jogador jogador = new Jogador()
            .nome(DEFAULT_NOME)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .idade(DEFAULT_IDADE)
            .posicao(DEFAULT_POSICAO)
            .camisa(DEFAULT_CAMISA)
            .numerodeGols(DEFAULT_NUMERODE_GOLS);
        return jogador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jogador createUpdatedEntity(EntityManager em) {
        Jogador jogador = new Jogador()
            .nome(UPDATED_NOME)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .idade(UPDATED_IDADE)
            .posicao(UPDATED_POSICAO)
            .camisa(UPDATED_CAMISA)
            .numerodeGols(UPDATED_NUMERODE_GOLS);
        return jogador;
    }

    @BeforeEach
    public void initTest() {
        jogador = createEntity(em);
    }

    @Test
    @Transactional
    void createJogador() throws Exception {
        int databaseSizeBeforeCreate = jogadorRepository.findAll().size();
        // Create the Jogador
        restJogadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jogador)))
            .andExpect(status().isCreated());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeCreate + 1);
        Jogador testJogador = jogadorList.get(jogadorList.size() - 1);
        assertThat(testJogador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testJogador.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testJogador.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testJogador.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testJogador.getPosicao()).isEqualTo(DEFAULT_POSICAO);
        assertThat(testJogador.getCamisa()).isEqualTo(DEFAULT_CAMISA);
        assertThat(testJogador.getNumerodeGols()).isEqualTo(DEFAULT_NUMERODE_GOLS);
    }

    @Test
    @Transactional
    void createJogadorWithExistingId() throws Exception {
        // Create the Jogador with an existing ID
        jogador.setId(1L);

        int databaseSizeBeforeCreate = jogadorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJogadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jogador)))
            .andExpect(status().isBadRequest());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJogadors() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        // Get all the jogadorList
        restJogadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jogador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].posicao").value(hasItem(DEFAULT_POSICAO.toString())))
            .andExpect(jsonPath("$.[*].camisa").value(hasItem(DEFAULT_CAMISA)))
            .andExpect(jsonPath("$.[*].numerodeGols").value(hasItem(DEFAULT_NUMERODE_GOLS)));
    }

    @Test
    @Transactional
    void getJogador() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        // Get the jogador
        restJogadorMockMvc
            .perform(get(ENTITY_API_URL_ID, jogador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jogador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE))
            .andExpect(jsonPath("$.posicao").value(DEFAULT_POSICAO.toString()))
            .andExpect(jsonPath("$.camisa").value(DEFAULT_CAMISA))
            .andExpect(jsonPath("$.numerodeGols").value(DEFAULT_NUMERODE_GOLS));
    }

    @Test
    @Transactional
    void getNonExistingJogador() throws Exception {
        // Get the jogador
        restJogadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJogador() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();

        // Update the jogador
        Jogador updatedJogador = jogadorRepository.findById(jogador.getId()).get();
        // Disconnect from session so that the updates on updatedJogador are not directly saved in db
        em.detach(updatedJogador);
        updatedJogador
            .nome(UPDATED_NOME)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .idade(UPDATED_IDADE)
            .posicao(UPDATED_POSICAO)
            .camisa(UPDATED_CAMISA)
            .numerodeGols(UPDATED_NUMERODE_GOLS);

        restJogadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJogador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJogador))
            )
            .andExpect(status().isOk());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
        Jogador testJogador = jogadorList.get(jogadorList.size() - 1);
        assertThat(testJogador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testJogador.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testJogador.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testJogador.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testJogador.getPosicao()).isEqualTo(UPDATED_POSICAO);
        assertThat(testJogador.getCamisa()).isEqualTo(UPDATED_CAMISA);
        assertThat(testJogador.getNumerodeGols()).isEqualTo(UPDATED_NUMERODE_GOLS);
    }

    @Test
    @Transactional
    void putNonExistingJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jogador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jogador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jogador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jogador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJogadorWithPatch() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();

        // Update the jogador using partial update
        Jogador partialUpdatedJogador = new Jogador();
        partialUpdatedJogador.setId(jogador.getId());

        partialUpdatedJogador
            .nome(UPDATED_NOME)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .posicao(UPDATED_POSICAO)
            .camisa(UPDATED_CAMISA);

        restJogadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJogador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJogador))
            )
            .andExpect(status().isOk());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
        Jogador testJogador = jogadorList.get(jogadorList.size() - 1);
        assertThat(testJogador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testJogador.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testJogador.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testJogador.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testJogador.getPosicao()).isEqualTo(UPDATED_POSICAO);
        assertThat(testJogador.getCamisa()).isEqualTo(UPDATED_CAMISA);
        assertThat(testJogador.getNumerodeGols()).isEqualTo(DEFAULT_NUMERODE_GOLS);
    }

    @Test
    @Transactional
    void fullUpdateJogadorWithPatch() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();

        // Update the jogador using partial update
        Jogador partialUpdatedJogador = new Jogador();
        partialUpdatedJogador.setId(jogador.getId());

        partialUpdatedJogador
            .nome(UPDATED_NOME)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .idade(UPDATED_IDADE)
            .posicao(UPDATED_POSICAO)
            .camisa(UPDATED_CAMISA)
            .numerodeGols(UPDATED_NUMERODE_GOLS);

        restJogadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJogador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJogador))
            )
            .andExpect(status().isOk());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
        Jogador testJogador = jogadorList.get(jogadorList.size() - 1);
        assertThat(testJogador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testJogador.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testJogador.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testJogador.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testJogador.getPosicao()).isEqualTo(UPDATED_POSICAO);
        assertThat(testJogador.getCamisa()).isEqualTo(UPDATED_CAMISA);
        assertThat(testJogador.getNumerodeGols()).isEqualTo(UPDATED_NUMERODE_GOLS);
    }

    @Test
    @Transactional
    void patchNonExistingJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jogador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jogador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jogador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJogador() throws Exception {
        int databaseSizeBeforeUpdate = jogadorRepository.findAll().size();
        jogador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJogadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jogador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jogador in the database
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJogador() throws Exception {
        // Initialize the database
        jogadorRepository.saveAndFlush(jogador);

        int databaseSizeBeforeDelete = jogadorRepository.findAll().size();

        // Delete the jogador
        restJogadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, jogador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jogador> jogadorList = jogadorRepository.findAll();
        assertThat(jogadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
