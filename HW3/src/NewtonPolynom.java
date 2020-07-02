import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 *
 * @author braeckle
 */
public class NewtonPolynom implements InterpolationMethod {

    /**
     * Stuetzstellen xi
     */
    double[] x;

    /**
     * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
     * a2*(x-x0)*(x-x1)+...
     */
    double[] a;

    /**
     * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
     * fuer die Erweiterung der Stuetzstellen benoetigt.
     */
    double[] f;

    /**
     * leerer Konstruktore
     */
    public NewtonPolynom() {
    }

    ;

    /**
     * Konstruktor
     *
     * @param x Stuetzstellen
     * @param y Stuetzwerte
     */
    public NewtonPolynom(double[] x, double[] y) {
        this.init(x, y);
    }

    /**
     * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
     * Newton-Polynom berechnet.
     */
    @Override
    public void init(double a, double b, int n, double[] y) {
        x = new double[n + 1];
        double h = (b - a) / n;

        for (int i = 0; i < n + 1; i++) {
            x[i] = a + i * h;
        }
        computeCoefficients(y);
    }

    /**
     * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
     * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
     * ist leer" werden nicht beachtet.
     *
     * @param x Stuetzstellen
     * @param y Stuetzwerte
     */
    public void init(double[] x, double[] y) {
        this.x = Arrays.copyOf(x, x.length);
        computeCoefficients(y);
    }

    /**
     * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
     * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
     * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
     * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
     * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
     * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
     * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
     * der Erweiterung der Stuetzstellen verwendet werden.
     * <p>
     * Es gilt immer: x und y sind gleich lang.
     */
    private void computeCoefficients(double[] y) {

        int n = y.length - 1;
        a = new double[y.length];
        f = new double[y.length];
        double[] col = y.clone();
        f[n] = y[n];
        a[0] = y[0];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                col[j] = (col[j + 1] - col[j]) / (x[j + i] - x[j]);
            }
            f[n - i] = col[n - i];
            a[i] = col[0];
        }

    }

    /**
     * Gibt die Koeffizienten des Newton-Polynoms a zurueck
     */
    public double[] getCoefficients() {
        return a;
    }

    /**
     * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
     * zurueck
     */
    public double[] getDividedDifferences() {
        return f;
    }

    /**
     * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
     * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
     * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
     * werden, da man den neuen Punkt unten anhaengen und das alte
     * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
     * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
     * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
     * die Stuetzstellen nicht erweitert.
     *
     * @param x_new neue Stuetzstelle
     * @param y_new neuer Stuetzwert
     */
    public void addSamplingPoint(double x_new, double y_new) {
        if (x != null && Arrays.asList(x).contains(x_new)) return;
        if (f == null || a == null) {
            double[] y = {y_new};
            computeCoefficients(y);
        }
        int n = a.length;
        double[] fn = new double[n + 1];
        double[] an = Arrays.copyOf(a, n + 1);
        double[] xn = Arrays.copyOf(x, n + 1);
        xn[n] = x_new;
        fn[n] = y_new;
        for (int i = 1; i <= n; i++) {
            fn[n - i] = (fn[n - i + 1] - f[n - i]) / (x_new - x[n - i]);
        }

        f = fn;
        an[n] = fn[0];
        a = an;
        x = xn;
    }

    /**
     * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
     * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
     * dass die Stuetzstellen nicht leer sind.
     */
    @Override
    public double evaluate(double z) {

        int n = a.length - 1;
        double erg = a[n];
        for (int i = 1; i <= n; i++) {
            erg = (erg * (z - x[n - i])) + a[n - i];
        }
        return erg;
    }
}
