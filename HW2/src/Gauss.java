

public class Gauss {


    /**
     * Diese Methode soll die Loesung x des LGS R*x=b durch
     * Rueckwaertssubstitution ermitteln.
     * PARAMETER:
     * R: Eine obere Dreiecksmatrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] backSubst(double[][] R, double[] b) {
        int n = b.length;
        double[] sol = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            sol[i] = calcRow(R[i], b[i], sol, i);//b[i] / R[i][i];
        }
        return sol;
    }

    private static double calcRow(double[] row, double v, double[] sol, int i) {
        double temp = v;
        for (int j = i + 1; j < row.length; j++) {
            temp -= row[j] * sol[j];
        }

        return temp / row[i];
    }

    /**
     * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
     * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden.
     * PARAMETER: A:
     * Eine regulaere Matrix der Groesse n x n
     * b: Ein Vektor der Laenge n
     */
    public static double[] solve(double[][] A, double[] b) {
        int n = b.length;
        double[][] G = new double[n][];
        for (int i = 0; i < n; i++) {
            G[i] = A[i].clone();
        }
        double[] gb = b.clone();

        for (int i = 0; i < n; i++) {
            gaussStep(G, gb, i);
        }
        return Gauss.backSubst(G, gb);
    }

    private static void gaussStep(double[][] G, double[] gb, int row) {
        // find max |v| and swap to the top
        int n = gb.length;
        int index = row;
        for (int j = row; j < n; j++) {
            if (Math.abs(G[j][row]) > Math.abs(G[index][row])) {
                index = j;
            }
        }
        //swap rows
        double[] temp = G[row];
        G[row] = G[index];
        G[index] = temp;
        double v = gb[row];
        gb[row] = gb[index];
        gb[index] = v;

        for (int j = row + 1; j < n; j++) {
            if (G[j][row] == 0) continue;
            double factor = G[j][row] / G[row][row];
            for (int k = row; k < n; k++) {
                G[j][k] -= factor * G[row][k];
            }
            gb[j] -= factor * gb[row];
        }
    }


    /**
     * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
     * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
     * <p>
     * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt):
     * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung
     * solange durch, bis in einem Schritt alle moeglichen Pivotelemente
     * numerisch gleich 0 sind (d.h. <1E-10)
     * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
     * loesen Sie Tx = -v durch Rueckwaertssubstitution
     * -Geben Sie den Vektor (x,1,0,...,0) zurueck
     * <p>
     * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
     * In diesem Fall soll der 0-Vektor zurueckgegeben werden.
     * PARAMETER:
     * A: Eine singulaere Matrix der Groesse n x n
     */
    public static double[] solveSing(double[][] A) {
        // copy A
        double[][] G = new double[A.length][];
        for (int i = 0; i < G.length; i++) {
            G[i] = A[i].clone();
        }
        double[] gb = new double[G.length]; // default value already zero
        // gauss steps
        int steps = 0;
        for (int t = 0; t < gb.length; t++) {
            // pivot element != zero?
            boolean allZero = true;
            for (int j = t; j < gb.length; j++) {
                if (G[j][t] > 0) allZero = false;
            }
            if (allZero) break; //end gauss steps
            gaussStep(G, gb, t);
            steps++;
        }
        // invertible?
        if(steps == gb.length) return new double[gb.length];

        // T has size t x t, v is at t + 1
        double[][] T = new double[steps][steps];
        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < steps; j++) {
                T[i][j] = G[i][j];
            }
        }


        double[] v = new double[steps];
        for (int i = 0; i < v.length; i++) {
            v[i] = -G[i][steps];
        }
        double[] x = backSubst(T,v);
        double[] p = new double[G.length];
        System.arraycopy(x,0,p,0,x.length);
        p[x.length] = 1;
        for (int i = x.length + 1; i < p.length; i++) {
            p[i] = 0; // probably not necessary since its the default value
        }
        return p;
    }

    /**D
     * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
     * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
     * Gauss-Loesung
     */
    public static double[] matrixVectorMult(double[][] A, double[] x) {
        int n = A.length;
        int m = x.length;

        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = 0;
            for (int j = 0; j < m; j++) {
                y[i] += A[i][j] * x[j];
            }
        }

        return y;
    }
}
