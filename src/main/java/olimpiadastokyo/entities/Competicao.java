package olimpiadastokyo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Competicao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long competicaoId;
    private String modalidade;
    private String local;
    private String adversarioUm;
    private String adversarioDois;
    private String etapa;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public Competicao(){}

    public Competicao(String modalidade, String local, String adversarioUm, String adversarioDois, String etapa, LocalDateTime inicio, LocalDateTime termino) {
        this.modalidade = modalidade;
        this.local = local;
        this.adversarioUm = adversarioUm;
        this.adversarioDois = adversarioDois;
        this.etapa = etapa;
        this.inicio = inicio;
        this.termino = termino;
    }

    public Long getCompeticaoId() {
        return competicaoId;
    }

    public void setCompeticaoId(Long competicaoId) {
        this.competicaoId = competicaoId;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAdversarioUm() {
        return adversarioUm;
    }

    public void setAdversarioUm(String adversarioUm) {
        this.adversarioUm = adversarioUm;
    }

    public String getAdversarioDois() {
        return adversarioDois;
    }

    public void setAdversarioDois(String adversarioDois) {
        this.adversarioDois = adversarioDois;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getTermino() {
        return termino;
    }

    public void setTermino(LocalDateTime termino) {
        this.termino = termino;
    }

    @Override
    public String toString() {
        return "Competicao{" +
                "competicaoId=" + competicaoId +
                ", modalidade='" + modalidade + '\'' +
                ", local='" + local + '\'' +
                ", adversarioUm='" + adversarioUm + '\'' +
                ", adversarioDois='" + adversarioDois + '\'' +
                ", etapa='" + etapa + '\'' +
                ", inicio=" + inicio +
                ", termino=" + termino +
                '}';
    }
}
