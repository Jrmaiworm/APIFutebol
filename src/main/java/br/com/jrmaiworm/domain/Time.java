package br.com.jrmaiworm.domain;

import br.com.jrmaiworm.domain.enumeration.EstadoOrigem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Time.
 */
@Entity
@Table(name = "time")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "emblema")
    private byte[] emblema;

    @Column(name = "emblema_content_type")
    private String emblemaContentType;

    @Lob
    @Column(name = "uniforme")
    private byte[] uniforme;

    @Column(name = "uniforme_content_type")
    private String uniformeContentType;

    @Column(name = "nome_clube")
    private String nomeClube;

    @Column(name = "titulos_brasileiro")
    private Integer titulosBrasileiro;

    @Column(name = "titulos_estadual")
    private Integer titulosEstadual;

    @Column(name = "titulos_libertadores")
    private Integer titulosLibertadores;

    @Column(name = "titulos_mundial")
    private Integer titulosMundial;

    @Column(name = "maior_artilheiro")
    private String maiorArtilheiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_origem")
    private EstadoOrigem estadoOrigem;

    @Column(name = "treinador")
    private String treinador;

    @Column(name = "presidente")
    private String presidente;

    @Column(name = "ano_fundacao")
    private LocalDate anoFundacao;

    @OneToMany(mappedBy = "time")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "time" }, allowSetters = true)
    private Set<Jogador> jogadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Time id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getEmblema() {
        return this.emblema;
    }

    public Time emblema(byte[] emblema) {
        this.setEmblema(emblema);
        return this;
    }

    public void setEmblema(byte[] emblema) {
        this.emblema = emblema;
    }

    public String getEmblemaContentType() {
        return this.emblemaContentType;
    }

    public Time emblemaContentType(String emblemaContentType) {
        this.emblemaContentType = emblemaContentType;
        return this;
    }

    public void setEmblemaContentType(String emblemaContentType) {
        this.emblemaContentType = emblemaContentType;
    }

    public byte[] getUniforme() {
        return this.uniforme;
    }

    public Time uniforme(byte[] uniforme) {
        this.setUniforme(uniforme);
        return this;
    }

    public void setUniforme(byte[] uniforme) {
        this.uniforme = uniforme;
    }

    public String getUniformeContentType() {
        return this.uniformeContentType;
    }

    public Time uniformeContentType(String uniformeContentType) {
        this.uniformeContentType = uniformeContentType;
        return this;
    }

    public void setUniformeContentType(String uniformeContentType) {
        this.uniformeContentType = uniformeContentType;
    }

    public String getNomeClube() {
        return this.nomeClube;
    }

    public Time nomeClube(String nomeClube) {
        this.setNomeClube(nomeClube);
        return this;
    }

    public void setNomeClube(String nomeClube) {
        this.nomeClube = nomeClube;
    }

    public Integer getTitulosBrasileiro() {
        return this.titulosBrasileiro;
    }

    public Time titulosBrasileiro(Integer titulosBrasileiro) {
        this.setTitulosBrasileiro(titulosBrasileiro);
        return this;
    }

    public void setTitulosBrasileiro(Integer titulosBrasileiro) {
        this.titulosBrasileiro = titulosBrasileiro;
    }

    public Integer getTitulosEstadual() {
        return this.titulosEstadual;
    }

    public Time titulosEstadual(Integer titulosEstadual) {
        this.setTitulosEstadual(titulosEstadual);
        return this;
    }

    public void setTitulosEstadual(Integer titulosEstadual) {
        this.titulosEstadual = titulosEstadual;
    }

    public Integer getTitulosLibertadores() {
        return this.titulosLibertadores;
    }

    public Time titulosLibertadores(Integer titulosLibertadores) {
        this.setTitulosLibertadores(titulosLibertadores);
        return this;
    }

    public void setTitulosLibertadores(Integer titulosLibertadores) {
        this.titulosLibertadores = titulosLibertadores;
    }

    public Integer getTitulosMundial() {
        return this.titulosMundial;
    }

    public Time titulosMundial(Integer titulosMundial) {
        this.setTitulosMundial(titulosMundial);
        return this;
    }

    public void setTitulosMundial(Integer titulosMundial) {
        this.titulosMundial = titulosMundial;
    }

    public String getMaiorArtilheiro() {
        return this.maiorArtilheiro;
    }

    public Time maiorArtilheiro(String maiorArtilheiro) {
        this.setMaiorArtilheiro(maiorArtilheiro);
        return this;
    }

    public void setMaiorArtilheiro(String maiorArtilheiro) {
        this.maiorArtilheiro = maiorArtilheiro;
    }

    public EstadoOrigem getEstadoOrigem() {
        return this.estadoOrigem;
    }

    public Time estadoOrigem(EstadoOrigem estadoOrigem) {
        this.setEstadoOrigem(estadoOrigem);
        return this;
    }

    public void setEstadoOrigem(EstadoOrigem estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    public String getTreinador() {
        return this.treinador;
    }

    public Time treinador(String treinador) {
        this.setTreinador(treinador);
        return this;
    }

    public void setTreinador(String treinador) {
        this.treinador = treinador;
    }

    public String getPresidente() {
        return this.presidente;
    }

    public Time presidente(String presidente) {
        this.setPresidente(presidente);
        return this;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public LocalDate getAnoFundacao() {
        return this.anoFundacao;
    }

    public Time anoFundacao(LocalDate anoFundacao) {
        this.setAnoFundacao(anoFundacao);
        return this;
    }

    public void setAnoFundacao(LocalDate anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public Set<Jogador> getJogadors() {
        return this.jogadors;
    }

    public void setJogadors(Set<Jogador> jogadors) {
        if (this.jogadors != null) {
            this.jogadors.forEach(i -> i.setTime(null));
        }
        if (jogadors != null) {
            jogadors.forEach(i -> i.setTime(this));
        }
        this.jogadors = jogadors;
    }

    public Time jogadors(Set<Jogador> jogadors) {
        this.setJogadors(jogadors);
        return this;
    }

    public Time addJogador(Jogador jogador) {
        this.jogadors.add(jogador);
        jogador.setTime(this);
        return this;
    }

    public Time removeJogador(Jogador jogador) {
        this.jogadors.remove(jogador);
        jogador.setTime(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Time)) {
            return false;
        }
        return id != null && id.equals(((Time) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Time{" +
            "id=" + getId() +
            ", emblema='" + getEmblema() + "'" +
            ", emblemaContentType='" + getEmblemaContentType() + "'" +
            ", uniforme='" + getUniforme() + "'" +
            ", uniformeContentType='" + getUniformeContentType() + "'" +
            ", nomeClube='" + getNomeClube() + "'" +
            ", titulosBrasileiro=" + getTitulosBrasileiro() +
            ", titulosEstadual=" + getTitulosEstadual() +
            ", titulosLibertadores=" + getTitulosLibertadores() +
            ", titulosMundial=" + getTitulosMundial() +
            ", maiorArtilheiro='" + getMaiorArtilheiro() + "'" +
            ", estadoOrigem='" + getEstadoOrigem() + "'" +
            ", treinador='" + getTreinador() + "'" +
            ", presidente='" + getPresidente() + "'" +
            ", anoFundacao='" + getAnoFundacao() + "'" +
            "}";
    }
}
