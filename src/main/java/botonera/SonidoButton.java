package botonera;

import javax.swing.*;
import java.io.File;

public class SonidoButton extends JButton {
    private File sonido;

    public SonidoButton(String text) {
        super(text);
    }

    public File getSonido() {
        return sonido;
    }

    public void setSonido(File sonido) {
        this.sonido = sonido;
    }
}
