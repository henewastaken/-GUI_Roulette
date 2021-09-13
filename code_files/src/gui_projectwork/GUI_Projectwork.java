package gui_projectwork;

import com.sun.javafx.geom.AreaOp;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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
    final int value = 50; // scale value for rectangle sizing. 
    Group root = new Group(); // Root group for roulette elements 
    ArrayList<Chip> allChips = new ArrayList<>();
    final int chipRadius = 15;
    ArrayList<Chip> betsList = new ArrayList<>();
    Stack<Chip> undoStack = new Stack<>(); // Stack for undo process
    Stack<Chip> redoStack = new Stack<>(); // Stack fo redo process

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        StackPane sp; // Used for creating chips
        VBox vb = new VBox();
        BorderPane bp = new BorderPane();

        // Create a menu bar
        Menu file = new Menu("_File");
        //Exititem
        MenuItem exitItem = new MenuItem("E_xit");
        exitItem.setMnemonicParsing(true);
        exitItem.setAccelerator(new KeyCharacterCombination("Q", KeyCombination.CONTROL_DOWN));
        file.getItems().add(exitItem);
        // UndoItem
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setMnemonicParsing(true);
        undoItem.setAccelerator(new KeyCharacterCombination("Z", KeyCombination.CONTROL_DOWN));
        file.getItems().add(undoItem);
        // RedoItem
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setMnemonicParsing(true);
        redoItem.setAccelerator(new KeyCharacterCombination("z", KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        file.getItems().add(redoItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file);
        root.getChildren().add(menuBar);

        // Handle close event
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });
        // Handle undo event
        undoItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (undoStack.isEmpty()) {
                    System.out.println("undo stack empty");
                } else {
                    Chip undoChip = undoStack.pop();
                    redoStack.add(undoChip);
                    root.getChildren().remove(undoChip);
                    System.out.println("undo chip numbers: " + Arrays.toString(undoChip.getNumbers()));
                }
            }
        });
        // Handle redo event
        redoItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (redoStack.isEmpty()) {
                    System.out.println("redo stack empty");
                } else {
                    Chip redoChip = redoStack.pop();
                    undoStack.add(redoChip);
                    root.getChildren().add(redoChip);
                    System.out.println("Redo chip numers: " + Arrays.toString(redoChip.getNumbers()));
                }
            }
        });

        // Create button to trigger ball spin
        Button spinbutton = new Button("Spin");
        spinbutton.setPrefSize(value * 3, value * 3);
        spinbutton.setLayoutX(value * 9);
        spinbutton.setLayoutY(380);

        // Adding elements to root group
        root.getChildren().addAll(creteRouletteTable(), spinbutton, vb);

        // Create chips and add them to group
        Chip dollarChip = new Chip(value * 10, 100, chipRadius, Color.CADETBLUE, 1);
        root.getChildren().add(dollarChip);
//        Node dollarRoot = root.getChildren().get(3);
        allChips.add(dollarChip);
//        System.out.println(dollarRoot.getClass());

        Chip fiveChip = new Chip(value * 10, 150, chipRadius, Color.RED, 5);
        root.getChildren().add(fiveChip);
//        Node fiveroot = root.getChildren().get(4);
        allChips.add(fiveChip);

        Chip tenChip = new Chip(value * 10, 200, chipRadius, Color.YELLOW, 10);
        root.getChildren().add(tenChip);
//        Node tenRoot = root.getChildren().get(5);
        allChips.add(tenChip);

        Chip twentyFiveChip = new Chip(value * 10, 250, chipRadius, Color.LIGHTGREEN, 25);
        root.getChildren().add(twentyFiveChip);
//        Node twentyFiveroot = root.getChildren().get(6);
        allChips.add(twentyFiveChip);

        Chip hundredChip = new Chip(value * 10, 300, chipRadius, Color.BLACK, 100);
        root.getChildren().add(hundredChip);
//        Node hundredRoot = root.getChildren().get(7);
        allChips.add(hundredChip);

        Label tf = new Label("Testi teksti");
        vb.getChildren().add(tf);
        vb.setLayoutX(value * 13);

        // Testit kaikille chipeille, että event toimii
        allChips.forEach(i -> {
            i.setOnMousePressed((t) -> {
//                System.out.println(i.getAmount() + " chip pressed");
            });
        });

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
                    betsHandlerer(i.getLayoutX(), i.getLayoutY(), i);
                    // Relocate the original chip to the side
                    i.relocate(i.getX(), i.getY());

                    // koko kasvaa, mutta miten saan ne liikuteltaviski?
