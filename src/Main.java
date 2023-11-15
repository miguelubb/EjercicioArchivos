import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String LINE_SEPARATOR_PATTERN =
            "\r\n|[\n\r\u2028\u2029\u0085]";
    public static void main(String[] args) {

        Scanner alumnos= null;
        try {
            alumnos = new Scanner(new File("files/alumnos.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo: file/alumnos.txt");
            return;
        }
        Scanner notas= null;
        try {
            notas = new Scanner(new File("files/notas.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo: file/notas.txt");
            return;
        }
        alumnos.useDelimiter(LINE_SEPARATOR_PATTERN+"|;");
        notas.useDelimiter(LINE_SEPARATOR_PATTERN+"|, ");
        notas.useLocale(Locale.ENGLISH);
        PrintStream output= null;
        try {
            output = new PrintStream("files/output.txt");
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo: file/output.txt");
            return;
        }
        output.println("                Promedio");
        output.println("                --------");
        output.printf("%3s %20s %8s %n","N°", "Nombre Estudiante", "Promedio");
        double sumaParcial = 0, sumaTotal=0, nParcial=0, nTotal=0;
        int numEstudiante, numEstNota;
        String nombre;
        double nota;
        while(alumnos.hasNext()){
            numEstudiante=alumnos.nextInt();
            nombre=alumnos.next();
            numEstNota=notas.nextInt();
            nota=notas.nextDouble();
            while(notas.hasNext() && numEstudiante==numEstNota){
                sumaParcial+=nota;
                nParcial++;
                numEstNota=notas.nextInt();
                nota=notas.nextDouble();
            }
            output.printf("%3d %20s %8.2f %n",numEstudiante, nombre,
                    (sumaParcial/nParcial));
            sumaTotal+=sumaParcial;
            nTotal+=nParcial;
            sumaParcial=nota;nParcial=1;
        }
        output.printf("%3s %20s %8.2f %n","", "Promedio curso:",
                (sumaTotal/nTotal));

        output.close();
        alumnos.close();
        notas.close();

    }
}
