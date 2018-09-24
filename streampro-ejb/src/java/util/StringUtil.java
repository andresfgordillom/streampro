/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class StringUtil {

    public String reemplazaEspacios(String cad, String reempl) {
        if (cad == null) {
            return "";
        }
        if (cad.trim().compareTo("") == 0) {
            return "";
        }

        String arr[] = cad.trim().split(" ");
        String nCad = "";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].trim().compareTo("") != 0) {
                nCad += arr[i].trim() + reempl.trim();
            }
        }
        return nCad.substring(0, nCad.length() - reempl.length());
    }

    public static String removeNonDigits(final String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str.replaceAll("\\D+", "");
    }

    public boolean isNumberFloat(String cadena) {
        if (cadena == null) {
            return false;
        }
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    } // fin de metodo

    public static String nChar(int n, char char_) {
        String tab = "";
        for (int i = 0; i < n; i++) {
            tab += char_;
        }
        return tab;
    }

    public static String nStr(int n, String repeat) {
        String tab = "";
        for (int i = 0; i < n; i++) {
            tab += repeat;
        }
        return tab;
    }

    public static String primeraMayuscula(String cad) {
        if (cad.length() == 0) {
            return "";
        } else {
            return cad.trim().substring(0, 1).toUpperCase() + cad.trim().substring(1, cad.trim().length());
        }
    }

    public static String primeraMinuscula(String cad) {
        if (cad.length() == 0) {
            return "";
        } else {
            return cad.trim().substring(0, 1).toLowerCase() + cad.trim().substring(1, cad.trim().length());
        }
    }

    public static int numVences(String cad, char ch) {
        int n = 0;
        char[] arrCH = cad.toCharArray();
        for (int i = 0; i < arrCH.length; i++) {
            if (ch == arrCH[i]) {
                n++;
            }
        }
        return n;
    }

    public static String formatoVariableJPA(String nomTableOrCampo, boolean primeraMayus) {
        String nom = "";
        nomTableOrCampo = nomTableOrCampo.trim();
        if (nomTableOrCampo == null || nomTableOrCampo.compareTo("") == 0) {
            return null;
        }

        //Primera en mayuscula
        if (primeraMayus) {
            nom = nomTableOrCampo.substring(0, 1).toUpperCase() + nomTableOrCampo.substring(1, nomTableOrCampo.length());
        } else {
            nom = nomTableOrCampo;
        }

        //Mayuscula cuando '_'
        char[] nomArr = nom.toCharArray();
        nom = "";
        for (int i = 0; i < nomArr.length; i++) {
            if (nomArr[i] == '_') {
                try {
                    String a2 = "" + nomArr[i + 1];
                    a2 = a2.toUpperCase();
                    nom += a2;
                    i++;
                } catch (Exception e) {
                }
            } else {
                nom += nomArr[i];
            }
        }

        return nom;
    }

    public static String quitarSaltosDeLinea(String str) {
        return str.replaceAll("[\n\r]", "");
    }

    public static String quitarCaracterEspecial(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùüñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇºª°";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcCoao";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String quitaCaracteresConAcentos(String str) {
        str = str.replaceAll("&", "Y");
        str = str.replaceAll("á", "a");
        str = str.replaceAll("é", "e");
        str = str.replaceAll("í", "i");
        str = str.replaceAll("ó", "o");
        str = str.replaceAll("ú", "u");
        str = str.replaceAll("ñ", "n");
        str = str.replaceAll("ü", "u");
        //
        str = str.replaceAll("Á", "A");
        str = str.replaceAll("É", "E");
        str = str.replaceAll("Í", "I");
        str = str.replaceAll("Ó", "O");
        str = str.replaceAll("Ú", "U");
        str = str.replaceAll("Ñ", "N");
        str = str.replaceAll("Ü", "U");

        return str;

    }

    public static String quitarCaracteresParaNombreArchivo(String nom) {
        return quitarCaracterEspecial(quitarSaltosDeLinea(quitaCaracteresConAcentos(nom)));
    }

    public static String rellenar(String cad, char relleno, int tam, boolean isIzq) {
        String rell = "";
        cad = cad.trim();
        if (cad.trim().length() <= tam) {
            int dif = tam - cad.length();
            for (int i = 0; i < tam; i++) {
                if (i < dif) {
                    rell += relleno;
                } else {
                    if (isIzq) {
                        rell += cad;
                    } else {
                        rell = cad + rell;
                    }
                    break;
                }
            }
        } else {
            rell = cad.trim().substring(0, tam);
        }
        return rell;
    }

    public static String quitarCaracter(String cad, char chQuitar) {
        String cadOut = "";
        for (int i = 0; i < cad.trim().length(); i++) {
            if (cad.charAt(i) != chQuitar) {
                cadOut += cad.charAt(i);
            }
        }
        return cadOut;
    }

    public static String recortar(String cadena, int caracteres) {
        return cadena.substring(0, Math.min(cadena.length(), caracteres));
    }

    public static String maxCharacter(String txt, int maxCh) {
        if (txt == null) {
            return "";
        }

        if (maxCh > txt.trim().length()) {
            return txt.trim();
        }

        return txt.trim().substring(0, maxCh);
    }

    public static String primerasLetrasMayuscula(String cadena) {
        cadena = cadena.toLowerCase();

        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);

        // el -2 es para evitar una excepción al caernos del arreglo
        for (int i = 0; i < cadena.length() - 2; i++) // Es 'palabra'
        {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') // Reemplazamos
            {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }

        return new String(caracteres);
    }

    public static String bytesToHuman(long bytes) {
        return bytesToHuman(bytes, true);
    }

    public static String bytesToHuman(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static boolean isCharTelephone(String num) {
        String cpp = "0123456789*#";
        for (char ch : num.toCharArray()) {
            if (!cpp.contains(ch + "")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDate(String fechax) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MMM-yyyy");
            Date fecha = formatoFecha.parse(fechax);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPasswordValid(String password) {
        String PASSWORD_PATTERN = "((?=.*\\d)"//Mín 1 Número
                + "(?=.*[a-z])"//Mín. 1 Minúscula
                + "(?=.*[A-Z])"//Mín. 1 Mayúscula
                + "(?=.*[^a-zA-Z0-9])"//Mín 1 NO Alfanumérico
                + "(?=\\S+$)"//No Se Permiten Espacios
                + ".{6,})";//mïn. 6 Caracteres

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isYoutube(String url) {
        if (url.toUpperCase().contains("YOUTUBE")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getYoutubeId(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String getYoutubeUrlEmbed(String url) {
        return "https://www.youtube.com/embed/" + getYoutubeId(url);
    }

    public static void main(String args[]) {
        System.out.println("primeras Letras: " + StringUtil.primerasLetrasMayuscula("ATENCIÓN INTEGRAL A LA PRIMERA INFANCIA"));
        System.out.println(new StringUtil().reemplazaEspacios("hola  como tas ", "|"));
        System.out.println("Es num->" + new StringUtil().isNumberFloat("31165482102265455554201"));
        System.out.println("¿Contraseña válida? " + new StringUtil().isPasswordValid("A1#joda"));

        System.out.println("YOUTUBE:" + getYoutubeUrlEmbed("https://www.youtube.com/watch?v=Ej1Kpv0WScw"));
    }
}
