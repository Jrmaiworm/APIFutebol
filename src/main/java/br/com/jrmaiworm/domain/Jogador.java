package br.com.jrmaiworm.domain;

import br.com.jrmaiworm.domain.enumeration.Posicao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Schema(description = "The Employee entity.")
@Entity
@Table(name = "jogador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "idade")
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(name = "posicao")
    private Posicao posicao;

    @Column(name = "camisa")
    private Integer camisa;

    @Column(name = "numerode_gols")
    private Integer numerodeGols;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jogadors" }, allowSetters = true)
    private Time time;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jogador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Jogador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Jogador foto(byte[] foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Jogador fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Integer getIdade() {
        return this.idade;
    }

    public Jogador idade(Integer idade) {
        this.setIdade(idade);
        return this;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Posicao getPosicao() {
        return this.posicao;
    }

    public Jogador posicao(Posicao posicao) {
        this.setPosicao(posicao);
        return this;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Integer getCamisa() {
        return this.camisa;
    }

    public Jogador camisa(Integer camisa) {
        this.setCamisa(camisa);
        return this;
    }

    public void setCamisa(Integer camisa) {
        this.camisa = camisa;
    }

    public Integer getNumerodeGols() {
        return this.numerodeGols;
    }

    public Jogador numerodeGols(Integer numerodeGols) {
        this.setNumerodeGols(numerodeGols);
        return this;
    }

    public void setNumerodeGols(Integer numerodeGols) {
        this.numerodeGols = numerodeGols;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Jogador time(Time time) {
        this.setTime(time);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jogador)) {
            return false;
        }
        return id != null && id.equals(((Jogador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jogador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", idade=" + getIdade() +
            ", posicao='" + getPosicao() + "'" +
            ", camisa=" + getCamisa() +
            ", numerodeGols=" + getNumerodeGols() +
            "}";
    }
}
