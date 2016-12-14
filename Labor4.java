
package labor4;
/**
 * Aufgabe 4
 * 
 * @author Eike Hoffmann <eike.s.hoffmann@student.fh-kiel.de>
 * @author Silas Röber <silas.roeber@student.fh-kiel.de>
 */
public class Labor4 {

    static double STEP = Math.PI / 100;


    private enum FN implements Fn {
        SPALT(new Fn() {
            private double m;

            public Fn init(final double m) {
                this.m = m;
                return this;
            }

            @Override
            public double value(final double x) {
                Double y = Math.sin(m * x) / (m * x);
                // Mathematisch korrekte Behebung der Lücke für x = 0 und daraus folgendem "0/0".
                return (y.isNaN() ? 1.0 : y);
            }
        }.init(5)),

        DREIECK(new Fn() {
            private double m;
            private double b;

            public Fn init(final double m, final double b) {
                this.m = m;
                this.b = b;
                return this;
            }

            @Override
            public double value(double x) {
                return (x >= 0 ? (-m * x + b ): (m * x + b));
            }
        }.init(1 / Math.PI, 1)),

        RECHTECK(new Fn() {
            private double m;

            public Fn init(final double m) {
                this.m = m;
                return this;
            }

            @Override
            public double value(double x) {
                return (x > -m && x < m ? 1 : 0);
            }
        }.init(Math.PI / 2.0)),

        KAMM(new Fn() {
            private int n;
            private int k;
            private double e;
            private double eps;

            public Fn init(final int n, final int k, final double e) {
                this.n = n; // Als Laufvariable hochzählen bis k; vorbesetzten für 1. peek noicht bie 0 oder k
                this.k = k;
                this.e = e;
                this.eps = Math.sin(Math.PI * (e / 2));
                return this;
            }
            /* Alternative 1
            @Override
            public double value(double x) {
                return delta(Math.sin(Math.PI * x));
            }
           
            private double delta(double y) {
                return (Math.abs(y) <= eps ? 1 : 0);
            }
            */
       
            /* Alternative 2
            @Override
            public double value(double x) {
                return (Math.abs(x % (e * k)) <= e ? 1 : 0);
            }
            */
            
            @Override
            public double value(double x) {
                if(n==k) {
                    n=0;
                    return 1;
                }
                else {
                    ++n;
                    return 0;
                }
            }
        }.init(5, 9, STEP));

        private Fn fn;
		

        FN(Fn fn) {
            this.fn = fn;
        }

        @Override
        public double value(final double x) {
            // "normierung" auf [-1, 1]
            double y = fn.value(x);
            if(y > 1) y = 1;
            else if(y < -1) y = -1;
            return y;
        }
    }
    
    public static double myfunc(double x) {
        double y = Math.sin(x);
        if(y > 1) y = 1;
            else if(y < -1) y = -1;
            return y;
    }

    public static void main(String[] args) {
        System.out.println("x;Spalt;Dreieck;Rechteck;Kamm");
        for(double x = -Math.PI; x <= Math.PI; x += STEP) {
            System.out.println(String.format("%s;\t%s;\t%s;\t%s;\t%s",
                    String.valueOf(x).replace(".", ","),
                    String.valueOf(FN.SPALT.value(x)*myfunc(x)).replace(".", ","),
                    String.valueOf(FN.DREIECK.value(x)*myfunc(x)).replace(".", ","),
                    String.valueOf(FN.RECHTECK.value(x)*myfunc(x)).replace(".", ","),
                    String.valueOf(FN.KAMM.value(x)*myfunc(x)).replace(".", ",")
                    ));
        }

        // Fun-Fact: Die Ausgabe kann in eine Datei gespeichert werden und in Excel / OpenOffice Calc importiert werden,
        //           um das Ergebnis in einem schnieken Diagramm zu bewundern :).
    }

}

