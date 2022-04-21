package br.com.jrmaiworm.repository;

import br.com.jrmaiworm.domain.Jogador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jogador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {}