//                    System.out.println(allChips.size());
//                    printArray(betsList);
                }
            });
        });

        bp.setTop(menuBar);
        bp.setLeft(root);
        // Show stuff
        stage.setScene(new Scene(bp));
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.show();
    }

    // Returns element closest to target in arr[]
    public double findClosest(double arr[], double target) {
        int n = arr.length;
        // Corner cases
        if (target <= arr[0]) {
            return arr[0];
        }
        if (target >= arr[n - 1]) {
            return arr[n - 1];
        }

        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
            if (arr[mid] == target) {
                return arr[mid];
            }

            // If target is less than array element, then search in left
            if (target < arr[mid]) {
                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && target > arr[mid - 1]) {
                    return getClosest(arr[mid - 1], arr[mid], target);
                }
                // Repeat for left half
                j = mid;
            } // If target is greater than mid
            else {
                if (mid < n - 1 && target < arr[mid + 1]) {
                    return getClosest(arr[mid],
                            arr[mid + 1], target);
                }
                i = mid + 1; // update i
            }
        }

        // Only single element left after search
        return arr[mid];
    }

    // Used with findClosest method. findClosest calls this.
    public double getClosest(double val1, double val2, double target) {
        if (target - val1 >= val2 - target) {
            return val2;
        } else {
            return val1;
        }
    }

    public ArrayList<Chip> betsHandlerer(double x, double y, Chip c) {
        double xsc = value + value / 2; // sscuare center locations X axis
        double xse = xsc + xsc / 2; // Square edge locations X axis
        boolean isOutside = false;

        double[] xSnapLocs = {value - value / 2, xsc, xse, xsc * 2, xse + xsc, xsc * 3, xsc * 4, xsc * 5};
        Arrays.sort(xSnapLocs);
        double yse = value / 2; // Square edge location Y axis
        // This could be done much smater, but it's midnight brain time LETSGOO!!
        double[] ySnapLocs = {0, yse, yse * 2, yse * 3, yse * 4, yse * 5, yse * 6, yse * 7, yse * 8,
            yse * 9, yse * 10, yse * 11, yse * 12, yse * 13, yse * 14, yse * 15,
            yse * 16, yse * 17, yse * 18, yse * 19, yse * 20, yse * 21, yse * 22,
            yse * 23, yse * 24, yse * 26}; //yse*25 is missing on purpose. No bet goes there

        Arrays.sort(ySnapLocs);

        // Check that is placed on roulette table
        // x (width) axix = between value and 425
        // y (height) axis = between 0 and 700
        if (x >= 0 && x <= 425 && y >= 0 && y <= 700) {
            // Calculate nearest position
            double xClosest = findClosest(xSnapLocs, x);
            double yClosest = findClosest(ySnapLocs, y);

            // Use helper list io get the index used in calculating numbers under bet
            ArrayList<Double> xhelplist = new ArrayList<>();
            for (double j : xSnapLocs) {
                xhelplist.add(j);
            }
            int xindex = xhelplist.indexOf(xClosest);

            ArrayList<Double> yhelplist = new ArrayList<>();
            for (double j : ySnapLocs) {
                yhelplist.add(j);
            }
            int yindex = yhelplist.indexOf(yClosest);

            // Limitate some coordinates manually
            if ((xindex == 0 || xindex == 2) && yindex == 0) {
                xindex = 1;
                xClosest = xSnapLocs[1];
            }
            if (xindex == 5 && yindex == 0) {
                xindex = 4;
                xClosest = xSnapLocs[4];
            }
            if (xindex > 5 && (yindex == 0 || yindex == 1)) {
                return null;
            }

            // outside bets snapping 
            // 2 to 1 bets
            if (xindex == 6) {
                isOutside = true;
                // 1-12
                if (yindex > 0 && yindex < 9) {
                    System.out.println("1-12");
                    yClosest = ySnapLocs[5];
                }
                // 13-24
                if (yindex >= 9 && yindex < 17) {
                    System.out.println("13-24");
                    yClosest = ySnapLocs[13];
                }
                // 25-36
                if (yindex >= 17 && yindex < 24) {
                    System.out.println("25-36");
                    yClosest = ySnapLocs[21];
                }
                // No bet goes here
                if (yindex > 24) {
                    return null;
                }
            }
            // 1 to 1 bets
            // checking the outer outdie bets snapping locations. One square is
            // 4 rows high, loop and check in which sqare chip is on and snap to center.
            if (xindex == 7) {
                int upperBound = 5;
                int lowerBound = 1;
                while (upperBound < 26) {
                    if (yindex >= lowerBound && yindex < upperBound) {
                        yClosest = ySnapLocs[upperBound - lowerBound - 1];
                        break;
                    }
                    upperBound += 4;
                }
                isOutside = true;
            }
            // column bets
            if (xindex > 0 && xindex <= 5 && yindex == 25) {
                isOutside = true;
                if (xindex % 2 == 0) {
                    return null;
                }
            }
            // Limit some coordinates
            if (yindex == 25 && (xindex > 5 || xindex == 0)) {
                return null;
            }

//            System.out.println("x: " + xindex+ ", y: " + yindex);
            // Create new chip from the moved chip
            Chip movedChip = new Chip(xClosest, yClosest + 5, chipRadius, c.getPaint(), c.getAmount());
            root.getChildren().add(movedChip);
            allChips.add(movedChip);

//            if (isOutside) {
//                
//            }
            int[] l = new int[1];
            if (isOutside) {
                int[] z = calculateOutsideBet(xindex, yindex);
//                System.out.println(Arrays.toString(z));
                System.arraycopy(z, 0, l, 0, l.length);
                l = z;
            } else {
                int[] z = calculateHittedNumbers(xindex, yindex);
                if (z != null) {
                    System.out.println(Arrays.toString(z));
                    System.arraycopy(z, 0, l, 0, l.length);
                    l = z;
                }
            }
            
//            l = isOutside ? calculateOutsideBet(xindex, yindex) : calculateHittedNumbers(xindex, yindex);
            System.out.println("print l: " + Arrays.toString(l));
            if (l != null) {
                // Set data to the chip
                movedChip.setNumbers(l);
                movedChip.setBetMultiplier();
                betsList.add(movedChip);
                System.out.println("moved chip numbers: " + Arrays.toString(movedChip.numbers));
//                printArray(betsList);
                undoStack.add(movedChip);
                System.out.println("undoStack size: " + undoStack.size());
            }
        }

        return betsList;
    }

    public int[] calculateOutsideBet(int xindex, int yindex) {
        int[] numbersToReturn = new int[12]; // size minimum 12
        final int[] balackNumbers = {15, 4, 2, 17, 6, 13, 11, 8, 10, 24, 33, 20, 31, 22, 29, 28, 35, 26};
        final int[] redNumbers = {32, 19, 21, 25, 34, 27, 36, 30, 23, 5, 16, 1, 14, 9, 18, 7, 12, 3};
        final int[] firstCol = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34};
        final int[] secondCol = {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35};
        final int[] thirdCol = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36};
        System.out.println("x: " + xindex + ", y: " + yindex);

        // Bets are 2 to bets
        if (xindex == 6) {

            // Use for loops to put the correct numbsers to array
            // First dozen
            if (yindex > 0 && yindex < 9) {

                for (int i = 0; i < 12; i++) {
                    numbersToReturn[i] = i + 1;
                }
            }
            // Second dozen 
            if (yindex >= 9 && yindex < 17) {
                for (int i = 0; i < 12; i++) {
                    numbersToReturn[i] = i + 13;
                }
            }
            // Third dozen
            if (yindex >= 17 && yindex < 24) {
                for (int i = 0; i < 12; i++) {
                    numbersToReturn[i] = i + 25;
                }
            }
        } else if (xindex > 0 && xindex <= 5 && yindex == 25) {
            if (xindex % 2 != 0) {
                switch (xindex) {
                    case 1:
                        return firstCol;
                    case 3:
                        return secondCol;
                    case 5:
                        return thirdCol;
                }
            }
        } // 1 to 1 bets (outer outside bets)
        else if (xindex == 7) {
            // Check red and balck
            if (yindex >= 9 && yindex <= 12) {
                return redNumbers;
            }
            if (yindex >= 13 & yindex <= 16) {
                return balackNumbers;
            }
            // increase numbersToRetrun size to match qamount of numbers needed
            int[] temp = new int[18];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);

            // Even numbers
            if (yindex >= 5 && yindex <= 8) {
                int n = 0;
                for (int i = 1; i <= 36; i++) {
                    if (i % 2 == 0) {
                        numbersToReturn[n] = i;
                        n++;
                    }
                }
            }
            // Odd numbers
            if (yindex >= 17 && yindex <= 20) {
                int n = 0;
                for (int i = 1; i <= 36; i++) {
                    if (i % 2 != 0) {
                        numbersToReturn[n] = i;
                        n++;
                    }
                }
            }

            // 1-18
            if (yindex >= 2 && yindex <= 4) {
                for (int i = 0; i < 18; i++) {
                    numbersToReturn[i] = i + 1;
                }
            }
            // 19-36
            if (yindex >= 21 && yindex <= 24) {
                for (int i = 0; i < 18; i++) {
                    numbersToReturn[i] = i + 19;
                }
            }
            return numbersToReturn;
        }
        return numbersToReturn;
    }

    /**
     * Method for calculating the numbers chip hits by coordinates.
     *
     * @param xIndex index of x axis list chip hits
     * @param yIndex index of y axis list chip hits
     * @return array of numbers chip is on top of or null if chip outside of
     * coordinates.
     */
    public int[] calculateHittedNumbers(int xIndex, int yIndex) {
        int[] numbersToReturn = new int[1];
        System.out.println("x " + xIndex + " y " + yIndex);

        int column = (int) Math.ceil(xIndex / 2);
        int row = (int) Math.ceil(yIndex / 2 - 1);

        // Use 2D array to get center numbers
        int[][] numbers = new int[13][3];
        int n = 1;

        // Add numbers 1-36 to 2d array [row][column]
        for (int col = 0; col < 12; col++) {
            for (int ro = 0; ro < 3; ro++) {
                numbers[col][ro] = n;
                n++;
            }
        }
        // calculate zero area. 37 ued for 00
        if (yIndex == 0 || yIndex == 1) {
            return calculateZeroNumbers(xIndex, yIndex);
        } /*
         Calculating center number that chip is on top of. 
         */ 
        else if (xIndex < 6 && xIndex % 2 != 0 && yIndex > 0 && yIndex % 2 == 0) {
//                System.out.println("straight");
            // Add the number to array
            numbersToReturn[0] = numbers[row][column];
            System.out.println(numbersToReturn[0]);
            return numbersToReturn;
        } /*
         Calculate the shared numbers chip is on top of.
         */ // corner up down
        else if (xIndex % 2 != 0) {
//                System.out.println("split updown");
            int[] temp = new int[2];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = numbers[row][column];
            numbersToReturn[1] = numbers[row + 1][column];

            return numbersToReturn;
        } // Street (3 adjacent numbers)
        else if (xIndex == 0 && yIndex > 0 && yIndex % 2 == 0) {
//                System.out.println("street");

            // Increase the numbersToReturn array size to match numbers in bet
            int[] temp = new int[3];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add numbers to numbersToReturn
            numbersToReturn[0] = numbers[row][0];
            numbersToReturn[1] = numbers[row][0] + 1;
            numbersToReturn[2] = numbers[row][0] + 2;

            return numbersToReturn;
        } // Double street (6 adjacent numbers)
        else if (xIndex == 0 && yIndex > 0 && yIndex % 2 != 0 && yIndex <= 23) {
//                System.out.println("double street");

            // Increase the numbersToReturn array size to match numbers in bet
            int[] temp = new int[6];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add numbers to numbersToReturn
            for (int i = 0; i < 6; i++) {
                numbersToReturn[i] = numbers[row][0] + i;
            }
            return numbersToReturn;
        } // Limitations for split and orner bet locations
        else if (xIndex < 6 && xIndex % 2 == 0 && yIndex > 0 && xIndex > 0) {

            // Calculate adjacent numbers using floor and celi functions
            int leftNro = (int) Math.floor(xIndex * 1.0 / 3);
            int rightNro = (int) Math.ceil(xIndex * 1.0 / 3);

            // Split x axix (sideways)
            if (yIndex % 2 == 0) {
//                    System.out.println("split");
                // Increase the numbersToReturn array size to 2
                int[] temp = new int[2];
                System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
                numbersToReturn = temp;

                // Add lefr and right number to numbersToReturn
                numbersToReturn[0] = numbers[row][leftNro];
                numbersToReturn[1] = numbers[row][rightNro];

                return numbersToReturn;

            } // Corner
            else if (yIndex > 1 && yIndex <= 23) {
//                    System.out.println("corner");
                // Calculate corner numbers
                int topLeft = numbers[row][leftNro];
                int topRight = numbers[row][rightNro];
                int botmLeft = topRight + 2;
                int botmRight = botmLeft + 1;

                // Increase the numbersToReturn array size to match numbers in bet
                int[] temp = new int[4];
                System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
                numbersToReturn = temp;

                // Add numbers to numbersToReturn
                numbersToReturn[0] = topLeft;
                numbersToReturn[1] = topRight;
                numbersToReturn[2] = botmLeft;
                numbersToReturn[3] = botmRight;

                return numbersToReturn;
            }
        }
        // Return null for later error checking
        return null;
    }

    /**
     * Method for calculating numbers under chip if it is around zeroes area
     *
     * @param xIndex
     * @param yIndex
     * @return
     */
    public int[] calculateZeroNumbers(int xIndex, int yIndex) {
        int[] numbersToReturn = new int[1];

        // Ugly hardcoding for bets around zeroes, because no easyer way
        // Use 37 as 00
        // 0
        if (yIndex == 0 && xIndex == 1) {
            //                System.out.println("zero");
            numbersToReturn[0] = 0;

            return numbersToReturn;
        } else if (yIndex == 0 && xIndex == 3) {
            //                System.out.println("0 & 00");
            int[] temp = new int[2];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 0;
            numbersToReturn[1] = 37;

            return numbersToReturn;
        } // 00
        else if (yIndex == 0 && xIndex == 4) {
            //                System.out.println("doublex zero");
            numbersToReturn[0] = 37;

            return numbersToReturn;
        } // Basket (0, 00, 1, 2, 3)
        else if (yIndex == 1 && xIndex == 0) {
            //                System.out.println("Basket");
            int[] temp = new int[5];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 0;
            numbersToReturn[1] = 37;
            numbersToReturn[2] = 1;
            numbersToReturn[3] = 2;
            numbersToReturn[4] = 3;

            return numbersToReturn;
        } // 0, 2
        else if (yIndex == 1 && xIndex == 1) {
            int[] temp = new int[2];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 0;
            numbersToReturn[1] = 2;

            return numbersToReturn;
        } else if (yIndex == 1 && xIndex == 2) {
            //                System.out.println("0 & 1 & 2");

            int[] temp = new int[3];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 0;
            numbersToReturn[1] = 1;
            numbersToReturn[2] = 2;

            return numbersToReturn;
        } // 0 & 00 & 2
        else if (yIndex == 1 && xIndex == 3) {
            //                System.out.println("0 & 00 & 2");
            int[] temp = new int[3];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 0;
            numbersToReturn[1] = 37;
            numbersToReturn[2] = 2;

            return numbersToReturn;
        } // 00 & 2 & 3
        else if (yIndex == 1 && xIndex == 4) {
            //                System.out.println("00 & 2 & 3");
            int[] temp = new int[3];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 37;
            numbersToReturn[1] = 2;
            numbersToReturn[2] = 3;

            return numbersToReturn;
        } // 00 & 3
        else if (yIndex == 1 && xIndex == 5) {
            //                System.out.println("00 & 3");
            int[] temp = new int[2];
            System.arraycopy(numbersToReturn, 0, temp, 0, numbersToReturn.length);
            numbersToReturn = temp;

            // Add lefr and right number to numbersToReturn
            numbersToReturn[0] = 37;
            numbersToReturn[1] = 3;

            return numbersToReturn;
        }
        return null;
    }

    /**
     * Print method for ArrayList. For testing.
     *
     * @param list ArrayList to be printed.
     */
    public void printArray(ArrayList<Chip> list) {
        list.forEach(i -> {
            System.out.print("{" + Arrays.toString(i.getNumbers()) + " , " + i.getAmount() + "}, ");
        });
        System.out.println(" ");
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
