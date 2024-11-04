package br.com.fiap.nextia.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FidelidadeClienteValidator implements ConstraintValidator<FidelidadeCliente, String> {
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("AFILIADO") || value.equals("NÃOFILIADO");
    }

}
