
package gui_projectwork;

import com.sun.javafx.tk.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * A roulette calculator. 
 * 
 * @author Henry Andersson, henry.andersson(at)tuni.fi
 * 
 */
public class GUI_Projectwork extends Application {

    Integer blackNumbersHelper[] = new Integer[] {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
    ArrayList blackNumbers = new ArrayList(Arrays.asList(blackNumbersHelper));
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group g = new Group();
        StackPane sp = new StackPane();
        Rectangle rtgl = null;
       
        // Call table create method
        creteRouletteTable(g, rtgl);
        
        // Show stuff
        stage.setScene(new Scene(g));
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.show();        
    }   
    
    /**
     * Method for creating roulette table.
     * @param g = Group used to lay text on top rectangle
     * @param rtgl = rectangle for one number on the table
     */
    public void creteRouletteTable (Group g, Rectangle rtgl) {
         
        int value = 50; // scale value for rectangle sizing. 
        int squareLoc = value, offset = value, x = value, y = value;
        
        // Loop for creating numbers 1 to 36
        for (int i = 1; i <= 39; i++) {
            rtgl = new Rectangle(x, y, (offset + offset / 2), offset);
            
            // Adding colours inside numbers
            if (i <= 36 && blackNumbers.contains(i)) {
                rtgl.setFill(Color.BLACK);
            } else if (i <= 36) {
                rtgl.setFill(Color.RED);
            } else {
                rtgl.setFill(Color.GREEN);
            }  
            
            // Adding numbers inside rectangles
            String text = String.valueOf(i);
            if (i > 36) {
                text = "2to1";
            }
            Text numbers = new Text(text);
            numbers.setX(x);
            numbers.setY(y+offset);
            numbers.setFont(Font.font(value-15));
            
            // Adding locations to numbers
            if (i % 3 == 0 && i > 0) {
                y += value;
                x = value;
            } else {
                x += (offset + offset / 2);
            }
            // Setting colours for stroke and text
            rtgl.setStroke(Color.WHITE);
            numbers.setFill(Color.YELLOW);

            // Add rectangle with numbre to group
            g.getChildren().addAll(rtgl, numbers);
        }
        
        // Adding zeroes
        int zeroOffset = (offset + offset / 2) + (offset + offset / 2) / 2; // offset value for zero text
        String zero = "0"; // Zero text
        Text zeroText;
        
        // Adding 0 and 00 in a loop using offsets. 
        // For some reason loop needs to be ran 3 times for code to work
        for (int i = 0; i <= 2; i++ ) {
            zeroText = new Text();
            // offset = offsets the rectangle x position
            // zeroOffset = ceates the x length for rectange
            // squareLoc = gets the Y length for rectangle
            rtgl = new Rectangle(offset, 0, zeroOffset, squareLoc);
            
            rtgl.setFill(Color.GREEN);
            
            // Adding numbers inside rectangles
            zeroText.setText(zero);
            zeroText.setX(offset);
            zeroText.setY(value);
            zeroText.setFont(Font.font(value-10));
            zeroText.setFill(Color.YELLOW);
            
            // Change text and location for 00 rectangle
            if (i == 1) {
                zero = "00";
                offset += zeroOffset;
            }
            
            rtgl.setStroke(Color.WHITE);
            g.getChildren().addAll(rtgl, zeroText);
        }
    }
}




// Tests and notes

//        GridPane mainGrid = new GridPane();
//        mainGrid.setPadding(new Insets(10, 10, 10, 10));
//        
//        // Creating a grid for the roulette layout
//        GridPane rouletteGrid = new GridPane();

        // (horisonal, vertical) 
//        rouletteGrid.add(new Button("1"), 0, 0);
//        rouletteGrid.add(new Button("2"), 1, 0);
//        rouletteGrid.add(new Button("3"), 2, 0);
//        rouletteGrid.add(new Button("Fi\nr\n  st\n \n dos"), 3, 1);
//        rouletteGrid.add(new Button("4"), 0, 1);
//        rouletteGrid.add(new Button("5"), 1, 1);
//        rouletteGrid.add(new Button("6"), 2, 1);
//        rouletteGrid.add(new Button("7"), 0, 2);
//        rouletteGrid.add(new Button("8"), 1, 2);
//        rouletteGrid.add(new Button("9"), 2, 2);
//        rouletteGrid.add(new Button("10"), 0, 3);
//        rouletteGrid.add(new Button("11"), 1, 3);
//        rouletteGrid.add(new Button("12"), 2, 3);
        
//        
//        // Creating the statistics layouts
//        VBox testiYlempi = new VBox(new Text("TEsti ylempi"));
//        VBox testiAlempo = new VBox(new Text("TEsti alempi"));
//        
//        // Adding components to mainGrid
//        mainGrid.setHgap(10);
//        mainGrid.add(rouletteGrid, 0, 0);
//        mainGrid.add(testiYlempi, 1, 0);
//        mainGrid.add(testiAlempo, 1, 1);