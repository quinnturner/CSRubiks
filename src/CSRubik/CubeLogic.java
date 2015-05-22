package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This class is the central hub of the application. It acts as a client for the various objects,
 * an executor for the various utilities classes, and as the controller behind the graphics
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class CubeLogic {

    // this is the array that CubeJPanel draws based on
    int[][] faceletColors;
    // the cube that is currently loaded in the program at any given time
    Cube cube;
    // this is where moves are stored to be executed
    String[] solveMoves;
    String moves;
    // the current move that is displayed on the cube, out of the queue
    int movesIndex = -1;
    // the number of moves currently queued up
    int numMoves;
    // the current instance of the class that is shared across the various graphics classes
    static CubeLogic currentInstance;
    // the currently selected color that clicking on a facelet will paint
    int paintColor;
    // checking if the cube is legal
    boolean CubeLegal;

    // basic constructor for CubeLogic; serves as a placeholder to avoid NullPointerExceptions
    CubeLogic() {
        cube = new Cube();
        faceletColors = CubeIO.writeToGraphicsFormat(cube);
    }

    // passes a cubelogic so it can be accessed among all classes
    static void setCurrentInstance(CubeLogic logic) {
        currentInstance = logic;
    }

    // returns the current running instance of CubeLogic
    static CubeLogic getCurrentInstance() {
        return currentInstance;
    }

    // resets CubeLogic to default values, especially useful in resetting the move queues
    void reset() {
        cube = new Cube();
        faceletColors = CubeIO.writeToGraphicsFormat(cube);
        CubeLegal = true;
        moves = "";
        solveMoves = TextManipulator.fixSpacing(moves).split(" ");
        movesIndex = -1;
    }

    // sets the currently running cube and the faceletColors displayed
    void setCube(Cube c) {
        cube = c;
        faceletColors = CubeIO.writeToGraphicsFormat(cube);
    }

    // returns CubeLegal
    boolean checkLegal() {
        return CubeLegal;
    }

    // reads whatever faceletColors and sets cube to the result
    void readCube() {
        CubeLegal = CubeIO.readFromGraphicsFormat(faceletColors, this);
    }

    // reads a cube from a file and sets it as the current cube
    void setCubeToFile(File f) throws FileNotFoundException {
        // catches exceptions and sets flags to tell frameform to display error messages
        try {
            CubeLegal = CubeIO.readNumbersFromFile(f, this);
        } catch (ArrayIndexOutOfBoundsException e) {
            CubeLegal = false;
        } catch (InputMismatchException e) {
            CubeLegal = false;
        }
    }

    // changes the specified facelet to a certain color
    void setFaceletColor(int i, int j, int color) {
        faceletColors[i][j] = color;
    }

    // sets paintcolor
    void setPaintColor(int color) {
        this.paintColor = color;
    }

    // sets the current move queue to the output of the solver
    boolean setMovesToSolve() {
        Solver solve = new Solver(cube);
        // SolverNewBroken solve = new SolverNewBroken(cube);
        moves = solve.getSolve();
        numMoves = solve.getNumMoves();
        solveMoves = TextManipulator.fixSpacing(moves).split(" ");
        movesIndex = -1;
        return solve.c.isSolved();
    }

    // sets the current move queue to the string
    void setMoves(String s) {
        moves = s;
        solveMoves = TextManipulator.fixSpacing(moves).split(" ");
        movesIndex = -1;
    }

    // have the cube execute the next move and set faceletColors to the result
    void advanceMoves() {
        try {
            if (movesIndex < solveMoves.length - 1) {
                cube.doMove(solveMoves[++movesIndex]);
            }
            faceletColors = CubeIO.writeToGraphicsFormat(cube);
        } catch (NullPointerException n) {
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    // have the cube execute the reverse of the last move and set faceletColors to the result
    void regressMoves() {
        try {
            if (movesIndex != -1) {
                cube.doReverseMove(solveMoves[movesIndex--]);
                faceletColors = CubeIO.writeToGraphicsFormat(cube);
            }
        } catch (NullPointerException n) {
        }

    }
    String scrambleString;

    // creates a new cube, sets scrambles the cube, and sets cube and faceletColors to the result
    void scrambleCube() {
        cube = new Cube();
        scrambleString = TextManipulator.convertScramble(cube.scramble());
        faceletColors = CubeIO.writeToGraphicsFormat(cube);
    }
}
