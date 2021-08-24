package gui_projectwork;

import com.sun.javafx.geom.AreaOp;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * A roulette calculator.
 *
 * @author Henry Andersson, henry.andersson(at)tuni.fi
 *
 */
public class GUI_Projectwork extends Application {

    Integer blackNumbersHelper[] = new Integer[]{2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    ArrayList blackNumbers = new ArrayList(Arrays.asList(blackNumbersHelper));
    int value = 50; // scale value for rectangle sizing. 
    Group root = new Group(); // Root group for roulette elements 
    ArrayList<Chip> allChips = new ArrayList<>();
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Bet> betsList = new ArrayList<>();
       
        StackPane sp; // Used for creating chips
        VBox vb = new VBox();

        // Create button to trigger ball spin
        Button spinbutton = new Button("Spin");
        spinbutton.setPrefSize(value * 3, value * 3);
        spinbutton.setLayoutX(value * 9);
        spinbutton.setLayoutY(380);

        // Adding elements to root group
        root.getChildren().addAll(creteRouletteTable(), spinbutton, vb);

        // Create chips and add them to group
        Chip dollarChip = new Chip(value * 10, 100, 17, Color.CADETBLUE, 1);
        root.getChildren().add(dollarChip);
//        Node dollarRoot = root.getChildren().get(3);
        allChips.add(dollarChip);
//        System.out.println(dollarRoot.getClass());
        
        Chip fiveChip = new Chip(value * 10, 150, 17, Color.RED, 5);
        root.getChildren().add(fiveChip);
//        Node fiveroot = root.getChildren().get(4);
        allChips.add(fiveChip);
        
        Chip tenChip = new Chip(value * 10, 200, 17, Color.YELLOW, 10);
        root.getChildren().add(tenChip);
//        Node tenRoot = root.getChildren().get(5);
        allChips.add(tenChip);
        
        Chip twentyFiveChip = new Chip(value * 10, 250, 17, Color.LIGHTGREEN, 25);
        root.getChildren().add(twentyFiveChip);
//        Node twentyFiveroot = root.getChildren().get(6);
        allChips.add(twentyFiveChip);
        
        Chip hundredChip = new Chip(value * 10, 300, 17, Color.BLACK, 100);
        root.getChildren().add(hundredChip);
//        Node hundredRoot = root.getChildren().get(7);
        allChips.add(hundredChip);

        Label tf = new Label("Testi teksti");
        vb.getChildren().add(tf);
        vb.setLayoutX(value * 13);

        // Testit kaikille chipeille, että event toimii
        for (Chip i : allChips) {
            i.setOnMousePressed((t) -> {
                System.out.println(i.getAmount() + " chip pressed");
        });}
//        fiveroot.setOnMousePressed((t) -> {
//            System.out.println("5 Dollar chip pressed");
//        });
//        tenRoot.setOnMousePressed((t) -> {
//            System.out.println("10 Dollar chip pressed");
//        });
//        twentyFiveroot.setOnMousePressed((t) -> {
//            System.out.println("25 Dollar chip pressed");
//        });
//        hundredRoot.setOnMousePressed((t) -> {
//            System.out.println("100 Dollar chip pressed");
//        });
 
        // Dragging operations for dollar chip
        allChips.forEach(i -> {
            i.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    i.setCursor(Cursor.HAND);
                }
            });
        });

        allChips.forEach(i -> {
            i.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
//                    System.out.println("Dragging detected");
                    i.setLayoutX(mouseEvent.getSceneX());
                    i.setLayoutY(mouseEvent.getSceneY());
                }
            });
        });
        
        allChips.forEach(i -> {
            i.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    i.setCursor(Cursor.HAND);
                    betsHandlerer(betsList, i.getLayoutX(), i.getLayoutY(), i);
                    i.relocate(i.getX(), i.getY());
                    
                    // koko kasvaa, mutta miten saan ne liikuteltaviski?
                    System.out.println(allChips.size());
                }
            });
        });       

        // Luo tämä raahaustoiminnon sisällä ja tallenna listaan?
