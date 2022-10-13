import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Controller {
    private Ventana ventana;
    private int dificulty = 4;
    ArrayList password = new ArrayList<String>(dificulty);
    ArrayList userPassword = new ArrayList<String>();

    public Controller(Ventana ventana) {
        this.ventana = ventana;
        iniciarVista();
        passwordGenerator();
        createButtons();
        dificultyHandler();
        ventana.userInput.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                String keyText = KeyEvent.getKeyText((e.getKeyCode()));
                if(e.isActionKey()) {
                    if(e.VK_ENTER == 10)
                    userPassword.add(keyText);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void iniciarVista() {
        ventana.setTitle("Mastermind");
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(700, 800);
        ventana.setVisible(true);
    }

    private void passwordGenerator() {
        while(dificulty > password.size()) {
            var num = String.valueOf((int)(Math.random()*9)+1);
            if(!password.contains(num)){
                password.add(num);
            }
        }
        System.out.println("password: " + password);
    }

    private boolean comparatePassword(ArrayList<String> p, ArrayList<String> userP) {
        if(p.size() == userP.size()){
            for(int i = 0; i < p.size(); i++){
                    if(p.equals(userP)) {
                        System.out.println("correcto:"+ p + " - " + userP);
                        ventana.respuesta.setText("Ganaste!!!");
                        return true;
                    }
                    else if(p.contains(userP.get(i)) && !p.equals(userP)){
                        JOptionPane.showMessageDialog(ventana, "Casi, a lo mejor hay que cambiar" +
                                " un par de cosas! Sigue así!");

                        var arr = new ArrayList<String>();
                        for (String s: userP) {
                            if(p.get(i) != userP.get(i) && p.contains(userP.get(i))){
                                arr.add(i, "1");
                            }else if(p.get(i) == userP.get(i)){
                                arr.add(i, "0");
                            }else if(!p.contains(userP.get(i))){
                                arr.add(i, "-");
                            }
                        }
                        System.out.println(arr);

                        ventana.correct = String.valueOf(arr);
                        ventana.respuesta.setText("Valores Correctos: "+ventana.correct);
                        ventana.tries--;

                        userP.clear();
                        return false;
                    }else {
                        System.out.println("Contraseña incorrecta");
                        ventana.tries--;
                        JOptionPane.showMessageDialog(ventana, "Fallaste, intentos: "+ventana.tries);
                        userP.clear();
                        ventana.attemps.setText("  Intentos restantes: "+ventana.tries);
                        return false;
                    }
            }
        }
        return false;
    }

    private void createButtons() {
        JButton btns;
        for (int i = 0; i < 9; i++) {
            btns = new JButton(String.valueOf(i + 1));
            JButton finalBtns = btns;
            btns.addActionListener(
                    e -> {
                        System.out.println("El valor del botón es: " + finalBtns.getText());
                        if(password.size() > userPassword.size()){
                            userPassword.add(finalBtns.getText());
                            System.out.println(userPassword);
                        }
                        if(ventana.tries > 0 && comparatePassword(password, userPassword)){
                            ventana.imageHandler();
                        }else if(ventana.tries > 0 && !comparatePassword(password, userPassword)
                                && password.size() == userPassword.size()){
                            userPassword.clear();
                            JOptionPane.showMessageDialog(ventana, "Contraseña incorrecta, prueba de nuevo," +
                                    " intentos: "+ventana.tries);
                            userPassword.add(finalBtns.getText());
                        }else if(ventana.tries == 0){
                            JOptionPane.showMessageDialog(ventana, "Vaya, has gastado todas las oportunidades," +
                                    " más suerte la próxima vez");

                            var res = JOptionPane.showConfirmDialog(ventana, "Quieres volver a intentarlo?");
                            if(res == JOptionPane.YES_OPTION) {
                                ventana.tries = dificulty;
                                ventana.masD.setText("+ dificultad");
                                ventana.menosD.setText("- dificultad");
                            }else {
                                System.exit(0);
                            }
                        }

                    }
            );
            ventana.keys.add(btns);
        }

    }

    private void dificultyHandler(){
        ventana.masD.addActionListener(
                e -> {
                    while(dificulty < 10){
                        dificulty++;
                        if(dificulty == 9){
                            ventana.masD.setText("Un solo intento.");
                            ventana.tries = 1;
                        }
                    }
                }
        );
        ventana.menosD.addActionListener(
                e -> {
                    while(dificulty > 0){
                        dificulty--;
                        if(dificulty == 1){
                            ventana.menosD.setText("Esto es broma, no?");
                            ventana.tries = 15;
                        }
                    }
                }
        );
    }

}
