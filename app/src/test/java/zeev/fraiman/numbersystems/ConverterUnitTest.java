package zeev.fraiman.numbersystems;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterUnitTest {

    @Test
    public void testRomanConverter() {
        assertEquals("I", RomanConverter.format(1));
        assertEquals("IV", RomanConverter.format(4));
        assertEquals("IX", RomanConverter.format(9));
        assertEquals("XII", RomanConverter.format(12));
        assertEquals("MCMLXXXIV", RomanConverter.format(1984));
        assertEquals("MMMCMXCIX", RomanConverter.format(3999));

        assertEquals(1, RomanConverter.parse("I"));
        assertEquals(4, RomanConverter.parse("IV"));
        assertEquals(9, RomanConverter.parse("IX"));
        assertEquals(12, RomanConverter.parse("XII"));
        assertEquals(1984, RomanConverter.parse("MCMLXXXIV"));
        assertEquals(3999, RomanConverter.parse("MMMCMXCIX"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRoman() {
        RomanConverter.parse("IIII");
    }

    @Test
    public void testGematriaConverter() {
        assertEquals("א'", GematriaConverter.format(1));
        assertEquals("ה'", GematriaConverter.format(5));
        assertEquals("י'", GematriaConverter.format(10));
        assertEquals("ט\"ו", GematriaConverter.format(15));
        assertEquals("ט\"ז", GematriaConverter.format(16));
        assertEquals("י\"ח", GematriaConverter.format(18));
        assertEquals("תשפ\"ד", GematriaConverter.format(784));
        assertEquals("א'תשפ\"ד", GematriaConverter.format(1784));

        assertEquals(1, GematriaConverter.parse("א'"));
        assertEquals(5, GematriaConverter.parse("ה"));
        assertEquals(10, GematriaConverter.parse("י"));
        assertEquals(15, GematriaConverter.parse("טו"));
        assertEquals(16, GematriaConverter.parse("טז"));
        assertEquals(18, GematriaConverter.parse("יח"));
        assertEquals(784, GematriaConverter.parse("תשפ\"ד"));
        assertEquals(1784, GematriaConverter.parse("א'תשפ\"ד"));
    }

    @Test
    public void testNumberConverter() {
        ConversionResult resultBin = NumberConverter.convertAll("1010", NumeralSystem.BIN);
        assertEquals(10, resultBin.getDecimalValue());
        assertEquals("1010", resultBin.get(NumeralSystem.BIN));
        assertEquals("12", resultBin.get(NumeralSystem.OCT));
        assertEquals("10", resultBin.get(NumeralSystem.DEC));
        assertEquals("A", resultBin.get(NumeralSystem.HEX));
        assertEquals("X", resultBin.get(NumeralSystem.ROMAN));
        assertEquals("י'", resultBin.get(NumeralSystem.GEMATRIA));
    }

    @Test
    public void testExpressionEvaluator() {
        CalculatorResult resRoman = ExpressionEvaluator.evaluate("XII + IX", NumeralSystem.ROMAN);
        assertEquals(21, resRoman.getResultDecimal());
        assertEquals("XII + IX", resRoman.getExpression(NumeralSystem.ROMAN));
        assertEquals("XXI", resRoman.getResult(NumeralSystem.ROMAN));
        assertEquals("21", resRoman.getResult(NumeralSystem.DEC));
        assertEquals("10101", resRoman.getResult(NumeralSystem.BIN));

        CalculatorResult resHex = ExpressionEvaluator.evaluate("A * B", NumeralSystem.HEX);
        assertEquals(110, resHex.getResultDecimal());
        assertEquals("A * B", resHex.getExpression(NumeralSystem.HEX));
        assertEquals("6E", resHex.getResult(NumeralSystem.HEX));
    }
}
