import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;

public class Minesweeper extends JFrame{
  private int difficulty = -1;
  private int count = 0;
  private JButton easy;
  private JButton med;
  private JButton hard;
  public Minesweeper(){
    //creates Jframe, adds buttons and labels
    Container cp = this.getContentPane();
    cp.setLayout(new FlowLayout());
    cp.add(new JLabel("Choose your difficulty."));
    cp.add(easy = new JButton("Easy"));
    cp.add(med = new JButton("Medium"));
    cp.add(hard = new JButton("Hard"));

    //adds action listener for buttons
    easy.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        ++count;
        System.out.println(count);
      }
    });
    med.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        ++count;
        System.out.println(count);
      }
    });
    hard.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        ++count;
        System.out.println(count);
      }
    });
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Minesweeper");
    setSize(500,100);
    setVisible(true);
  }
}
