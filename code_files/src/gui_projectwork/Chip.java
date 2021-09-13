package gui_projectwork;

import java.util.Arrays;
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
public class Chip extends StackPane {

    int[] numbers = new int[1];
    int amount;
    double x;
    double y;
    Paint paint;
    int betMultiplier;
    boolean isWinner = false;

    public Chip(double x, double y, double r, Paint paint, int amount) {
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.paint = paint;

        Circle chip = new Circle();
        chip.setRadius(r);

//        chip.setCenterX(x);
//        chip.setCenterY(y);
        chip.setFill(paint);

        Text text = new Text(String.valueOf(amount));
        text.setFont(Font.font(20));

        // If amount = 100, so chip is black, set text to white.
        if (amount == 100) {
            text.setFill(Color.WHITE);
        } else {
            text.setFill(Color.BLACK);
            chip.setStroke(Color.WHITE);
        }
//        text.setX(chip.getCenterX()-7);
//        text.setY(chip.getCenterY()+6);

        setLayoutX(x);
        setLayoutY(y);

        getChildren().addAll(chip, text);
    }

    public int getAmount() {
        return amount;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Paint getPaint() {
        return paint;
    }

    // Kun chippi asetetaan pöydälle lisätään tähän numerot jotka asetetaan betsLsitiin mainissa
    public void setNumbers(int[] n) {
//        System.out.println("in chip numbers: " + Arrays.toString(n));
        System.arraycopy(n, 0, this.numbers, 0, this.numbers.length);
        this.numbers = n;
        setBetMultiplier();
    }

    public int[] getNumbers() {
        return this.numbers;
    }

    public boolean isWinner(int winningNro) {
        if (numbers.length > 0) {
            for (int i : numbers) {
                if (i == winningNro) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean getWinner() {
        return isWinner;
    }
    public int getBetMultiplier() {
        return betMultiplier;
    }

    public void setBetMultiplier() {
        int len = this.numbers.length;
        if (len > 0) {
            // Set bet multiplier according to numbers lenght 
            switch (len) {
                case 1:
                   this.betMultiplier = 35;
                    break;
                case 2:
                   this.betMultiplier = 17; 
                   break;
                case 3:
                   this.betMultiplier = 11;
                   break;
                case 4:
                   this.betMultiplier = 8;
                   break;
                case 5:
                   this.betMultiplier = 6;
                   break;
                case 6:
                   this.betMultiplier = 5;
                   break;
                case 12:
                   this.betMultiplier = 2; 
                   break;
                case 18:
                   this.betMultiplier = 1;
                   break;
            }         
        }
    }

    public int getTotalwinnig() {
        return amount * betMultiplier;
    }
}


