package dft;

/**
 * Schnelle inverse Fourier-Transformation
 *
 * @author Sebastian Rettenberger
 */
public class IFFT {
    /**
     * Schnelle inverse Fourier-Transformation (IFFT).
     * <p>
     * Die Funktion nimmt an, dass die Laenge des Arrays c immer eine
     * Zweierpotenz ist. Es gilt also: c.length == 2^m fuer ein beliebiges m.
     */
    public static Complex[] ifft(Complex[] c) {
        int n = c.length;
        Complex[] v = new Complex[n];
        if (v.length == 1) {
            v[0] = c[0];
        } else {
            int m = n / 2;
            Complex[] even = new Complex[m];
            Complex[] odd = new Complex[m];
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    even[i / 2] = c[i];
                } else {
                    odd[i / 2] = c[i];
                }
            }
            Complex[] z1 = ifft(even);
            Complex[] z2 = ifft(odd);
            Complex omega = Complex.fromPolar(1, 2 * Math.PI / n);

            for (int i = 0; i < m; i++) {
                v[i] = z1[i].add(omega.power(i).mul(z2[i]));
                v[m + i] = z1[i].sub(omega.power(i).mul(z2[i]));
            }
        }

        return v;
    }
}
