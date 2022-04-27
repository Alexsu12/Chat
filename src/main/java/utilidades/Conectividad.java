package utilidades;

public class Conectividad {
    public static boolean comprobarIP(String ip){
        int contador = 0;
        String numeros = "";

        for (int i = 0; i < ip.length(); i++) {
            if (ip.charAt(i) == '.') {
                contador++;
            }
            if (ip.charAt(i) != '.'){
                numeros = numeros.concat(String.valueOf(ip.charAt(i)));
            }
        }

        if (contador != 3) {
            return false;
        }

        try {
            Long.parseLong(numeros);
        }catch (NumberFormatException e){
            return false;
        }

        return true;
    }
}
