package metodosMetaData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CreacionMetaData {

    private static final String RUTA_DIR = "./metaData";
/**
    public static void main(String[] args) {
        System.out.println("CREACION DE METADATOS PARA LA GRAFICACION");
        System.out.println("--------------------------------------------------");
    }
 **/
    public static void imprimirMensaje(String mensaje) {
        char separador = ',';
        int pos = mensaje.indexOf(separador);
        String nameAlgoritmo= mensaje.substring(0, pos);
        System.out.println("CREACION DE METADATOS PARA LA GRAFICACION DE ALGORITMO: " + nameAlgoritmo);
        System.out.println("--------------------------------------------------");
    }

    public static String crearDATA(String name, int posLineal, long t0, long t1){
        String resultado = "";
        double ms = (t1 - t0) / 1_000_000.0;
        resultado+= name + "," + posLineal + "," + ms;
        return resultado;
    }

    public static void grabarDATA_p2(String dataAlgoritmo) throws IOException {

        imprimirMensaje(dataAlgoritmo);

        Path directorio = Paths.get(RUTA_DIR);
        if(!Files.exists(directorio)){
            Files.createDirectories(directorio);
        }

        Path rutaArchivo = directorio.resolve("metaData_punto2.csv");
        if(!Files.exists(rutaArchivo)){
            String encabezado = "Algoritmo,tam_array,Tiempo(ms)\n";
            Files.write(rutaArchivo, encabezado.getBytes(), StandardOpenOption.CREATE);
        }

        Files.write(rutaArchivo, (dataAlgoritmo+"\n").getBytes(), StandardOpenOption.APPEND);

    }
    public static void grabarDATA_p1(String dataAlgoritmo) throws IOException {

        imprimirMensaje(dataAlgoritmo);

        Path directorio = Paths.get(RUTA_DIR);
        if(!Files.exists(directorio)){
            Files.createDirectories(directorio);
        }

        Path rutaArchivo = directorio.resolve("metaData_punto1.csv");
        if(!Files.exists(rutaArchivo)){
            String encabezado = "Algoritmo,tam_array,Tiempo(ms)\n";
            Files.write(rutaArchivo, encabezado.getBytes(), StandardOpenOption.CREATE);
        }

        Files.write(rutaArchivo, (dataAlgoritmo+"\n").getBytes(), StandardOpenOption.APPEND);

    }
}
