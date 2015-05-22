package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This is a set of utilites that deal with converting the cube to other mediums
 * of representation and back again
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CubeIO {

    // this array is a color representation of all the cubelets, with the facelets sorted alphabetically
    // The indexes of the array represent the number of the cubelet in with the cube in default orientation
    static final String[] originalCubeletNumbers = {"gow", "bow", "brw", "grw", "goy", "boy", "bry", "gry", "ow", "bw", "rw", "gw", "go", "bo", "br", "gr", "oy", "by", "ry", "gy"};
    // an array with the letter representation of the colors that are outputed from Cubelet's getFaceColor methods
    static final String[] colors = {"w", "y", "r", "o", "b", "g"};
    // these are the same reordering arrays as used in cube. They are used to reorder arrays in 
    // generateCubeletNumberCompareStrings and generateNewCubeletNumbers
    static final int[] xNewPositions = {3, 2, 6, 7, 0, 1, 5, 4, 10, 14, 18, 15, 11, 9, 17, 19, 8, 13, 16, 12};
    static final int[] yNewPositions = {3, 0, 1, 2, 7, 4, 5, 6, 11, 8, 9, 10, 15, 12, 13, 14, 19, 16, 17, 18};

    // reorders originalCubeletNumbers so that the indexes represent the correct numbers according to faces
    // in the Translator parameter
    static String[] generateCubeletNumberCompareStrings(Translator targetTranslator) {
        // creates a fresh translator and a clone of originalCubeletNumbers
        Translator translator = new Translator();
        String[] newCubeletNumbers = originalCubeletNumbers;
        // to establish the new numbers, the method simulates rotations on both cubeletNumbers and translator
        // until translator is the same as the target. 
        boolean faceFound = false;
        int xRotations = 0;
        // to begin with, the method simulates up to 4 x rotations on translator, looking for the target face 
        for (int i = 0; i < 4; i++) {
            if (translator.faces[0] != targetTranslator.faces[0]) {
                translator.faceChange(0);
                xRotations++;
            } else {
                faceFound = true;
            }
        }

        // If it finds the correct face then it simulates the same moves on newCubeletNumbers
        if (faceFound) {
            for (int i = 0; i < xRotations; i++) {
                String[] newCubeletNumbersCopy = newCubeletNumbers.clone();
                for (int j = 0; j < 20; j++) {
                    newCubeletNumbers[j] = newCubeletNumbersCopy[xNewPositions[j]];
                }
            }
        } else { // if it doesn't find the correct face with 4 x's, then it must be on one of the sides
            // therefore, it does a y rotate and tries again
            translator.faceChange(1);
            String[] newCubeletNumbersCopy = newCubeletNumbers.clone();
            for (int j = 0; j < 20; j++) {
                newCubeletNumbers[j] = newCubeletNumbersCopy[yNewPositions[j]];
            }

            for (int i = 0; i < 4; i++) {
                if (translator.faces[0] != targetTranslator.faces[0]) {
                    translator.faceChange(0);
                    newCubeletNumbersCopy = newCubeletNumbers.clone();
                    for (int j = 0; j < 20; j++) {
                        newCubeletNumbers[j] = newCubeletNumbersCopy[xNewPositions[j]];
                    }
                } else {
                    break;
                }
            }
        }
        // the top face is the same as the target, therefore the front face must be on one of the sides
        // the method keeps doing y's until it finds correct front face
        while (translator.faces[2] != targetTranslator.faces[2]) {
            translator.faceChange(1);
            String[] newCubeletNumbersCopy = newCubeletNumbers.clone();
            for (int j = 0; j < 20; j++) {
                newCubeletNumbers[j] = newCubeletNumbersCopy[yNewPositions[j]];
            }
        }
        return newCubeletNumbers;
    }

    // reorders a number array so that the indexes represent the correct numbers according to faces
    // in the Translator parameter
    static int[] generateNewCubeletNumbers(Translator targetTranslator) {
        // creates a fresh translator and the number array
        Translator translator = new Translator();
        int[] newCubeletNumbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        // to establish the new numbers, the method simulates rotations on both cubeletNumbers and translator
        // until translator is the same as the target. 
        boolean faceFound = false;
        int xRotations = 0;
        // to begin with, the method simulates up to 4 x rotations on translator, looking for the target face 
        for (int i = 0; i < 4; i++) {
            if (translator.faces[0] != targetTranslator.faces[0]) {
                translator.faceChange(0);
                xRotations++;
            } else {
                faceFound = true;
            }
        }
        // If it finds the correct face then it simulates the same moves on the number array
        if (faceFound) {
            for (int i = 0; i < xRotations; i++) {
                int[] newCubeletNumbersCopy = newCubeletNumbers.clone();
                for (int j = 0; j < 20; j++) {
                    newCubeletNumbers[j] = newCubeletNumbersCopy[xNewPositions[j]];
                }
            }
        } else {// if it doesn't find the correct face with 4 x's, then it must be on one of the sides
            // therefore, it does a y rotate and tries again
            translator.faceChange(1);
            int[] newCubeletNumbersCopy = newCubeletNumbers.clone();
            for (int j = 0; j < 20; j++) {
                newCubeletNumbers[j] = newCubeletNumbersCopy[yNewPositions[j]];
            }

            for (int i = 0; i < 4; i++) {
                if (translator.faces[0] != targetTranslator.faces[0]) {
                    translator.faceChange(0);
                    newCubeletNumbersCopy = newCubeletNumbers.clone();
                    for (int j = 0; j < 20; j++) {
                        newCubeletNumbers[j] = newCubeletNumbersCopy[xNewPositions[j]];
                    }
                } else {
                    break;
                }
            }
        }
        // the top face is the same as the target, therefore the front face must be on one of the sides
        // the method keeps doing y's until it finds correct front face
        while (translator.faces[2] != targetTranslator.faces[2]) {
            translator.faceChange(1);
            int[] newCubeletNumbersCopy = newCubeletNumbers.clone();
            for (int j = 0; j < 20; j++) {
                newCubeletNumbers[j] = newCubeletNumbersCopy[yNewPositions[j]];
            }
        }
        return newCubeletNumbers;
    }

    // this method was written so the user could enter colors on a textfile and have the program read it
    // However, it is much easier to simply enter colors on the program using the color picker, and so
    // this method was ultimately not used
    private static Cube readColorsFromFile(String fileName) throws FileNotFoundException {
        Scanner file = new Scanner(new FileReader(fileName));

        String[] faceColors = file.nextLine().trim().split(" ");
        int[] axes = new int[3];
        if (faceColors[0].equalsIgnoreCase("W") || faceColors[0].equalsIgnoreCase("Y")) {
            axes[0] = 0;
        } else if (faceColors[0].equalsIgnoreCase("R") || faceColors[0].equalsIgnoreCase("O")) {
            axes[0] = 1;
        } else if (faceColors[0].equalsIgnoreCase("B") || faceColors[0].equalsIgnoreCase("G")) {
            axes[0] = 2;
        } else {
            System.out.println("Top face error");
        }

        if (faceColors[1].equalsIgnoreCase("W") || faceColors[1].equalsIgnoreCase("Y")) {
            axes[1] = 0;
        } else if (faceColors[1].equalsIgnoreCase("R") || faceColors[1].equalsIgnoreCase("O")) {
            axes[1] = 1;
        } else if (faceColors[1].equalsIgnoreCase("B") || faceColors[1].equalsIgnoreCase("G")) {
            axes[1] = 2;
        } else {
            System.out.println("Front face error");
        }
        String lastAxes = "012".replace(axes[0] + "", "").replace(axes[1] + "", "");
        if (lastAxes.length() == 1) {
            axes[2] = Integer.valueOf(lastAxes);
        } else {
            System.out.println("Cube axes error");//throw error
        }
//        System.out.println(axes[0] + "" + axes[1] + axes[2]);
        Translator translator = new Translator();
        int selectedVariant = translator.setTranslator(axes, faceColors);

        String[] originalNumbers = originalCubeletNumbers.clone();
        String[] newNumbers = generateCubeletNumberCompareStrings(translator);
        boolean allLoaded = true;

        Cubelet[] cubelets = new Cubelet[20];
        for (int load = 0; load < 20; load++) {
            String[] colors = file.next().trim().toLowerCase().split("");
            Arrays.sort(colors);
            String colorString = "";
            for (String s : colors) {
                colorString = colorString + s;
            }
            int originalNumber = 0;
            for (int check = 0; check < 20; check++) {
                if (colorString.equalsIgnoreCase(originalNumbers[check])) {
                    originalNumbers[check] = "";
                    originalNumber = check;
                    break;
                }
                if (check == 19) {
                    allLoaded = false;
                }
            }
            int number = 0;
            for (int check = 0; check < 20; check++) {
                if (colorString.equalsIgnoreCase(newNumbers[check])) {
                    number = check;
                    break;
                }
            }
            cubelets[load] = new Cubelet(number, originalNumber, file.nextInt(), load, translator);
        }

        if (!allLoaded) {
            System.out.println("Cubelets are wrong");//throw error
        }

        boolean[] cubePhases = {true, false, false, true, true, false};
        Cube c = new Cube(cubelets, cubePhases[selectedVariant], translator);
        if (!checkIfLegal(c)) {
            System.out.println("Cube is Illegal");
        }
        return c;
    }

    // this method generates a cube from a file with the correct formatting
    // It then sets logic to use the cube, and returns a boolean signifying that it was successful
    static boolean readNumbersFromFile(File f, CubeLogic logic) throws FileNotFoundException, ArrayIndexOutOfBoundsException, InputMismatchException {
        Scanner file = new Scanner(new FileReader(f));

        // uses the 3 face colors at the top to generate a translator
        String[] faceColors = file.nextLine().trim().split(" ");
        int[] axes = new int[3];
        if (faceColors[0].equalsIgnoreCase("W") || faceColors[0].equalsIgnoreCase("Y")) {
            axes[0] = 0;
        } else if (faceColors[0].equalsIgnoreCase("R") || faceColors[0].equalsIgnoreCase("O")) {
            axes[0] = 1;
        } else if (faceColors[0].equalsIgnoreCase("B") || faceColors[0].equalsIgnoreCase("G")) {
            axes[0] = 2;
        } else {
            return false;
        }
        if (faceColors[1].equalsIgnoreCase("W") || faceColors[1].equalsIgnoreCase("Y")) {
            axes[1] = 0;
        } else if (faceColors[1].equalsIgnoreCase("R") || faceColors[1].equalsIgnoreCase("O")) {
            axes[1] = 1;
        } else if (faceColors[1].equalsIgnoreCase("B") || faceColors[1].equalsIgnoreCase("G")) {
            axes[1] = 2;
        } else {
            return false;
        }
        String lastAxes = "012".replace(axes[0] + "", "").replace(axes[1] + "", "");
        if (lastAxes.length() == 1) {
            axes[2] = Integer.valueOf(lastAxes);
        } else {
            return false;
        }
        // makes a translator and sets the translator which generates if the cube is in phase or not
        Translator translator = new Translator();
        int selectedVariant = translator.setTranslator(axes, faceColors);
        int[] newCubeletNumbers = generateNewCubeletNumbers(translator);
        // generates an arraylist with the cubelet numbers in them
        ArrayList check = new ArrayList();
        for (int i = 0; i < 20; i++) {
            check.add(i);
        }

        // searches through the numbers, creates the cubelet, and also removes the cubelet's number from the arrayList
        // if the cubelet's number does not exist in the arraylist, it immediately exits, because the cube is invalid
        Cubelet[] cubelets = new Cubelet[20];
        for (int load = 0; load < 20; load++) {
            int number = file.nextInt();
            if (check.remove((Object) number)) {
                cubelets[load] = new Cubelet(number, newCubeletNumbers[number], file.nextInt(), load, translator);
            } else {
                return false;
            }
        }

        // makes the cube
        boolean[] cubePhases = {true, false, false, true, true, false};
        Cube c = new Cube(cubelets, cubePhases[selectedVariant], translator);

        // checks the cube to see if it's orientation is legal. It is impossible to tell if the positions are legal until
        // the program tries to solve it.
        if (!checkIfLegal(c)) {
            return false;
        }
        // closes the file
        file.close();
        // if everything works out fine, then the method sets CubeLogic and returns true
        logic.setCube(c);
        return true;
    }

    // writes numbers to a file in the correct format
    static void writeNumbersToFile(File f, Cube c) throws IOException {
        PrintWriter file = new PrintWriter(new FileWriter(f));
        // prints the top, front, and right face colors at the top
        file.println(colors[c.translator.faces[0]] + " " + colors[c.translator.faces[2]] + " " + colors[c.translator.faces[4]]);
        // prints each cubelet number and respective orientation beside it
        for (int i = 0; i < 20; i++) {
            file.println(c.getCubeletNumber(i) + " " + c.getCubeletOrientationP(i));
        }
        // saves and closes the file
        file.close();
    }

    // takes the int[][] that CubeJPanel draws from and makes it into a cube, passes the cube
    // to CubeLogic, and returns if it was successful or not
    static boolean readFromGraphicsFormat(int[][] array, CubeLogic logic) {
        // it checks if the face colors are different or not
        int[] arrayCheck = array[0].clone();
        int[] result = {0, 1, 2, 3, 4, 5};
        Arrays.sort(arrayCheck);
        if (!Arrays.equals(arrayCheck, result)) {
            return false;
        }
        // uses the face array to generate a translator
        String[] faceColors = {colors[array[0][0]], colors[array[0][2]], colors[array[0][4]]};
        int[] axes = new int[3];
        if (faceColors[0].equalsIgnoreCase("W") || faceColors[0].equalsIgnoreCase("Y")) {
            axes[0] = 0;
        } else if (faceColors[0].equalsIgnoreCase("R") || faceColors[0].equalsIgnoreCase("O")) {
            axes[0] = 1;
        } else if (faceColors[0].equalsIgnoreCase("B") || faceColors[0].equalsIgnoreCase("G")) {
            axes[0] = 2;
        } else {
            System.out.println("Top face error");
        }
        if (faceColors[1].equalsIgnoreCase("W") || faceColors[1].equalsIgnoreCase("Y")) {
            axes[1] = 0;
        } else if (faceColors[1].equalsIgnoreCase("R") || faceColors[1].equalsIgnoreCase("O")) {
            axes[1] = 1;
        } else if (faceColors[1].equalsIgnoreCase("B") || faceColors[1].equalsIgnoreCase("G")) {
            axes[1] = 2;
        } else {
            System.out.println("Front face error");
        }
        String lastAxes = "012".replace(axes[0] + "", "").replace(axes[1] + "", "");
        if (lastAxes.length() == 1) {
            axes[2] = Integer.valueOf(lastAxes);
        } else {
            System.out.println("Cube axes error");//throw error
            return false;
        }
        // makes a translator and sets the translator which generates if the cube is in phase or not
        Translator translator = new Translator();
        int selectedVariant = translator.setTranslator(axes, faceColors);
        // generates the compare strings and the Cube constructor's cubelet array
        String[] originalNumbers = originalCubeletNumbers.clone();
        String[] newNumbers = generateCubeletNumberCompareStrings(translator);
        Cubelet[] cubelets = new Cubelet[20];

        // scans each cubelet in order of position and adds them to the array
        for (int load = 0; load < 20; load++) {
            int[] faceNumbers = array[load + 1];
            // using the top faces, it determines what orientation the cubelets are in
            int orientation;
            if (load < 8) {
                if (faceNumbers[0] == array[0][0] || faceNumbers[0] == array[0][1]) {
                    orientation = 0;
                } else if (faceNumbers[0] == array[0][2] || faceNumbers[0] == array[0][3]) {
                    orientation = 1;
                } else {
                    orientation = 2;
                }
            } else {
                if (faceNumbers[0] == array[0][0] || faceNumbers[0] == array[0][1]) {
                    orientation = 0;
                } else if (faceNumbers[0] == array[0][4] || faceNumbers[0] == array[0][5]) {
                    orientation = 1;
                } else {
                    if (faceNumbers[1] == array[0][0] || faceNumbers[1] == array[0][1]) {
                        orientation = 1;
                    } else {
                        orientation = 0;
                    }
                }
            }
            // sorts faceNumbers and generates colorstring in aphabetical order
            Arrays.sort(faceNumbers);
            String colorString = "";
            for (int i : faceNumbers) {
                colorString = colors[i] + colorString;
            }
            // matches the cubelet and deletes the entry to prevent duplicates
            int originalNumber = 0;
            for (int check = 0; check < 20; check++) {
                if (colorString.equalsIgnoreCase(originalNumbers[check])) {
                    originalNumbers[check] = "";
                    originalNumber = check;
                    break;
                }
                if (check == 19) {
                    return false;
                }
            }
            int number = 0;
            for (int check = 0; check < 20; check++) {
                if (colorString.equalsIgnoreCase(newNumbers[check])) {
                    number = check;
                    break;
                }
            }
            cubelets[load] = new Cubelet(number, originalNumber, orientation, load, translator);
        }
        // creates the cube
        boolean[] cubePhases = {true, false, false, true, true, false};
        Cube c = new Cube(cubelets, cubePhases[selectedVariant], translator);
        // checks if the cube's orientations are legal
        if (!checkIfLegal(c)) {
            return false;
        }
        // if everything checks out, then it passes the cube to logic
        logic.setCube(c);
        return true;
    }

    // generates a int[][] based on a cube
    static int[][] writeToGraphicsFormat(Cube c) {
        int[][] array = new int[21][];
        // sets the first array to the faces array in the cube's translator
        array[0] = c.translator.faces;
        // sets the other elements to the arrays generated by the cubelets
        for (int i = 1; i < 21; i++) {
            array[i] = c.getCubeletFaceColors(i - 1);
        }
        return array;
    }

    // checks to orientations on a cube to see if it's legal
    private static boolean checkIfLegal(Cube c) {
        boolean[] isCorner = {true, false, true, false, false, true, false, true};
        // the orientation of the corners need to be weighed slightly differently for the orientation formula to work
        int cornerSum = 0;
        for (int i = 0; i < 8; i++) {
            if (!isCorner[c.cubeletPositions[i].originalNumber]) {
                cornerSum += c.cubeletPositions[i].orientation == 0 ? 0 : c.cubeletPositions[i].orientation == 1 ? 2 : 1;
            } else {
                cornerSum += c.cubeletPositions[i].orientation;
            }
        }
        int edgeSum = 0;
        for (int i = 8; i < 20; i++) {
            edgeSum += c.cubeletPositions[i].orientation;
        }
        // the sum of the corner's orientations, once they have been weighted, must be divisable by 3
        // while only an even number of edges can be in one orientation or another
        return cornerSum % 3 == 0 && edgeSum % 2 == 0;
    }
}
