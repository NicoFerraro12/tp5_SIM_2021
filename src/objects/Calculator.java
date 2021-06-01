package objects;

import java.util.Random;

public class Calculator {

    private static final Random rnd = new Random();

    public static double calcular_uniforme(double random, double a, double b) {
        return a + (random * (b - a));
    }

    public static double calcular_uniforme() {
        return rnd.nextDouble();
    }

    public static double[] calcular_normal(double random,double random2, double media, double desviacion) {

        double[] resultado = new double[2];
        
        
        double rnd1 = random;
        double rnd2 = random2;
        
        double lnRND1 = Math.log(rnd1);
        
        double factorIzquierdo = ((-2) * lnRND1);
        double raizFactor = Math.sqrt(factorIzquierdo);
        
        double factorDerecho = 2 * (Math.PI) * rnd2;
        
        double cosenoFactor = Math.cos(factorDerecho);
        double senoFactor = Math.sin(factorDerecho);
        
        double z1 = raizFactor * cosenoFactor;
        double z2 = raizFactor * senoFactor;
        
        double n1 = ((z1 * desviacion) + media);
        double n2 = ((z2 * desviacion) + media);
        
        resultado[0] = n1;
        resultado[1] = n2;
        
        return resultado;

    }

    public static double calcular_exponencial(double random, double lambda) {

        return -(1 / lambda) * Math.log(1 - random);

    }

}
