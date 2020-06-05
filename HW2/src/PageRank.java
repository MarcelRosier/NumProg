import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

    /**
     * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
        double[][] A = new double[L.length][L.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                double links = 0;
                for (int l = 0; l < A.length; l++) {
                    links += L[l][j];
                }
                if (L[i][j] == 1) A[i][j] = 1.0 / links;
                A[i][j] = (1 - rho) * A[i][j] + rho / A.length;
            }
        }
        return A;
    }

    /**
     * Diese Methode berechnet die PageRanks der einzelnen Seiten,
     * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
     * (Entspricht dem p-Strich aus der Angabe)
     * Die Ausgabe muss dazu noch normiert sein.
     * PARAMETER:
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
     * ,anstatt einem Link zu folgen.
     */
    public static double[] rank(int[][] L, double rho) {
        double[][] A = buildProbabilityMatrix(L,rho);
        for (int i = 0; i < A.length; i++) {
            A[i][i]--;
        }
        double[] p = Gauss.solveSing(A);
        //normalize
        double sum= Arrays.stream(p).sum();
        p = Arrays.stream(p).map(x -> x/sum).toArray();

        return p;
    }

    /**
     * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
     * absteigendem PageRank.
     * PARAMETER:
     * urls: Die URLs der betrachteten Seiten
     * L: die Linkmatrix (s. Aufgabenblatt)
     * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
     * zufaellig irgendeine Seite zu besuchen
     */
    public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
        int n = L.length;

        double[] p = rank(L, rho);

        RankPair[] sortedPairs = new RankPair[n];
        for (int i = 0; i < n; i++) {
            sortedPairs[i] = new RankPair(urls[i], p[i]);
        }

        Arrays.sort(sortedPairs, new Comparator<RankPair>() {

            @Override
            public int compare(RankPair o1, RankPair o2) {
                return -Double.compare(o1.pr, o2.pr);
            }
        });

        String[] sortedUrls = new String[n];
        for (int i = 0; i < n; i++) {
            sortedUrls[i] = sortedPairs[i].url;
        }

        return sortedUrls;
    }

    /**
     * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
     * als Hilfsklasse zum Sortieren der Urls
     */
    private static class RankPair {
        public String url;
        public double pr;

        public RankPair(String u, double p) {
            url = u;
            pr = p;
        }
    }
}
