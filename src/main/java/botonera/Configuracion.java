package botonera;

import java.io.*;

public class Configuracion {
    private static final File ficheroConfiguracion = new File("config");

    private static int maxColumnas = 4;
    private static int maxFilas = 32;
    private static int columnas = 4;
    private static int filas = 12;
    private static boolean modoOscuro = false;
    private static File[] sonidos = new File[maxColumnas * maxFilas];

    public static void getConfiguracion() {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(ficheroConfiguracion)))) {
            maxColumnas = dis.readInt();
            maxFilas = dis.readInt();
            columnas = dis.readInt();
            filas = dis.readInt();
            modoOscuro = dis.readBoolean();
            sonidos = new File[maxColumnas * maxFilas];

            for (int i = 0; i < sonidos.length; i++) {
                File sonido = new File(dis.readUTF());
                if (sonido.exists() && sonido.isFile()) sonidos[i] = sonido;
                else sonidos[i] = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setConfiguracion() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ficheroConfiguracion)))) {
            dos.writeInt(maxColumnas);
            dos.writeInt(maxFilas);
            dos.writeInt(columnas);
            dos.writeInt(filas);
            dos.writeBoolean(modoOscuro);

            for (File file : sonidos) {
                if (file == null) dos.writeUTF("");
                else dos.writeUTF(file.getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getMaxColumnas() {
        return maxColumnas;
    }

    public static void setMaxColumnas(int maxColumnas) {
        Configuracion.maxColumnas = maxColumnas;
    }

    public static int getMaxFilas() {
        return maxFilas;
    }

    public static void setMaxFilas(int maxFilas) {
        Configuracion.maxFilas = maxFilas;
    }

    public static int getColumnas() {
        return columnas;
    }

    public static void setColumnas(int columnas) {
        Configuracion.columnas = columnas;
    }

    public static int getFilas() {
        return filas;
    }

    public static void setFilas(int filas) {
        Configuracion.filas = filas;
    }

    public static boolean isModoOscuro() {
        return modoOscuro;
    }

    public static void setModoOscuro(boolean modoOscuro) {
        Configuracion.modoOscuro = modoOscuro;
    }

    public static File[] getSonidos() {
        return sonidos;
    }

    public static void setSonidos(File[] sonidos) {
        Configuracion.sonidos = sonidos;
    }

    public static File getSonido(int i) {
        return sonidos[i];
    }

    public static void setSonido(int i, File sonido) {
        sonidos[i] = sonido;
    }
}
