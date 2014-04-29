import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Junk{
  public Junk(){
    try{
      JFrame f = new JFrame("Offscreen Paint");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      MyJPanel p = new MyJPanel();
      f.add(p);
      f.pack();
      f.setVisible(true);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public static void main(String[] args){
    new Junk();
  }
  class MyJPanel extends JPanel implements ActionListener{
    int inc = 1;
    int count = 0;
    BufferedImage bi = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    Graphics myG = bi.createGraphics();
    Timer t = new Timer(500, this);
    public MyJPanel(){
      this.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
      myG.clearRect(0, 0, 256, 256);
      this.repaint();
      t.start();
    }
    public void actionPerformed(ActionEvent e){
      MyPaintOffScreen(myG);
      this.repaint();
    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(bi, 0, 0, this);
    }
    public void MyPaintOffScreen(Graphics g){
      if(count<0){
        count = 1;
        inc *= -1;
      }else if(count > 9){
        count = 8;
        inc *= -1;
      }
      g.clearRect(0, 0, 256, 256);
      g.drawString(Integer.toString(count), 128, 128);
      count+=inc;
    }
  }
}