package com.celebtwit.htmlui;

/**
 * A standard validation exception.
 */

public class ValidationException extends Exception {

    private String[] validationErrors = new String[0];

    public ValidationException(){

    }

    public ValidationException(String validationError){
        addValidationError(validationError);
    }

    public String getErrorsAsSingleString(){
        StringBuffer mb = new StringBuffer();
        for (int i = 0; i < validationErrors.length; i++) {
            String validationError = validationErrors[i];
            mb.append(validationError + " ");
        }
        return mb.toString();
    }

    public void addErrorsFromAnotherGeneralException(ValidationException errors){
        for (int i = 0; i < errors.getErrors().length; i++) {
            addValidationError(errors.getErrors()[i]);
        }
    }

    public String[] getErrors(){
        return validationErrors;
    }

    public void addValidationError(String validationError){
        if (validationErrors==null){
            validationErrors = new String[0];
        }
        validationErrors = com.celebtwit.util.Str.addToStringArray(validationErrors, validationError);
    }

}
