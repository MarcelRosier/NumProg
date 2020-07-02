import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLinear {
	
	@Test
	public void middleValue() {
		double[] x = { 1, 2 };
		double[] y = { 0, 1 };
		double interp_x = 1.5;
		double interp_y = 0.5;
		LinearInterpolation l = new LinearInterpolation();
		l.init(x, y);
		assertEquals(interp_y, l.evaluate(interp_x), 0.0001);
	}
	
	@Test
	public void interpolationCondition() {
		double[] x = { 1, 2, 3 };
		double[] y = { 0, 1, 0.52342 };
		LinearInterpolation l = new LinearInterpolation();
		l.init(x, y);
		for (int i = 0; i < y.length; i++) {
			assertEquals(y[i], l.evaluate(x[i]), 0.0001);
		}
	}
	
	@Test
	public void flat() {
		double[] x = { 1, 2, 3 };
		double[] y = { 5, 5, 5 };
		LinearInterpolation l = new LinearInterpolation();
		l.init(x, y);
		assertEquals(5, l.evaluate(1.5), 0.0001);
		assertEquals(5, l.evaluate(2.5), 0.0001);
	}
	
	@Test
	public void outer() {
		double[] x = { 1, 2 };
		double[] y = { 0.1, 0.3 };
		LinearInterpolation l = new LinearInterpolation();
		l.init(x, y);
		assertEquals(0.1, l.evaluate(0), 0.0001);
		assertEquals(0.3, l.evaluate(5), 0.0001);
	}
	
}

