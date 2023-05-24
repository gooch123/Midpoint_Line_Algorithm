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
      inputPanel.add(new JLabel("Point 1: "));
      inputPanel.add(x1Field);
      inputPanel.add(y1Field);
      inputPanel.add(new JLabel("Point 2: "));
      inputPanel.add(x2Field);
      inputPanel.add(y2Field);
      
      JPanel translatePanel = new JPanel();
      translatePanel.setLayout(new FlowLayout());
      translateXField = new JTextField(5);
      translateYField = new JTextField(5);
      translatePanel.add(new JLabel(" translation X:"));
      translatePanel.add(translateXField);
      translatePanel.add(new JLabel(" Y:"));
      translatePanel.add(translateYField);
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      
      drawButton = new JButton("그리기");
      drawButton.addActionListener(this);
      
      buttonPanel.add(drawButton);
      
      translateButton = new JButton("이동");
      translateButton.addActionListener(this);
      buttonPanel.add(translatePanel);
      
      canvas = new JPanel() {
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Draw grid
            g.setColor(Color.LIGHT_GRAY);
            int canvasWidth = getWidth(); // 캔버스 넓이
            int canvasHeight = getHeight(); // 캔버스 높이
            int cellWidth = canvasWidth / 10; // 격자 넓이
            int cellHeight = canvasHeight / 10; // 격자 높이
            for (int i = 0; i < canvasWidth; i += cellWidth) {
               g.drawLine(i, 0, i, canvasHeight);
            }
            for (int i = 0; i < canvasHeight; i += cellHeight) {
               g.drawLine(0, i, canvasWidth, i);
            }
            
            // Draw line and dots
            if (isLineDrawn) {
               // Draw line
               g.setColor(Color.RED);
               int adjustedX1 = (int) (x1 * cellWidth); // 입력받은 x1좌표
               int adjustedY1 = (int) (y1 * cellHeight); // 입력받은 y1좌표
               int adjustedX2 = (int) (x2 * cellWidth); // 입력받은 x2좌표
               int adjustedY2 = (int) (y2 * cellHeight); // 입력받은 y2좌표
               int canvasY1 = canvasHeight - adjustedY1; // 캔버스의 y1좌표
               int canvasY2 = canvasHeight - adjustedY2; // 캔버스의 y2좌표
               g.drawLine(adjustedX1, canvasY1, adjustedX2, canvasY2); // 라인그리기
               
               // Draw dots
               int startX = (int) Math.min(adjustedX1, adjustedX2); // x1좌표와 x2좌표 중 작은 좌표
               int endX = (int) Math.max(adjustedX1, adjustedX2); // x1좌표와 x2좌표 중 큰 좌표
               int startY = (int) Math.min(adjustedY1, adjustedY2); // y1좌표와 y2좌표 중 작은 좌표
               int endY = (int) Math.max(adjustedY1, adjustedY2); // y1좌표와 y2좌표 중 큰 좌표
               double dy = adjustedY2 - adjustedY1; // y 증가량
               double dx = adjustedX2 - adjustedX1; // x 증가량
               double slope = -dy/dx; // 기울기
               double slopeY = adjustedY1 - (-slope * adjustedX1); // y절편
               if(slope <= 1 && slope >= -1){ // 기울기가 1보다 작고 -1보다 클 때
                  for (int x = startX; x <= endX; x += cellWidth) {
                     if (x > startX && x < endX) { // x1좌표보다 크고 x2좌표보다 작을 때
                        double meetY = slope * x - slopeY;
                        int drawOvalY = Integer.parseInt(String.format("%.0f", meetY));
                        g.fillOval(x - cellWidth / 4, roundingCell(canvasHeight + drawOvalY) - cellHeight / 4, cellWidth / 2, cellHeight / 2);
                     }
                  }
               }else{
                  if(dx == 0){ // 기울기가 없을 때
                     for(int y = startY; y <= endY; y += cellHeight){
                        if(y > startY && y < endY){
                           g.fillOval( startX-cellHeight/4,  (canvasHeight- y) - cellHeight / 4, cellWidth / 2, cellHeight / 2);
                        }
                     }
                  }else{
                     for(int y = startY; y <= endY; y += cellHeight){
                        if(y > startY && y < endY){
                           double meetX = (y-slopeY) / -slope;
                           System.out.println(meetX+ " " + slope);
                           int drawOvalX = Integer.parseInt(String.format("%.0f", meetX));
                           g.fillOval(roundingCell(drawOvalX) - cellHeight/4,  (canvasHeight- y) - cellHeight / 4, cellWidth / 2, cellHeight / 2);
                        }
                     }
                  }
               }
            }
         }
         
         @Override
         public Dimension getPreferredSize() { //사이즈
            return new Dimension(400, 400);
         }
      };
      
      
      canvas.setBackground(Color.WHITE);
      canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      
      add(inputPanel, BorderLayout.NORTH);
      add(buttonPanel,BorderLayout.CENTER);
      add(drawButton, BorderLayout.WEST);
      add(translateButton, BorderLayout.EAST);
      add(canvas, BorderLayout.SOUTH);
      
      pack();
      setVisible(true);
   }
   
   public int roundingCell(int coordinates){
      int result;
      if(coordinates < 380 && coordinates >= 340)
         result = 360;
      else if (coordinates < 340 && coordinates >= 300)
         result = 320;
      else if (coordinates < 300 && coordinates >= 260)
         result = 280;
      else if (coordinates < 260 && coordinates >= 220)
         result = 240;
      else if (coordinates < 220 && coordinates >= 180)
         result = 200;
      else if (coordinates < 180 && coordinates >= 140)
         result = 160;
      else if (coordinates < 140 && coordinates >= 100)
         result = 120;
      else if (coordinates < 100 && coordinates >= 60)
         result = 80;
      else if (coordinates < 60 && coordinates >= 20)
         result = 40;
      else if (coordinates <= 400 && coordinates >= 380)
         result = 400;
      else if (coordinates <= 20 && coordinates >= 0)
         result = 0;
      else
         return 100000;
      return result;
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
            JOptionPane.showMessageDialog(this, "좌표를 입력해주세요!!");
         }
      } else if (e.getSource() == translateButton) {
         try {
            translateX = Double.parseDouble(translateXField.getText());
            translateY = Double.parseDouble(translateYField.getText());
            
            x1 += translateX;
            y1 += translateY;
            x2 += translateX;
            y2 += translateY;
            
            canvas.repaint();
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "이동을 위해 좌표를 입력해주세요");
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

