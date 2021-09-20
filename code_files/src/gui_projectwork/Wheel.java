package gui_projectwork;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.RotateTransition;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.Pair;


/**
 * Class for roulette wheel.
 * Creates graphics for wheel and ball and spin animation. 
 * Calculates number where ball landed.
 * @author Henry Andersson, henry.andersson<at>gmail.com
 */
public class Wheel extends StackPane {
    // Group contains all graphical elements for the wheel.
    private StackPane wheel = new StackPane();
    // Angle per segment of roulette wheel.
    private static final double ANGLE = 9.4736842f;
    // Sets the start angle for drawing the wheel segments.
    private static final double START_ANGLE = 85.135135135f;
    // NumberSet used to draw text onto wheel.
    private int[] wheelNumbers;

    // Map for number data (value, colour, angle
    Map<Integer, Pair<String, Double>> numberdata = new HashMap<>();

    /**
     * Constructor
     */
    public Wheel() {
        fillMap();
        fillBottom();
        drawSegments();
        addNumbers();
        fillTop();
    }
    
    /**
     * Method for filling the map for number data.
     * Map key = number
     * Map value = (Pair key = String of colour, value = angle)
     */
    private void fillMap() {
        numberdata.put(0, new Pair("G", 180.0));
        numberdata.put(1, new Pair("R", ANGLE * 37)); // 5
        numberdata.put(2, new Pair("B", ANGLE * 18)); // 24
        numberdata.put(3, new Pair("R", ANGLE * 33)); // 17
        numberdata.put(4, new Pair("B", ANGLE * 14)); // 22
        numberdata.put(5, new Pair("R", ANGLE * 29)); // 1
        numberdata.put(6, new Pair("B", ANGLE * 10)); // 27
        numberdata.put(7, new Pair("R", ANGLE * 25)); // 13
        numberdata.put(8, new Pair("B", ANGLE * 6)); // 33
        numberdata.put(9, new Pair("R", ANGLE * 21)); // 9
        numberdata.put(10, new Pair("B", ANGLE * 2)); // 36
        numberdata.put(11, new Pair("B", ANGLE * 24)); // 31
        numberdata.put(12, new Pair("R", ANGLE * 5)); // 15
        numberdata.put(13, new Pair("B", ANGLE * 36)); // 29
        numberdata.put(14, new Pair("R", ANGLE * 17)); // 7
        numberdata.put(15, new Pair("B", ANGLE * 32)); // 20
        numberdata.put(16, new Pair("R", ANGLE * 13)); // 3
        numberdata.put(17, new Pair("B", ANGLE * 28)); // 25
        numberdata.put(18, new Pair("R", ANGLE * 9)); // 11
        numberdata.put(19, new Pair("R", ANGLE * 7)); // 21
        numberdata.put(20, new Pair("B", ANGLE * 26)); // 6
        numberdata.put(21, new Pair("R", ANGLE * 11)); // 23
        numberdata.put(22, new Pair("B", ANGLE * 30)); // 10
        numberdata.put(23, new Pair("R", ANGLE * 15)); // 34
        numberdata.put(24, new Pair("B", ANGLE * 34)); // 2
        numberdata.put(25, new Pair("R", ANGLE * 3)); // 25
        numberdata.put(26, new Pair("B", ANGLE * 22)); // 18
        numberdata.put(27, new Pair("R", ANGLE * 1)); // 28
        numberdata.put(28, new Pair("B", ANGLE * 20)); // 14
        numberdata.put(29, new Pair("B", ANGLE * 4)); // 12
        numberdata.put(30, new Pair("R",ANGLE * 23)); // 32
        numberdata.put(31, new Pair("B", ANGLE * 8)); // 8
        numberdata.put(32, new Pair("R", ANGLE * 27)); // 19
        numberdata.put(33, new Pair("B", ANGLE * 12)); // 4
        numberdata.put(34, new Pair("R", ANGLE * 31)); // 26
        numberdata.put(35, new Pair("B", ANGLE * 16)); //16
        numberdata.put(36, new Pair("R", ANGLE * 35)); // 30
        numberdata.put(37, new Pair("G", 0.0));
    }
    
    
    public StackPane getWheel() {
        return wheel;
    }
    
    /**
     * Method for drawing segments to the wheel
     */
    private void drawSegments() {
        Pane segments = new Pane();
        double startAngle = START_ANGLE;
        for (int i = 0; i < 38; i++) {
            Arc arc = new Arc();
            arc.setStroke(Color.GOLD);
            if (i == 0) {
                arc.setFill(Color.GREEN);
            } 
            
            else if (i % 2 == 0) {
                arc.setFill(Color.RED);
            }
            else if (i == 19) {
                 arc.setFill(Color.GREEN);
            }
            else {
                arc.setFill(Color.BLACK);
            }
            arc.setCenterX(350.0f);
            arc.setCenterY(350.0f);
            arc.setRadiusX(300.0f);
            arc.setRadiusY(300.0f);
            arc.setStartAngle(startAngle);
            arc.setLength(ANGLE);
            arc.setType(ArcType.ROUND);

            segments.getChildren().add(arc);

            startAngle += ANGLE;
            
        }
        wheel.getChildren().addAll(segments);
    }
    
