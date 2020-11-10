package pl.krzysztofwojciechowski.restnumberconverter.exceptions;

public class InvalidNumberException extends Exception {
    private final String conversion;
    private final Long number;

    public InvalidNumberException(String conversion, Long number, String message) {
        super(message);
        this.conversion = conversion;
        this.number = number;
    }

    public String getConversion() {
        return conversion;
    }

    public Long getNumber() {
        return number;
    }
}