//        dollarChip = new Bet(new int[] {1,2}, value*10, 100, 17, Color.CADETBLUE, sp, 1);
//        betsList.add((Bet) dollarChip);
//        printArray(betsList);
        // Show stuff
        stage.setScene(new Scene(root));
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.show();
    }

    // Returns element closest to target in arr[]
    public double findClosest(double arr[], double target) {
        int n = arr.length;
        // Corner cases
        if (target <= arr[0])
            return arr[0];
        if (target >= arr[n - 1])
            return arr[n - 1];
        
        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
            if (arr[mid] == target)
                return arr[mid];
            
            // If target is less than array element, then search in left
            if (target < arr[mid]) {
                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && target > arr[mid - 1])
                    return getClosest(arr[mid - 1], arr[mid], target);
                // Repeat for left half
                j = mid;             
            }
 
            // If target is greater than mid
            else {
                if (mid < n-1 && target < arr[mid + 1])
                    return getClosest(arr[mid],
                          arr[mid + 1], target);               
                i = mid + 1; // update i
            }
        }
 
        // Only single element left after search
        return arr[mid];
    }
    // Used with findClosest method. findClosest calls this.
    public double getClosest(double val1, double val2, double target) {
        if (target - val1 >= val2 - target)
            return val2;       
        else
            return val1;       
    }
    
    
    public ArrayList<Bet> betsHandlerer(ArrayList<Bet> betsList, double x, double y, Chip i) {
        double xsc = value + value / 2; // sscuare center locations X axis
        double xse = xsc + xsc / 2; // Square edge locations X axis
        double[] xSnapLocs = {value - value / 2, xsc, xse, xsc*2, xse + xsc, xsc*3, xsc*4, xsc*5 };
        
        double yse = value / 2; // Square edge location Y axis
        // This could be done much smater, but it's midnight brain time LETSGOO!!
        double[] ySnapLocs = {0,yse,yse*2,yse*3,yse*4,yse*5,yse*6,yse*7,yse*8, 
                             yse*9,yse*10,yse*11, yse*12, yse*13,yse*14,yse*15,
                             yse*16,yse*17,yse*18,yse*19,yse*20,yse*21,yse*22,
                             yse*23,yse*24,yse*26}; //yse*25 is missing on purpose. No bet goes there
        
        Arrays.sort(ySnapLocs);
        // Calculate nearest position
        double xClosest = findClosest(xSnapLocs, x);
        double yClosest = findClosest(ySnapLocs, y);
        System.out.println("closest " + yClosest);
//        int amount = i.parentProperty();
//        Bet bet = new Bet(new int[]{1,2}, x, y, 17, Color.CADETBLUE, sp, 1);
//        betsList.add(bet);      
//        int index = root.getChildren().indexOf()
        System.out.println(i.getAmount() + " " + i.getX());
        // Check that is placed on roulette table
        // x (width) axix = between value and 425
        // y (height) axis = between 0 and 700
        if (x >=0 && x <= 425 && y >= 0 && y <= 700) {
            
            Chip movedChip = new Chip(xClosest, yClosest + 5, 17, i.getPaint(), i.getAmount());
            root.getChildren().add(movedChip);
//            Node makeMoovable = root.getChildren().get(root.getChildren().indexOf(movedChip));
            allChips.add(movedChip);
            System.out.println("moved chip amount " + movedChip.getAmount());
            calculateHittedNumbers(xClosest, yClosest);
            Bet bet = new Bet(new int[] {1}, x, y, movedChip.getAmount());
        }
//        printArray(allChips);
        return betsList;
    }

    /**
     * Method for calculating the numbers chip hits by coordinates
     */
    public int[] calculateHittedNumbers(double x, double y) {
        int[] numbers = new int[1];
        
        double xNumber = (x + value) % value*1.5;
        System.out.println("on calculated, x: " + x + ", y: " + y);
        return numbers;
    }
    
    /**
     * Print method for ArrayList. For testing.
     *
     * @param list ArrayList to be printed.
     */
    public void printArray(ArrayList<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * Method for creating roulette table layout.
     *
     * @return Group with all the elements of the roulette table
     */
    public Group creteRouletteTable() {
        Group g = new Group();
        Rectangle rtgl;

        // Jos jokasen ruudulle tekisi oman metodin käyttäen stackpanea
        // ja lisäisi ne sitten grouppiin, teksti olisi keskellä.
        // Ruutuolio voi olla myös tarpeellinen kun laskee chipin numeroita.
        int squareLoc = value, offset = value, x = value, y = value;
        String text;
        // Loop for creating numbers 1 to 36
        for (int i = 1; i <= 39; i++) {
            rtgl = new Rectangle(x, y, (offset * 1.5), offset);

            // Adding colours inside numbers
            if (i <= 36 && blackNumbers.contains(i)) {
                rtgl.setFill(Color.BLACK);
            } else if (i <= 36) {
                rtgl.setFill(Color.RED);
            } else {
                rtgl.setFill(Color.GREEN);
            }

            // Adding numbers inside rectangles
            text = String.valueOf(i);
            if (i > 36) {
                text = "2to1";
            }
            Text numbers = new Text(text);
            numbers.setX(x);
            numbers.setY(y + offset);
            numbers.setFont(Font.font(value - 15));

            // Adding locations to numbers
            if (i % 3 == 0 && i > 0) {
                y += value;
                x = value;
            } else {
                x += (offset * 1.5);
            }
            // Setting colours for stroke and text
            rtgl.setStroke(Color.WHITE);
            numbers.setFill(Color.YELLOW);

            // Add rectangle with numbre to group
            g.getChildren().addAll(rtgl, numbers);
        }

        // Adding zeroes
        double zeroOffset = (offset * 1.5) + (offset * 1.5) / 2; // offset value for zero text
        text = "0"; // Zero text
        Text zeroText;

        // Adding 0 and 00 in a loop using offsets. 
        for (int i = 0; i < 2; i++) {
            zeroText = new Text();
            // offset = offsets the rectangle x position
            // zeroOffset = ceates the x length for rectange
            // squareLoc = gets the Y length for rectangle
            rtgl = new Rectangle(offset, 0, zeroOffset, squareLoc);

            rtgl.setFill(Color.GREEN);

            // Adding numbers inside rectangles
            zeroText.setText(text);
            zeroText.setX(offset);
            zeroText.setY(value);
            zeroText.setFont(Font.font(value - 10));
            zeroText.setFill(Color.YELLOW);

            // Change text and location for 00 rectangle
            text = "00";
            offset += zeroOffset;

            rtgl.setStroke(Color.WHITE);
            g.getChildren().addAll(rtgl, zeroText);
        }

        // Adding inner outside bets
        text = "1ST12";
        Text outsideBetText;
        int outsideOffset = value;
        for (int i = 1; i <= 3; i++) {
            //q                     loc         loc         size        size
            rtgl = new Rectangle(offset, outsideOffset, value * 1.5, value * 4);
            rtgl.setFill(Color.GREEN);
            rtgl.setStroke(Color.WHITE);

            // Adding numbers inside rectangles
            outsideBetText = new Text();
            outsideBetText.setText(text);
            outsideBetText.setX(offset - 10);
            outsideBetText.setY(outsideOffset + value * 2);
            outsideBetText.setFont(Font.font(value - 10));
            outsideBetText.setFill(Color.YELLOW);
            outsideBetText.setRotate(90);

            g.getChildren().addAll(rtgl, outsideBetText);

            // Change text
            if (i == 1) {
                text = "2ND12";
            }
            if (i == 2) {
                text = "3RD12";
            }
            // offset Y cordinate
            outsideOffset += value * 4;
        }
        int outerOffset = value;
        Color colour = Color.YELLOW;
        // Adding outser outside bets
        for (int i = 1; i <= 6; i++) {
            // change text
            if (i == 1) {
                text = "1TO18";
            }
            if (i == 2) {
                text = "EVEN";
            }
            if (i == 3) {
                text = "RED";
                colour = Color.RED;
            }
            if (i == 4) {
                text = "BLACK";
                colour = Color.BLACK;
            }
            if (i == 5) {
                text = "ODD";
                colour = Color.YELLOW;
            }
            if (i == 6) {
                text = "18TO36";
            }

            //q                         loc                loc          size        size
            rtgl = new Rectangle(offset + value * 1.5, outerOffset, value * 1.5, value * 2);
            rtgl.setFill(Color.GREEN);
            rtgl.setStroke(Color.WHITE);

            // Adding numbers inside rectangles
            outsideBetText = new Text();
            outsideBetText.setText(text);
            outsideBetText.setX(offset + value * 1.5);
            outsideBetText.setY(outerOffset + value + 10);
            outsideBetText.setFont(Font.font(value - 20));
            outsideBetText.setFill(colour);
            outsideBetText.setRotate(90);

            g.getChildren().addAll(rtgl, outsideBetText);

            // offset Y cordinate
            outerOffset += value * 2;
        }
        return g;
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
