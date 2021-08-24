
package gui_projectwork;

import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Class for a single bet. 
 * Has numbers and amount
 * @author Henry Andersson
 */
public class Bet extends Chip{
    int[] numbers;
    int amount;

    public Bet(int[] numbers, double x, double y, double r, Paint paint, StackPane g, int amount) {
        super(x, y, r, paint, g, amount);
        this.numbers = numbers;
        this.amount = amount;
    }

    public int[] getNumbers() {
        return numbers;
    }

    // Override toString method. Mostly for testing purposes.
    @Override
    public String toString() {
        return "Bet{" + "numbers=" + Arrays.toString(numbers) + " amount: " + amount + '}';
    }
    

}
