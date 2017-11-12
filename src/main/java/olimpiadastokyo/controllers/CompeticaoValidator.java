package olimpiadastokyo.controllers;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.services.EtapasEnum;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompeticaoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Competicao.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {
        Competicao c = (Competicao) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(e, "modalidade", "modalidade.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "local", "local.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "adversarioUm", "adversarioUm.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "adversarioDois", "adversarioDois.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "etapa", "etapa.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "dataInicio", "dataInicio.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "dataTermino", "dataTermino.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "horaInicio", "horaInicio.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "horaTermino", "horaTermino.empty");

        boolean foundMatch = false;
        for (EtapasEnum etapa : EtapasEnum.values()) {
            if (c.getEtapa().equals(etapa.getEtapaName()))
                foundMatch = true;
        }

        if (!foundMatch)
            e.rejectValue("etapa", "etapa.invalid");

    }
}
