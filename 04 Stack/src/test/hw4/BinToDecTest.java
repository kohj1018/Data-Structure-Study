package hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinToDecTest {

    @Test
    void testBinToDecBaseCase1() {
        assertEquals(0, BinToDec.binToDec("0"));
    }

    @Test
    void testBinToDecBaseCase2() {
        assertEquals(1, BinToDec.binToDec("1"));
    }

    @ParameterizedTest
    @CsvSource({
        "000, 0",
        "001, 1",
        "010, 2",
        "011, 3",
        "100, 4",
        "101, 5",
        "110, 6",
        "111, 7",
    })
    void testBinToDecRecurseCase(String number, int result) {
        assertEquals(result, BinToDec.binToDec(number));
    }

    @Test
    void testBinToDecBaseCaseTR1() {
        assertEquals(0, BinToDec.binToDecTR("0", 0));
    }

    @Test
    void testBinToDecBaseCaseTR2() {
        assertEquals(1, BinToDec.binToDecTR("1", 0));
    }

    @ParameterizedTest
    @CsvSource({
        "000, 0",
        "001, 1",
        "010, 2",
        "011, 3",
        "100, 4",
        "101, 5",
        "110, 6",
        "111, 7",
    })
    void testBinToDecRecurseCaseTR(String number, int result) {
        assertEquals(result, BinToDec.binToDecTR(number,0));
    }
}
