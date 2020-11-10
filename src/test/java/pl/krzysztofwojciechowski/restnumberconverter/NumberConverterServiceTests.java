package pl.krzysztofwojciechowski.restnumberconverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.InvalidNumberException;
import pl.krzysztofwojciechowski.restnumberconverter.exceptions.UnknownOperationException;
import pl.krzysztofwojciechowski.restnumberconverter.services.NumberConverterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NumberConverterServiceTests {
    @Autowired
    private NumberConverterService numberConverterService;

    @Test
    void testToHex() {
        assertThat(numberConverterService.toHex(0L)).isEqualTo("0");
        assertThat(numberConverterService.toHex(1L)).isEqualTo("1");
        assertThat(numberConverterService.toHex(31L)).isEqualTo("1f");
        assertThat(numberConverterService.toHex(11259375L)).isEqualTo("abcdef");
        assertThat(numberConverterService.toHex(-1L)).isEqualTo("ffffffffffffffff");
    }

    @Test
    void testToRomanBasic() throws InvalidNumberException {
        assertThat(numberConverterService.toRoman(1L)).isEqualTo("I");
        assertThat(numberConverterService.toRoman(3L)).isEqualTo("III");
        assertThat(numberConverterService.toRoman(1998L)).isEqualTo("MCMXCVIII");
        assertThat(numberConverterService.toRoman(3999L)).isEqualTo("MMMCMXCIX");
    }

    @Test
    void testToRomanFull() throws IOException {
        ClassPathResource res = new ClassPathResource("roman-all.txt");
        try (
                InputStream is = res.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
        ) {
            br.lines().forEach((line) -> {
                String[] splits = line.split(",");
                Long number = Long.parseLong(splits[0]);
                String roman = splits[1];
                assertThatCode(() -> numberConverterService.toRoman(number)).doesNotThrowAnyException();
                try {
                    assertThat(numberConverterService.toRoman(number)).isEqualTo(roman);
                } catch (InvalidNumberException e) {
                    // Should never reach this due to the previous assertion
                    // Required due to Java's checked exceptions and forEach not declaring any
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    void testToRomanThrows() {
        assertThatThrownBy(() -> numberConverterService.toRoman(0L)).isExactlyInstanceOf(InvalidNumberException.class);
        assertThatThrownBy(() -> numberConverterService.toRoman(-1L)).isExactlyInstanceOf(InvalidNumberException.class);
        assertThatThrownBy(() -> numberConverterService.toRoman(4000L)).isExactlyInstanceOf(InvalidNumberException.class);
    }

    @Test
    void testConvert() throws InvalidNumberException, UnknownOperationException {
        assertThat(numberConverterService.convert("hex", 31L)).isEqualTo("1f");
        // assertThat(numberConverterService.convert("roman", 31L)).isEqualTo("XXXI");
        assertThatThrownBy(() -> numberConverterService.convert("foo", 31L)).isExactlyInstanceOf(UnknownOperationException.class);
        assertThatThrownBy(() -> numberConverterService.convert("roman", -1L)).isExactlyInstanceOf(InvalidNumberException.class);

    }

}