    /**
     * Calculates color of winner number
     * @param winner
     * @return String of black or red.
     */
    public String getWinnerColour(int winner) {
        String letter = numberdata.get(winner).getKey();
        if (letter.equals("B")) {
            return "Black";
        } 
        if (letter.equals("R")) {
            return "Red";
        } 
        return "";
    }
    
    /**
     * Method for adding the numbers to the wheel.
     */
    private void addNumbers() {
        Pane text = new Pane();
        for (int  i = 0; i <= 37; i++) {
            Text textNode = new Text(String.valueOf(i));
            int centreX = 350;
            int centreY = 330;
            int radius = 260;
            double angleText = (180 + (numberdata.get(i).getValue()));
            textNode.setFill(Color.WHITE);
            textNode.setFont(Font.font ("Tahoma", 20));
            double x = centreX + radius * (Math.cos(((numberdata.get(i).getValue())+88) * Math.PI / 180f));
            double y = centreY + radius * (Math.sin(((numberdata.get(i).getValue())+88) * Math.PI / 180f));
            textNode.relocate(x, y);
            textNode.getTransforms().add(new Rotate(angleText));
            text.getChildren().add(textNode);
        }
        wheel.getChildren().add(text);
    }
    
    /**
     * Draws bottom of the wheel.
     */
    private void fillBottom() {
        Pane pane = new Pane();

        Circle c = new Circle(350, 350, 350);
        c.setFill(Color.SADDLEBROWN);
        c.setStroke(Color.GOLD);
        pane.getChildren().add(c);

        wheel.getChildren().add(pane);
    }
    
    /**
     * Draws top of the wheel.
     */
    private void fillTop() {
        Pane pane = new Pane();

        Circle centre = new Circle(350, 350, 200);
        centre.setStroke(Color.GOLD);
        centre.setFill(Color.SADDLEBROWN);
        pane.getChildren().add(centre);

        Circle c = new Circle(350, 350, 260);
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.GOLD);
        pane.getChildren().add(c);

        wheel.getChildren().add(pane);
    }
    
    /**
     * Creates and animates the ball spin around the wheel.
     * @return winning number
     */
    public int spinBall() {
        int winner;
        Pane pane = new Pane();
        
        Circle ball = new Circle(10, Color.WHEAT);
//        ball.setCenterX(400);
//        ball.setCenterY(400);
        int min = 1080; // Ball must spin atleast 3 times
        int max = 3600; // Some maximum, 10 spins.
        int randomAngle = (int) ((Math.random() * (max - min)) + min);
        int rotationAngle = (int) (Math.round(randomAngle/ANGLE) * ANGLE);
        int centreX = 400;
        int centreY = 380;
        int radius = 270;
        pane.getChildren().add(ball);
        ball.setCenterX(550);
        ball.setCenterY(550);
        RotateTransition rt = new RotateTransition();
        rt.setDuration(Duration.millis(1000));
        rt.setNode(pane);
        rt.setFromAngle(45);
        rt.setByAngle(rotationAngle);
        rt.setCycleCount(1);
        rt.play();
        int jaannos = (rotationAngle % 360);


        wheel.getChildren().add(pane);
        return winnerNro(jaannos);
    }
    
    /**
     * Calulates the winning number by rotation angle.
     * @param rotationAngle
     * @return winning number.
     */
    private int winnerNro (int rotationAngle) {
        int closest = (int) Math.abs(numberdata.get(0).getValue());
        int key = 0;
        for (int i = 1; i < numberdata.size(); i++ ){
            int x = (int) Math.abs(numberdata.get(i).getValue() - rotationAngle);
            if (x < closest) {
                key = i;
                closest = x;
            }
        }
        return key;
    }
}


//////////////////
// Muistiinpanoja ja testikoodia
//        final int[] balackNumbers = {15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26};
//        final int[] redNumbers = {32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3};
//        for (int i = 0; i < 38; i++) {
//            ArrayList<String> list = new ArrayList<>();
//            list.add(String.valueOf(i)); // Add number
//            if (i != 0 || i != 37) {// Add color
//                for (int j : balackNumbers) {
//                    if (i == j) {
//                        list.add("B");  
//                    } else {
//                        list.add("R");
//                    }
//                }
//            } else {
//                list.add("G");
//            }
//            list.add("180");
//            numberdata.put(0, list);
//        }