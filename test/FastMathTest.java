import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastMathTest {

    @BeforeAll
    static void setUp() {
        Gleitpunktzahl.setSizeExponent(4);
        Gleitpunktzahl.setSizeMantisse(8);
    }



    @Test
    void invSqrt1() {
        FastMath.setMagic(1024);
        assertEquals(0.056396484375, FastMath.invSqrt(new Gleitpunktzahl(11.1)).toDouble());
    }

    @Test
    void invSqrt2() {
        FastMath.setMagic(1300);
        assertEquals(0.3203125, FastMath.invSqrt(new Gleitpunktzahl(7)).toDouble());
    }

    @Test
    void invSqrt3() {
        FastMath.setMagic(1300);
        assertEquals(Double.NaN, FastMath.invSqrt(new Gleitpunktzahl(-1)).toDouble());
    }

    @Test
    void invSqrt4() {
        FastMath.setMagic(1300);
        assertEquals(Double.NaN, FastMath.invSqrt(new Gleitpunktzahl(Double.NaN)).toDouble());
    }

    @Test
    void invSqrt5() {
        FastMath.setMagic(1300);
        assertEquals(0, FastMath.invSqrt(new Gleitpunktzahl(Double.POSITIVE_INFINITY)).toDouble());
    }

    @Test
    void invSqrt6() {
        FastMath.setMagic(1300);
        assertEquals(Double.NaN, FastMath.invSqrt(new Gleitpunktzahl(Double.NEGATIVE_INFINITY)).toDouble());
    }
}
