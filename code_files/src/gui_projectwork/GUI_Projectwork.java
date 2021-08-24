package gui_projectwork;

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
    ArrayList<Node> allChips = new ArrayList<>();
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Bet> betsList = new ArrayList<>();
       
        StackPane sp; // Used for creating chips
        VBox vb = new VBox();
        int[] n = new int[6];

        // Create button to trigger ball spin
        Button spinbutton = new Button("Spin");
        spinbutton.setPrefSize(value * 3, value * 3);
        spinbutton.setLayoutX(value * 9);
        spinbutton.setLayoutY(380);

        // Adding elements to root group
//        root.getChildren().addAll(creteRouletteTable(), spinbutton, vb);

        // Create chips and add them to group
        Chip dollarChip = new Chip(value * 10, 100, 17, Color.CADETBLUE, sp = new StackPane(), 1);
        root.getChildren().add(sp);
        Node dollarRoot = root.getChildren().get(0);
        allChips.add(dollarRoot);
        
        Chip fiveChip = new Chip(value * 10, 150, 17, Color.RED, sp = new StackPane(), 5);
        root.getChildren().add(sp);
        Node fiveroot = root.getChildren().get(1);
        allChips.add(fiveroot);
        
        Chip tenChip = new Chip(value * 10, 200, 17, Color.YELLOW, sp = new StackPane(), 10);
        root.getChildren().add(sp);
        Node tenRoot = root.getChildren().get(2);
        allChips.add(tenRoot);
        
        Chip twentyFiveChip = new Chip(value * 10, 250, 17, Color.LIGHTGREEN, sp = new StackPane(), 25);
        root.getChildren().add(sp);
        Node twentyFiveroot = root.getChildren().get(3);
        allChips.add(twentyFiveroot);
        
        Chip hundredChip = new Chip(value * 10, 300, 17, Color.BLACK, sp = new StackPane(), 100);
        root.getChildren().add(sp);
        Node hundredRoot = root.getChildren().get(4);
        allChips.add(hundredRoot);

        Label tf = new Label("Testi teksti");
        vb.getChildren().add(tf);
        vb.setLayoutX(value * 13);

        // Testit kaikille chipeille, että event toimii
        for (Node i : allChips) {
            i.setOnMousePressed((t) -> {
                System.out.println(i + " chip pressed");
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
        for (Node i : allChips) {
            i.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    i.setCursor(Cursor.HAND);
                }
            });
        }

        for (Node i : allChips) {
            i.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
//                    System.out.println("Dragging detected");
                    i.setLayoutX(mouseEvent.getSceneX());
                    i.setLayoutY(mouseEvent.getSceneY());
                }
            });
        }
        
        for (Node i : allChips) {
            i.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    i.setCursor(Cursor.HAND);
                    betsHandlerer(betsList, i.getLayoutX(), i.getLayoutY(), i);
                    i.relocate(500, 100);
                    
                    // koko kasvaa, mutta miten saan ne liikuteltaviski?
                    System.out.println(allChips.size());
                }
            });
        }
//        

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

    
    
    public ArrayList<Bet> betsHandlerer(ArrayList<Bet> betsList, double x, double y, Node i) {
        StackPane sp;
//        Bet bet = new Bet(new int[]{1,2}, x, y, 17, Color.CADETBLUE, sp, 1);
//        betsList.add(bet);      
//        int index = root.getChildren().indexOf()
        
        Chip movedChip = new Chip(x, y, 17, Color.CADETBLUE, sp = new StackPane(), 1);
        root.getChildren().add(sp);
        Node makeMoovable = root.getChildren().get(root.getChildren().indexOf(sp));
        allChips.add(makeMoovable);
//        printArray(allChips);
        return betsList;
    }

    /**
     * Print method for ArrayList. For testing.
     *
     * @param list ArrayList to be printed.
     */
    public void printArray(ArrayList<Node> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).parentProperty());
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
