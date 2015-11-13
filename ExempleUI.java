import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class ExempleUI {

    // Ceci appartient a la couche METIER

    public static class UseCase1Metier {
        private String param;
        public UseCase1Metier(String param) {
            // Faire l'action correspondante au C.U (en utilisant les couches fabrique/etc..)
            this.param = param;
        }
        public String resultat() {
            //Recuperer resultat du C.U.
            try {
                return ((new Integer(Integer.parseInt(param)*2)).toString());
            } catch (Exception e) {
                return ("???");
            }
        }
    }

    // Ceci appartient a la couche INTERFACE (à faire normalement dans un fichier séparé! La tout est mis ensemble pour garder l'exemple simple.)
    public static class UseCase1UI extends JPanel implements ActionListener {

        JButton yes = new JButton("OK");
        JButton no = new JButton("Annuler");
        JLabel resultat = new JLabel("Resultat: ");
        
        Document modeleTextField = new PlainDocument(); // Modele du JTextField
        JTextField textfield = new JTextField(modeleTextField, "", 8); //Creer un JTextField avec le modele

        public UseCase1UI() {

            //ajouter elements
            add(new JLabel("Hello world"));
            add(yes);
            add(no);
            add(resultat);
            add(textfield);

            //definir le listener
            yes.addActionListener(this);
            no.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Un event s'est produit!");
            if (e.getSource() == yes) {
                System.out.println("Vous avez clique: OK");
                try {
                
                  // Lecture des infos du JTExtField via le modele, et appel de la classe correspondante au C.U.
                  UseCase1Metier m = new UseCase1Metier(modeleTextField.getText(0, modeleTextField.getLength()));
                  String res = m.resultat();
                  resultat.setText("Resultat: " + res);
                } catch (BadLocationException ex) {
                    // gestion erreur ici ...
                }
            } else {
                System.out.println("Vous avez clique: Annuler");
                try {
                  // Effacement du JTextField via le modele
                  modeleTextField.remove(0, modeleTextField.getLength());
                } catch (BadLocationException ex) {
                  // gestion erreur ici ... 
                }
            }
        }
    }
    public static class MyFrame extends JFrame {

        public MyFrame() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            setSize(640,400);

            //ajout d'un panel dans la frame
            getContentPane().add(new UseCase1UI());
            setLocationRelativeTo(null);
        }
    }

    public static void main(String [] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}
