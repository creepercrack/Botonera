package botonera;

import javax.swing.*;

public class SonidoButton extends JButton {
    private int i;

    public SonidoButton(String text) {
        super(text);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
