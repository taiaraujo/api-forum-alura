package br.com.alura.forum.config.validation;

public class FormErrorDto {

    private String variable;
    private String error;

    public FormErrorDto(String variable, String error) {
        this.variable = variable;
        this.error = error;
    }

    public String getVariable() {
        return variable;
    }

    public String getError() {
        return error;
    }
}
