import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Ventana extends JFrame {
    static JToolBar tb;
    public JPanel keys;
    public JLabel chest = new JLabel(new ImageIcon("resources"
            + File.separator+"cofreCerrado.jfif"));
    public String correct;
    public JLabel respuesta = new JLabel();
    public int tries = 10;

    public JButton menosD = new JButton("- dificultad");
    public JButton masD = new JButton("+ dificultad");

    public JLabel attemps;

    public JTextArea userInput = new JTextArea();
    public Ventana(){
        initComponents();
    }

    private void initComponents(){
        //Layouts
        getContentPane().setLayout(new BorderLayout());
        keys = new JPanel(new GridLayout(3, 3));
        var response = new JPanel(new GridLayout());

        // Interactuables
        attemps = new JLabel("Comienza el juego");

        keys.setBorder(new LineBorder((Color.BLUE)));

        var menu = new JPanel(new GridLayout(2,1));

        menu.add(menosD);
        menu.add(masD);

        tb = new JToolBar();
        tb.setSize(5, 10);
        tb.add(menu);

        add(userInput, BorderLayout.NORTH);
        add(keys, BorderLayout.CENTER);
        add(response, BorderLayout.SOUTH);
        add(tb, BorderLayout.EAST);


        response.add(respuesta);
        response.add(chest);
        response.add(attemps);

    }

    public void imageHandler(){
        chest.setIcon(new ImageIcon("resources"
                + File.separator+"cofreAbierto.png"));
    }
}
