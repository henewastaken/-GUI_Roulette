
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
public class Bet{
    int[] numbers;
    int amount;

    public Bet(int[] numbers, double x, double y, int amount) {
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
