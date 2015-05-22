package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This is a sort of utilities class that stores methods used in string manipulation.
 * It is used because the various classes use strings to transfer moves between eachother, and
 * thus formatting is required to ensure that the moves will be processed correctly
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TextManipulator {

    // This method is used to output a text file containing moves as code 
    private static void printCases() throws FileNotFoundException {
        Scanner read = new Scanner(new FileReader("PLLCases.txt"));
        while (read.hasNext()) {
            String s = read.nextLine();
            Scanner scan = new Scanner(s);

            System.out.println("case number: " + scan.next());

            while (scan.hasNext()) {
                String s1 = scan.next().trim();
                if (s1.length() == 0) {
                } else if (s1.length() == 1) {
                    System.out.println("testCube." + s1 + "();");
                } else if (s1.length() == 2) {
                    System.out.print("testCube." + s1.charAt(0));
                    if (s1.charAt(1) == "'".charAt(0) || s1.charAt(1) == "’".charAt(0)) {
                        System.out.println("Prime();");
                    } else if (s1.charAt(1) == "2".charAt(0)) {
                        System.out.println("Squared();");
                    }
                }
            }
            System.out.println("");
        }
    }

    /* This method takes a string and converts it to be compatible with the console of Arcus,
     * a rubik's cube simulator program
     */
    static void printForArcus(String s) {
        // Arcus does not support x,y,z turn notation, instead, it opts for the "all" of a move
        s = s.replace("z", "Fa");
        s = s.replace("y", "Ua");
        s = s.replace("x", "Ra");

        // The method then strips the string of spaces and prints the resulting moves
        char[] c = s.toCharArray();
        for (char c2 : c) {
            if (c2 == " ".charAt(0)) {
            } else {
                System.out.print(c2);
            }
        }
        System.out.println("");
    }

    /* This method takes a string and strips the x, y, and z moves out of it, while 
     * translating moves to there non-turn equivalent
     */
    static String stripTurns(String s) {
        String moves = "";

        // the method goes through the string and only activates when it hits an x,y, or z
        for (int i = 0; i < s.length(); i++) {
            int moveType = -1;
            if (s.charAt(i) == 120) {
                moveType = 0;
            } else if (s.charAt(i) == 121) {
                moveType = 1;
            } else if (s.charAt(i) == 122) {
                moveType = 2;
            } else {
                moves = moves + s.charAt(i);
            }
            // if the method hits a x,y,or z, it applies the change to the rest of the string
            // it recursively calls itself, so that each call only has to translate one type of turn
            if (moveType != -1) {
                if (s.charAt(i + 1) == 39) {
                    return moves + translateMoves(stripTurns(s.substring(i + 2)), moveType, 3);
                } else if (s.charAt(i + 1) == 50) {
                    return moves + translateMoves(stripTurns(s.substring(i + 2)), moveType, 2);
                } else {
                    return moves + translateMoves(stripTurns(s.substring(i + 1)), moveType, 1);
                }
            }
        }
        return moves;
    }

    /* This method is the helper method for strip turns. It will return the non-turn equivilent 
     * of any move entered. rotationType refers to the rotation type (0 = x, 1 = y, 2 = z) and times
     * refers to the number of normal rotations to do (1 = normal, 2 = squared, 3 = prime)
     */
    private static String translateMoves(String moves, int rotationType, int times) {
        // this method is a simple mass replace that changes moves that happen perpendicular to the
        // rotation. For example, a R is still a R regardless of how many x's are done
        for (int i = 0; i < times; i++) {
            switch (rotationType) {
                case 0: // X
                    moves = moves.replace("F", "f");
                    moves = moves.replace("B", "b");
                    moves = moves.replace("U", "u");
                    moves = moves.replace("D", "d");

                    moves = moves.replace("f", "D");
                    moves = moves.replace("u", "F");
                    moves = moves.replace("d", "B");
                    moves = moves.replace("b", "U");
                    break;
                case 1: // Y
                    moves = moves.replace("F", "f");
                    moves = moves.replace("R", "r");
                    moves = moves.replace("L", "l");
                    moves = moves.replace("B", "b");

                    moves = moves.replace("f", "R");
                    moves = moves.replace("r", "B");
                    moves = moves.replace("l", "F");
                    moves = moves.replace("b", "L");
                    break;
                case 2: // Z
                    moves = moves.replace("R", "r");
                    moves = moves.replace("L", "l");
                    moves = moves.replace("U", "u");
                    moves = moves.replace("D", "d");

                    moves = moves.replace("u", "L");
                    moves = moves.replace("d", "R");
                    moves = moves.replace("r", "U");
                    moves = moves.replace("l", "D");
                    break;
            }
        }
        return moves;
    }

    /* This method takes a string and removes the brackets from them. Was primarily used
     * to strip the finger-trick notation brackets from the text versions of case solutions
     */
    static String stripCharacters(String s) {
        char[] c = s.toCharArray();
        String r = "";
        for (char c2 : c) {
            if (c2 == 40 || c2 == 41) {
            } else {
                r = r + c2;
            }
        }
        return r;
    }

    /* This method ensures that any extra spaces between tokens in a string are deleted.
     * It is used to ensure that no blank substrings appear when a string is split by spaces
     */
    static String fixSpacing(String s) {
        // a simple loop utilizing Scanner and its tokens system to delete extra spaces
        Scanner scan = new Scanner(s);
        String t = "";
        while (scan.hasNext()) {
            t = t + scan.next() + " ";
        }
        return t;
    }

    /* This method checks to see if a set of moves is valid to enter into Cube's doMove method
     */
    static boolean movesValid(String s) {
        String[] moves = fixSpacing(s.toUpperCase().trim()).split(" ");
        // a loop that checks that each letter represents a valid move and that the following
        // prefix is either a 2 or a '
        // It returns false if it detects anything wrong
        for (String currentMove : moves) {
            if (currentMove.length() > 2) {
                return false;
            }
            switch (currentMove.charAt(0)) {
                case 70:// F
                    break;
                case 66:// B
                    break;
                case 85:// U
                    break;
                case 68:// D
                    break;
                case 82:// R
                    break;
                case 76:// L
                    break;
                case 88:// x
                    break;
                case 89:// y
                    break;
                case 90:// z
                    break;
                default:
                    return false;
            }
            if (currentMove.length() == 2) {
                if (currentMove.charAt(1) == 39 || currentMove.charAt(1) == 50) {
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    // converts the output from scramble to standard notation
    static String convertScramble(String[] s) {
        String moves = "";
        for (String current : s) {
            current = current.replace("Prime", "'");
            current = current.replace("Squared", "2");
            moves = moves + current + " ";
        }
        return moves;
    }
}
