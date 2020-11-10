package pl.krzysztofwojciechowski.restnumberconverter.services;

import org.springframework.stereotype.Service;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.InvalidNumberException;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.UnknownOperationException;
import pl.krzysztofwojciechowski.restnumberconverter.models.RomanNumeral;

/* A service that can convert numbers. */
@Service
public class NumberConverterService {
    /** Roman numeral definitions. */
    private static final RomanNumeral[] ROMAN_NUMERALS;

    static {
        ROMAN_NUMERALS = new RomanNumeral[] {
                new RomanNumeral("M", 1000L),
                new RomanNumeral("CM", 900L),
                new RomanNumeral("D", 500L),
                new RomanNumeral("CD", 400L),
                new RomanNumeral("C", 100L),
                new RomanNumeral("XC", 90L),
                new RomanNumeral("L", 50L),
                new RomanNumeral("XL", 40L),
                new RomanNumeral("X", 10L),
                new RomanNumeral("IX", 9L),
                new RomanNumeral("V", 5L),
                new RomanNumeral("IV", 4L),
                new RomanNumeral("I", 1L),
        };
    }

    /**
     * Convert a number to hex.
     * @param number Number to convert
     * @return Number as a hexadecimal string (lowercase)
     */
    public String toHex(Long number) {
        return Long.toHexString(number);
    }

    /**
     * Convert a number to Roman numerals. Supports numbers between 1 and 3999.
     * @param number Number to convert
     * @return Number as a Roman string (UPPERCASE)
     * @throws InvalidNumberException if number is out of range
     */
    public String toRoman(Long number) throws InvalidNumberException {
        if (number <= 0 || number >= 4000) {
            throw new InvalidNumberException("roman", number, String.format("Number must be between 1 and 3999 (got %d)", number));
        }
        StringBuilder sb = new StringBuilder();
        for (RomanNumeral rn : ROMAN_NUMERALS) {
            long appearances = number / rn.getValue();
            for (long i = 0L; i < appearances; i++) {
                sb.append(rn.getCharacters());
            }
            number -= appearances * rn.getValue();
            assert number >= 0;
            if (number <= 0) {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Convert a number to a different representation.
     * @param to     The new representation (hex, roman)
     * @param number Number to convert
     * @return Number in a different representation, as a string
     * @throws UnknownOperationException if the operation type is incorrect
     * @throws InvalidNumberException    if the converter cannot accept the provided number
     */
    public String convert(String to, Long number) throws UnknownOperationException, InvalidNumberException {
        switch (to.toLowerCase()) {
            case "hex":
                return toHex(number);
            case "roman":
                return toRoman(number);
            default:
                throw new UnknownOperationException(to);
        }
    }
}
