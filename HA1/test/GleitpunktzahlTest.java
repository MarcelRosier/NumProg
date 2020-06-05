import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GleitpunktzahlTest {

    @BeforeAll
    static void before() {
        Gleitpunktzahl.setSizeMantisse(8);
        Gleitpunktzahl.setSizeExponent(4);
    }

    @Test
    void normalize1() {
        Gleitpunktzahl g = new Gleitpunktzahl();
        g.vorzeichen = true;
        g.mantisse = 0b1111011;
        g.exponent = 2;
        g.normalisiere();
        assertEquals("-1,1110110 * 2^(1-7)", g.toString());
    }

    @Test
    void normalize2() {
        Gleitpunktzahl g = new Gleitpunktzahl();
        g.vorzeichen = false;
        g.mantisse = 0b100010101;
        g.exponent = 6;
        g.normalisiere();
        assertEquals("1,0001011 * 2^(7-7)", g.toString());
    }

    @Test
    void normalize3() {
        Gleitpunktzahl g = new Gleitpunktzahl();
        g.vorzeichen = true;
        g.mantisse = 0b101011001;
        g.exponent = 14;
        g.normalisiere();
        assertEquals("-Inf", g.toString());
    }

    @Test
    void denormalize1() {
        Gleitpunktzahl g = new Gleitpunktzahl();
        g.vorzeichen = true;
        g.mantisse = 0b11110110;
        g.exponent = 2;
        g.normalisiere();

        Gleitpunktzahl f = new Gleitpunktzahl();
        f.vorzeichen = false;
        f.mantisse = 0b10010110;
        f.exponent = 1;
        f.normalisiere();

        Gleitpunktzahl.denormalisiere(f, g);
        assertEquals("-11,1101100 * 2^(1-7)", g.toString());
        assertEquals("1,0010110 * 2^(1-7)", f.toString());
    }

    @Test
    void add1() {
        Gleitpunktzahl g = new Gleitpunktzahl(1.5);
        Gleitpunktzahl f = new Gleitpunktzahl(2.0);
        assertEquals(3.5, g.add(f).toDouble());
    }

    @Test
    void add2() {
        Gleitpunktzahl g = new Gleitpunktzahl(-11.7);
        Gleitpunktzahl f = new Gleitpunktzahl(12.1);
        assertEquals(0.4375, g.add(f).toDouble());
    }

    @Test
    void add3() {
        Gleitpunktzahl g = new Gleitpunktzahl(-1.25);
        Gleitpunktzahl f = new Gleitpunktzahl(-3.5);
        assertEquals(-4.75, g.add(f).toDouble());
    }

    @Test
    void add4() {
        Gleitpunktzahl g = new Gleitpunktzahl(1.25);
        Gleitpunktzahl f = new Gleitpunktzahl(-3.5);
        assertEquals(-2.25, g.add(f).toDouble());
    }

    @Test
    void add5() {
        Gleitpunktzahl g = new Gleitpunktzahl(Double.POSITIVE_INFINITY);
        Gleitpunktzahl f = new Gleitpunktzahl(Double.POSITIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, g.add(f).toDouble());
    }

    @Test
    void add6() {
        Gleitpunktzahl g = new Gleitpunktzahl(Double.POSITIVE_INFINITY);
        Gleitpunktzahl f = new Gleitpunktzahl(Double.NEGATIVE_INFINITY);
        assertEquals(Double.NaN, g.add(f).toDouble());
    }

    @Test
    void add7() {
        Gleitpunktzahl g = new Gleitpunktzahl(Double.POSITIVE_INFINITY);
        Gleitpunktzahl f = new Gleitpunktzahl(7);
        assertEquals(Double.POSITIVE_INFINITY, g.add(f).toDouble());
    }

    @Test
    void add8() {
        Gleitpunktzahl g = new Gleitpunktzahl(Double.NaN);
        Gleitpunktzahl f = new Gleitpunktzahl(7);
        assertEquals(Double.NaN, g.add(f).toDouble());
    }

    @Test
    void sub1() {
        Gleitpunktzahl g = new Gleitpunktzahl(1.25);
        Gleitpunktzahl f = new Gleitpunktzahl(3.5);
        assertEquals(-2.25, g.sub(f).toDouble());
    }
}
