package objects;

import java.util.Random;

import static java.lang.Math.*;

public class Calculator {

    private static final Random rnd = new Random();

    public static double calcular_uniforme(double a, double b) {
        return a + (rnd.nextDouble() * (b - a));
    }

    public static double calcular_uniforme() {
        return rnd.nextDouble();
    }

    public static double calcular_normal(double media, double desviacion) {

        double raiz = sqrt((-2) * log(rnd.nextDouble()));

        double segundo_miembro = cos(2 * PI * rnd.nextDouble());

        double z = raiz * segundo_miembro;

        return (media + (desviacion * z));

    }

    public static double calcular_exponencial(double lambda) {
        return (1 / lambda) * Math.log(1 - rnd.nextDouble());
    }

}
