import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class GaussTest {


    @Test
    void solve_test1() {
        double[][] M = {{5, -3, 2}, {-2, 6, 1}, {6, 0, 3}};
        double[] b = {2, 4, 6};
        double[] sol = {1, 1, 0};
        assertArrayEquals(sol, Gauss.solve(M, b),1.0E-10);
    }

    @Test
    void solve_test2() {
        double[][] M = {{2, -3, 1}, {1, 1, -2}, {3, -1, -4}};
        double[] b = {10, -6, -5};
        double[] sol = {0.7, -2.1, 2.3};
        assertArrayEquals(sol, Gauss.solve(M, b),1.0E-10);
    }

    @Test
    void solveSing_test1() {
        double[][] M = {{0.99999999999, -0.5}, {1.0, -0.5}};
        double[] sol = {0.5,1};
        assertArrayEquals(sol, Gauss.solveSing(M),1.0E-10);
    }

    @Test
    void solveSing_test2() {
        double[][] M = {{1.74, 3.01, -17.0}, {3.12, 9.0, 1.11},{4.86, 12.01, -15.89}};
        double[] sol = {24.93955781010718, -8.769046707503822, 1.0};
        assertArrayEquals(sol, Gauss.solveSing(M),1.0E-10);
    }
}
