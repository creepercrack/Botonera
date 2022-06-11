package botonera;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// TODO: fichero de configuración
public class Botonera extends JFrame {
    private final GridLayout gridLayout;

    private JPanel mainPanel;
    private JSpinner columnasSpinner;
    private JSpinner filasSpinner;
    private JButton aplicarButton;
    private JCheckBox modoOscuroCheckBox;
    private JPanel botonesPanel;

    Botonera() {
        setTitle("Botonera");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(mainPanel);

        SpinnerNumberModel columnasSpinnerNumberModel = new SpinnerNumberModel(4, 1, 4, 1);
        columnasSpinner.setModel(columnasSpinnerNumberModel);
        ((JSpinner.DefaultEditor) columnasSpinner.getEditor()).getTextField().setEditable(false);

        SpinnerNumberModel filasSpinnerNumberModel = new SpinnerNumberModel(12, 1, 32, 1);
        filasSpinner.setModel(filasSpinnerNumberModel);
        ((JSpinner.DefaultEditor) filasSpinner.getEditor()).getTextField().setEditable(false);

        aplicarButton.addActionListener(event -> generarBotones());
        modoOscuroCheckBox.addActionListener(event -> cambiarLookAndFeel());

        gridLayout = new GridLayout(1, 1, 10, 5);
        botonesPanel.setLayout(gridLayout);

        generarBotones();
        cambiarLookAndFeel();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Botonera();
    }

    private void generarBotones() {
        botonesPanel.removeAll();

        gridLayout.setColumns((Integer) columnasSpinner.getValue());
        gridLayout.setRows((Integer) filasSpinner.getValue());

        for (int i = 0; i < (Integer) columnasSpinner.getValue() * (Integer) filasSpinner.getValue(); i++) {
            SonidoButton sonidoButton = new SonidoButton("Botón " + (i + 1));
            sonidoButton.addActionListener(this::sonidoButtonAP);
            sonidoButton.setMinimumSize(sonidoButton.getSize());
            botonesPanel.add(sonidoButton);
        }

        pack();
        repaint();
    }

    private void cambiarLookAndFeel() {
        try {
            FlatLaf flatLaf;

            if (modoOscuroCheckBox.isSelected()) flatLaf = new FlatDarkLaf();
            else flatLaf = new FlatLightLaf();

            UIManager.setLookAndFeel(flatLaf);
            SwingUtilities.updateComponentTreeUI(this);
            pack();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void sonidoButtonAP(ActionEvent event) {
        SonidoButton sonidoButton = ((SonidoButton) event.getSource());

        if ((event.getModifiers() & ActionEvent.CTRL_MASK) != 0) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3"));

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File sonido = fileChooser.getSelectedFile();

                sonidoButton.setText(sonido.getName());
                sonidoButton.setToolTipText(sonido.getName());
                sonidoButton.setSonido(sonido);

                pack();
            }
        } else {
            new Thread(() -> {
                try {
                    FileInputStream fis = new FileInputStream(sonidoButton.getSonido());
                    Player player = new Player(fis);
                    player.play();
                } catch (FileNotFoundException | JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
