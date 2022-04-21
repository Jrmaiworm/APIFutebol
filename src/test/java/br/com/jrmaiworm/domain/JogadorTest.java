package br.com.jrmaiworm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jrmaiworm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JogadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jogador.class);
        Jogador jogador1 = new Jogador();
        jogador1.setId(1L);
        Jogador jogador2 = new Jogador();
        jogador2.setId(jogador1.getId());
        assertThat(jogador1).isEqualTo(jogador2);
        jogador2.setId(2L);
        assertThat(jogador1).isNotEqualTo(jogador2);
        jogador1.setId(null);
        assertThat(jogador1).isNotEqualTo(jogador2);
    }
}
