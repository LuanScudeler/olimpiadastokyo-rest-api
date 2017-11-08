package olimpiadastokyo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competicao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long competicaoId;
    private String modalidade;
    private String local;
    private String adversarioUm;
    private String adversarioDois;
    private String etapa;
    private String inicio;
    private String termino;
}
