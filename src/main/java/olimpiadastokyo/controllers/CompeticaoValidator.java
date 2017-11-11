package olimpiadastokyo.controllers;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.services.EtapasEnum;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CompeticaoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Competicao.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Competicao c = (Competicao) target;
        boolean foundMatch = false;
        for (EtapasEnum etapa : EtapasEnum.values()) {
            if (c.getEtapa().equals(etapa.getEtapaName()))
                foundMatch = true;
        }

        if (!foundMatch)
            errors.rejectValue("etapa", "etapa.not.exists");
    }
}
