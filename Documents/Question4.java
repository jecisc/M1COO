

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.Box.Filler;

import javax.swing.text.PlainDocument;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

class Question4 {

    static class Identite {
        int id;
        String nom;
        String prenom;
        public String toString() {
           return nom + " " + prenom;
        }
        public Identite(int id, String nom, String prenom) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
        }
        public int getId() {
            return id;
        }
    }

    static class LeftPanel extends JPanel implements ListSelectionListener {
        DefaultListModel<Identite> modele= new DefaultListModel<Identite>();
        JList<Identite> liste = new JList<Identite>(modele);
        TopPanel parent;
        public LeftPanel(TopPanel parent) {
            setLayout(new BorderLayout());
            this.parent = parent;
            add(new JLabel("Vos N-1"), BorderLayout.NORTH);
            modele.addElement(new Identite(0, "Jean", "DUPONT"));
            add(liste, BorderLayout.CENTER);
            liste.getSelectionModel().addListSelectionListener(this);
        }
        public void valueChanged(ListSelectionEvent e) {
            int index = e.getFirstIndex();
            int id =modele.getElementAt(index).getId();
            parent.setPersonId(id);
        }
    }

    static class RightPanel extends JPanel implements ActionListener {
        JButton save = new JButton("Sauvegarder");
        PlainDocument modele1 = new PlainDocument();
        JTextField note1 = new JTextField(modele1, "", 10);
        PlainDocument modele2  = new PlainDocument();
        JTextField note2 = new JTextField(modele2, "",10);
        PlainDocument modeleAppr = new PlainDocument();
        JTextArea appr = new JTextArea(modeleAppr, "", 5, 20);
        
        int id; // ID de la personne en cours
        
        public void actionPerformed(ActionEvent e) {
            if (id == -1) {
                return; // aucune personne selectionnee
            }
            try {
                String note1_str = modele1.getText(0, modele1.getLength());
                String note2_str = modele1.getText(0, modele2.getLength());
                String appreciation_str = modeleAppr.getText(0, modeleAppr.getLength());
                
                System.out.println(note1_str + " " + note2_str + " " + appreciation_str);
                System.out.println("id: " + id);
                // new MettreAppreciation(id, note1_str, note2_str, appreciation_str);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            
        }       
        public void setPersonId(int id) {
            this.id = id;
            // AffichierPersonne ap = new AfficherPersonne(id);
            // a partir de l'objet ap, recuperer et afficher les champs, via modele1 / modele2 / ... 
            
        } 
        public RightPanel(int id) {
            this.id = id;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(new JLabel("Eval Enseignement"));
            add(note1);
            add(new JLabel("Eval Rech."));
            add(note2);
            add(new JLabel("Remarques"));
            add(appr);
            
            save.addActionListener(this);
            add(save);
        }
    }
    static class TopPanel extends JPanel {
        RightPanel right_panel;
        public TopPanel(int id) {
            right_panel = new RightPanel(id);
            setLayout(new BorderLayout());
            add(new LeftPanel(this), BorderLayout.WEST);
            add(right_panel, BorderLayout.EAST);
        }
        public void setPersonId(int id) {
            right_panel.setPersonId(id);
            
        }
    }
    static class ButtonPanel extends JPanel {
        public ButtonPanel() {
            add(new JButton("Quitter"));
            add(new JButton("Voir les infos"));
        }
    }
    
    static class MyPanel extends JPanel {
        public MyPanel(int id) {
            setLayout(new BorderLayout());
            add(new TopPanel(id), BorderLayout.NORTH);
            add(new ButtonPanel(), BorderLayout.SOUTH);
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Question 4");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(new MyPanel(-1));
        frame.setVisible(true);
    
    }

}