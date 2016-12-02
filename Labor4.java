/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labor4;

import jdk.nashorn.internal.codegen.CompilerConstants;


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
                return Math.sin(m * x) / (m * x);
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
                return (x >= 0 ? -m * x + b : m * x + b);
            }
        }.init(5, 1)),

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

            public Fn init(final int n, final int k, final double e) {
                this.n = n;
                this.k = k;
                this.e = e;
                return this;
            }

            @Override
            public double value(double x) {
                if (n==k) return 1;
                else return 0;
            }
        }.init(1, 9, STEP));

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
            System.out.println(String.format("%s;%s%s;%s;%s",
                    String.valueOf(x).replace(".", ","),
                    String.valueOf(FN.SPALT.value(x)).replace(".", ","),
                    String.valueOf(FN.DREIECK.value(x)).replace(".", ","),
                    String.valueOf(FN.RECHTECK.value(x)).replace(".", ","),
                    String.valueOf(FN.KAMM.value(x)).replace(".", ",")
                    ));
        }

        // Fun-Fact: Die Ausgabe kann in eine Datei gespeichert werden und in Excel / OpenOffice Calc importiert werden,
        //           um das Ergebnis in einem schnieken Diagramm zu bewundern :).
    }

}

