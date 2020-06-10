package ejemplos.tema2;

/**
 * class Mayoritarios.
 * 
 * @author FTG 
 * @version 2.0
 */

public class Mayoritarios {
      
    public int mayoritario1(int[] v) {
        int mitad = v.length / 2;
        for (int i = 0; i < v.length; i++) {
            int candidatoI = v[i];
            int votosCI = 1;
            for (int j = i + 1; j < v.length; j++) {
                if (v[j] == candidatoI) votosCI++; 
                if (votosCI > mitad) return candidatoI; 
            }
        }
        return 0;
    }

    public int mayoritario2(int[] v, int k) {
        int[] votosCI = new int[k + 1];
        for (int i = 0; i < v.length; i++) votosCI[v[i]]++; 
        for (int i = 1; i <= k; i++) {
            if (votosCI[i] > v.length / 2) return i; 
        }
        return 0;
    }
    /*Ejercicio 7*/
    private static int mayoritario(int[] v, int i, int j) {
        // EM: elemento mayoritario
        // subarray vacío: NO hay EM, ni elemento alguno
        if (i > j) return 0; 
        // subarray de 1 elemento: v[i] es el EM
        else if (i == j) return v[i]; 
        else { 
            // subarray de 2 o más elementos: calcular 1 candidato por mitad
            int m = (i + j) / 2;
            int c1 = mayoritario(v, i, m);
            int c2 = mayoritario(v, m + 1, j);
            // c1 y c2 iguales: c1 es el EM
            if (c1 == c2) return c1; 
            // c1 es EM en la 1ª mitad, ver si es EM en v[i, j]
            if (c1 > 0) { 
                if (contar(v, i, j, c1) > (j - i + 1) / 2) return c1;
            }
            // c2 es EM en la 2ª mitad, ver si es EM en v[i, j]
            if (c2 > 0) { 
                if (contar(v, i, j, c2) > (j - i + 1) / 2) return c2;
            }
            return 0; 
        }
    }
    
    public static int mayoritario(int[] v) {
        return mayoritario(v, 0, v.length - 1);
    }
    
    private static int contar(int[] v, int i, int j, int c) {
        int n = 0;
        for (int k = i; k <= j; k++) {
            if (v[k] == c) n++;
        }
        return n;
    }
    
    private static <T> T mayoritario(T[] v, int i, int j) {
        // EM: elemento mayoritario
        // subarray vacío: NO hay EM, ni elemento alguno
        if (i > j) return null; 
        // subarray de 1 elemento: v[i] es el EM
        else if (i == j) return v[i]; 
        else { 
            // subarray de 2 o más elementos: calcular 1 candidato por mitad
            int m = (i + j) / 2;
            T c1 = mayoritario(v, i, m);
            T c2 = mayoritario(v, m + 1, j);
            // c1 y c2 iguales y no nulos: c1 es el EM
            if (c1 != null && c2 != null && c1.equals(c2)) {
                return c1; 
            }
            // c1 es EM en la 1ª mitad, ver si es EM en v[i, j]
            if (c1 != null) { 
                if (contar(v, i, j, c1) > (j - i + 1) / 2) return c1;
            }
            // c2 es EM en la 2ª mitad, ver si es EM en v[i, j]
            if (c2 != null) { 
                if (contar(v, i, j, c2) > (j - i + 1) / 2) return c2;
            }
            return null; 
        }
    }
    
    public static <T> T mayoritario(T[] v) {
        return mayoritario(v, 0, v.length - 1);
    }
    
    private static <T> int contar(T[] v, int i, int j, T c) {
        int n = 0;
        for (int k = i; k <= j; k++) {
            if (v[k].equals(c)) n++;
        }
        return n;
    }
}