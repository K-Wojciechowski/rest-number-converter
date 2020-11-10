package pl.krzysztofwojciechowski.restnumberconverter.exceptions;

public class UnknownOperationException extends Exception {
    private final String operation;

    public UnknownOperationException(String operation) {
        super(String.format("Unknown operation %s", operation));
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
