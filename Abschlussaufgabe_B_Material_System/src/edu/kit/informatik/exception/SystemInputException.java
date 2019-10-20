package edu.kit.informatik.exception;

/**
 * This exception is used if the user enters a invalid system input,
 * for example if the input creates a loop or the specified amount is too high.
 * 
 * @author
 * @version 1.0
 */
public class SystemInputException extends Exception {

    private static final long serialVersionUID = 1273589374058308457L;

    /**
     * Exception gets thrown if the user input value is invalid.
     * 
     * @param message The error message.
     */
    public SystemInputException(String message) {
        super(message);
    }

}
