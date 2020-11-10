package pl.krzysztofwojciechowski.restnumberconverter.models;

/** A Roman numeral definition. Numbers can be built out of those basic definitions. */
public class RomanNumeral {
    private final String characters;
    private final long value;

    public RomanNumeral(String characters, long value) {
        this.characters = characters;
        this.value = value;
    }

    public String getCharacters() {
        return characters;
    }

    public long getValue() {
        return value;
    }
}
