package pl.krzysztofwojciechowski.restnumberconverter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.InvalidNumberException;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.UnknownOperationException;
import pl.krzysztofwojciechowski.restnumberconverter.services.NumberConverterService;

/** The controller that provides number conversion. */
@RestController
public class NumberConverterController {
    private final NumberConverterService numberConverterService;

    /** Initialize the controller and inject the service. */
    public NumberConverterController(NumberConverterService numberConverterService) {
        this.numberConverterService = numberConverterService;
    }

    /**
     * Convert a number to a different representation.
     * @param to     The new representation (hex, roman)
     * @param number Number to convert
     * @return Number in a different representation, as a string
     * @throws UnknownOperationException if the operation type is incorrect
     * @throws InvalidNumberException    if the converter cannot accept the provided number
     */
    @GetMapping("/convert/{to}/{number}")
    public String convert(@PathVariable("to") String to, @PathVariable("number") Long number) throws UnknownOperationException, InvalidNumberException {
        return numberConverterService.convert(to, number);
    }

    /** Handle an InvalidNumberException in a friendly way. */
    @ExceptionHandler(InvalidNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleInvalidNumber(InvalidNumberException ex) {
        return ex.getMessage();
    }

    /** Handle an UnknownOperationException in a friendly way. */
    @ExceptionHandler(UnknownOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleUnknownOperation(UnknownOperationException ex) {
        return ex.getMessage();
    }
}
