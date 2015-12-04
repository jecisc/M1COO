import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.table.*;

public class ExempleTable {

    static class Personne {
        String nom;
        String prenom;
        int age;
        
        public Personne(String nom, String prenom, int age) {
            this.nom = nom;
            this.prenom = prenom;
            this.age = age;
        }
        
        String getNom() {
            return nom;
        }
        
        String getPrenom() {
            return prenom;
        }
        
        int getAge() {
            return age;
        }
        
        void setNom(String nom) {
            this.nom = nom;
        }
        void setPrenom(String prenom) {
            this.prenom = prenom;
        }
        void setAge(int age) {
            this.age = age;
        }
    }
    /* 
     * Le modele de table (sous-classe de AbstractTableModel), va representer : 
     *
     * - le nombre de lignes/colonnes de la table
     * - le nom des colonnes
     * - le contenu de la table
     *
     * Pour modifier tout ceci, il faudra toujours passer par le modele.
     *
     */
    public static class MyTableModel extends AbstractTableModel  {
    
    
        // Methodes "obligatoires" héritées d'AbstractTableModel
        
        Personne []donnees = {new Personne("Durand", "Paul", 25), new Personne("Dupont", "Jean", 32) };
        // Nom des colonnes
        public String getColumnName(int col) {
            String [] colnames = {"nom", "prenom", "age"};
            return colnames[col];
        }
        
        // Numero de lignes
        public int getRowCount() {
            return 2; // 2 lignes
        }
        
        // Nombre de colonnes
        public int getColumnCount() {
            return 3; // 3 colonnes
        }
        
        // Est-ce que la cellule (row,col) est editable par l'utilisateur? 
        public boolean isCellEditable(int row, int col) {
            return false; //non
        }
        
        // Recuperer la valeur d'une cellule
        public Object getValueAt(int row, int col) {
            Personne p = donnees[row];
            switch(col) {
                case 0:
                    return p.getNom();
                case 1:
                    return p.getPrenom();
                case 2:
                    return p.getAge();
                default:
                    throw new RuntimeException("Colonne invalide");
            }
        }
        
        // Modifier la valeur d'une cellune
        public void setValueAt(int row, int col, Object obj) {
            Personne p = donnees[row];
            switch(col) {
                case 0:
                    p.setNom((String)obj);
                case 1:
                    p.setPrenom((String)obj);
                case 2:
                    p.setAge((Integer) obj);
                default:
                    throw new RuntimeException("Colonne invalide");
            }
        }
        
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }
        
        // Methodes "à nous" (pas heritées de AbstractTableModel)
        
        public Personne getPersonne(int row) {
            return donnees[row];
        }
        
    }
    
    public static class MyPanel extends JPanel implements ListSelectionListener {

        JTable t;
        MyTableModel m = new MyTableModel();
        public MyPanel() {
            t = new JTable(m);
            t.getSelectionModel().addListSelectionListener(this);
            add(t);
            
        }
      public void valueChanged(ListSelectionEvent e) {
            int debutIndex = t.getSelectionModel().getMinSelectionIndex();
            int finIndex = t.getSelectionModel().getMaxSelectionIndex();
            
            if (t.getSelectionModel().isSelectionEmpty()) {
                System.out.println("Rien n'est selectionne.");
            } else {
                for (int i = debutIndex; i <= finIndex; i++) {
                    Personne pers = m.getPersonne(i);
                    System.out.println(pers.getNom() + " " + pers.getPrenom() + ", " + pers.getAge() + " ans");
                }
            }
          
      }
    
    }
    public static class MyFrame extends JFrame {

        public MyFrame() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            setSize(640,400);

            //ajout d'un panel dans la frame
            getContentPane().add(new MyPanel());
            setLocationRelativeTo(null);
        }
    }

    public static void main(String [] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}
