import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.table.*;

public class ExempleScroll {

    public static class MyPanel extends JPanel  {

        DefaultListModel<Integer> model = new DefaultListModel<Integer>();
        JList<Integer> l = new JList<Integer>(model);
        public MyPanel() {
            JScrollPane sp = new JScrollPane(l);
            add(sp);
            
            for (int i = 0; i < 20; i++) {
                model.addElement(new Integer(i));
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
