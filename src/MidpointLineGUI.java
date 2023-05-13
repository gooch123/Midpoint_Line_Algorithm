import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MidpointLineGUI extends JFrame implements ActionListener {
   
   private JTextField x1Field, y1Field, x2Field, y2Field, translateXField, translateYField;
   private JButton drawButton, translateButton;
   private JPanel canvas;
   private double x1, y1, x2, y2;
   private double translateX, translateY;
   private boolean isLineDrawn;
   
   public MidpointLineGUI() {
      setTitle("Midpoint Line Algorithm");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new FlowLayout());
      x1Field = new JTextField(5);
      y1Field = new JTextField(5);
      x2Field = new JTextField(5);
      y2Field = new JTextField(5);
      inputPanel.add(new JLabel("Point 1: (x1, y1)"));
      inputPanel.add(x1Field);
      inputPanel.add(y1Field);
      inputPanel.add(new JLabel("Point 2: (x2, y2)"));
      inputPanel.add(x2Field);
      inputPanel.add(y2Field);
      
      JPanel translatePanel = new JPanel();
      translatePanel.setLayout(new FlowLayout());
      translateXField = new JTextField(5);
      translateYField = new JTextField(5);
      translatePanel.add(new JLabel("Translation X:"));
      translatePanel.add(translateXField);
      translatePanel.add(new JLabel("Translation Y:"));
      translatePanel.add(translateYField);
      
      drawButton = new JButton("Draw Line");
      drawButton.addActionListener(this);
      
      translateButton = new JButton("Translation");
      translateButton.addActionListener(this);
      
      canvas = new JPanel() {
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isLineDrawn) {
               g.setColor(Color.RED);
               int canvasWidth = getWidth();
               int canvasHeight = getHeight();
               int adjustedX1 = (int) (x1 * canvasWidth / 100.0);
               int adjustedY1 = (int) (y1 * canvasHeight / 100.0);
               int adjustedX2 = (int) (x2 * canvasWidth / 100.0);
               int adjustedY2 = (int) (y2 * canvasHeight / 100.0);
               g.drawLine(adjustedX1, canvasHeight - adjustedY1, adjustedX2, canvasHeight - adjustedY2);
            }
         }
         
         @Override
         public Dimension getPreferredSize() {
            return new Dimension(400, 400);
         }
      };
      
      canvas.setBackground(Color.WHITE);
      canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      
      add(inputPanel, BorderLayout.NORTH);
      add(translatePanel, BorderLayout.CENTER);
      add(drawButton, BorderLayout.WEST);
      add(translateButton, BorderLayout.EAST);
      add(canvas, BorderLayout.SOUTH);
      
      pack();
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == drawButton) {
         try {
            x1 = Double.parseDouble(x1Field.getText());
            y1 = Double.parseDouble(y1Field.getText());
            x2 = Double.parseDouble(x2Field.getText());
            y2 = Double.parseDouble(y2Field.getText());
            
            isLineDrawn = true;
            canvas.repaint();
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values for the coordinates.");
         }
      } else if (e.getSource() == translateButton) {
         try {
            double translateX = Double.parseDouble(translateXField.getText());
            double translateY = Double.parseDouble(translateYField.getText());
            
            x1 += translateX;
            y1 += translateY;
            x2 += translateX;
            y2 += translateY;
            
            canvas.repaint();
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values for translation.");
         }
      }
   }
   
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new MidpointLineGUI();
         }
      });
   }
}
