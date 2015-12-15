import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.table.*;
import java.util.List;
import java.util.ArrayList;

public class ExempleGenerique {
    // interface
    interface ISupprimer<T> {
        public void supprimer(T cible);
    }
    interface IAjouter<T> {
        public T ajouter(List<String> fields);
    }

    // metier
    static class MetierAjouter implements IAjouter<MaDonnee> {
        public MaDonnee ajouter(List<String> fields) {
            MaDonnee nouveau = new MaDonnee(fields.get(0));
            System.out.println("On a ajoute: " + nouveau);
            // Comme c'est un exemple, on cree l'objet ici, mais normalement on doit appeler la fabrique
            return(nouveau);
        }
        
    }
    static class MetierSupprimer implements ISupprimer<MaDonnee> {
        public void supprimer(MaDonnee cible) {
            // appeler la fabrique pour supprimer l'objet
            System.out.println("On a supprime: " + cible);
        }
    }
    
    // domaine
    static class MaDonnee {
        String champ;
        public MaDonnee(String champ) {
            this.champ = champ;
        }
        public String toString() {
            return champ;
        }
    }
    
    // Swing
    static class MyPanel<T,A extends IAjouter<T> ,S extends ISupprimer<T> > extends JPanel implements ActionListener, ListSelectionListener {
        DefaultListModel<T> modele = new DefaultListModel<T>();
        JList<T> liste = new JList<T>(modele);
        JButton supprimer = new JButton("supprimer");
        JButton ajouter = new JButton("ajouter");
        A a;
        S s;
        int currently_selected = -1;
        int nFields;
        List<JTextField> listfields = new ArrayList<JTextField>();
        public MyPanel(A a, S s, int nFields) {
            this.a = a;
            this.s = s;
            this.nFields = nFields;
            add(liste);
            
            modele.addElement((T)new MaDonnee("titi"));
            modele.addElement((T)new MaDonnee("toto"));
            
            liste.getSelectionModel().addListSelectionListener(this);
            add(supprimer);
            add(ajouter);
            supprimer.addActionListener(this);
            ajouter.addActionListener(this);
            
            for (int i = 0; i < nFields; i++) {
                JTextField tf = new JTextField(new PlainDocument(), "", 10);
                add(tf);
                listfields.add(tf);
            }
        }
        
        public void valueChanged(ListSelectionEvent e) {
            currently_selected = e.getFirstIndex();
            
        }
        
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == supprimer) && (currently_selected != -1)) {
                T t = modele.getElementAt(currently_selected);
                s.supprimer(t); // appel de la classe metier
                modele.removeElementAt(currently_selected);
            
            } else if (e.getSource() == ajouter) {
                List<String> l = new ArrayList<String>();
                for (JTextField field : listfields) {
                    Document doc = field.getDocument();
                    try {
                        l.add(doc.getText(0, doc.getLength()));
                    } catch (BadLocationException ex) {
                        ex.printStackTrace(); // TODO: gestion exception
                    }
                    
                }
                T t = a.ajouter(l);
                modele.addElement(t);
            }
        }
    }
    
    public static class MyFrame extends JFrame {

        public MyFrame() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            setSize(640,400);

            //ajout d'un panel dans la frame
            getContentPane().add(new MyPanel<MaDonnee, MetierAjouter, MetierSupprimer>(new MetierAjouter(), new MetierSupprimer(), 1));
            setLocationRelativeTo(null);
        }
    }

    public static void main(String [] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}
