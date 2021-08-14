
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

    public Chip(double x, double y, double r, Paint paint, Group g, int amount) {
        Circle chip = new Circle();
        chip.setRadius(r);
        chip.setCenterX(x);
        chip.setCenterY(y);
        chip.setFill(paint);
        
        Text text = new Text(String.valueOf(amount));
        text.setFont(Font.font(25));
        text.setFill(Color.WHITE);
        text.setX(chip.getCenterX()-7);
        text.setY(chip.getCenterY()+6);
        
        g.getChildren().addAll(chip, text);        
    }
   
}
