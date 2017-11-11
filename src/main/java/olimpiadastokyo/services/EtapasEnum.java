package olimpiadastokyo.services;

import java.util.ArrayList;
import java.util.List;

public enum EtapasEnum {

    ELIMINATORIAS("Eliminatórias"),
    OITAVAS_DE_FINAL("Oitavas de Final"),
    QUARTAS_DE_FINAL("Quartas de Final"),
    SEMIFINAL("Semifinal"),
    FINAL("Final");

    private String etapaName;

    EtapasEnum(String etapaName) {
        this.etapaName = etapaName;
    }

    public String getEtapaName() {
        return etapaName;
    }

    public static List<String> getValues() {
        List<String> list = new ArrayList<>();
        for(EtapasEnum v : values())
            list.add(v.getEtapaName());

        return list;
    }
}
