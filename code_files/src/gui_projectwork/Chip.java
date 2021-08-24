
package gui_projectwork;

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author henea
 */
public class Chip extends Circle{
    int amount;
    
    public Chip(double x, double y, double r, Paint paint, StackPane g, int amount) {
        this.amount = amount;
        Circle chip = new Circle();
        chip.setRadius(r);
//        chip.setCenterX(x);
//        chip.setCenterY(y);
        chip.setFill(paint);
        
        Text text = new Text(String.valueOf(amount));
        text.setFont(Font.font(25));
        
        // If amount = 100, so chip is black, set text to white.
        if (amount == 100) {
            text.setFill(Color.WHITE);
        } else {
            text.setFill(Color.BLACK);
        }
//        text.setX(chip.getCenterX()-7);
//        text.setY(chip.getCenterY()+6);
        
        g.setLayoutX(x);
        g.setLayoutY(y);
        
        g.getChildren().addAll(chip, text);        
    }
   
    public int getAmount() {
        return amount;
    }
    
}
