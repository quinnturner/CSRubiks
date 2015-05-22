package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: Testing and debugging class, mainly for use with the solver. 
 * Alot of time was spent here. Alot.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SolverClient {

    public static void main(String[] args) throws FileNotFoundException {
        Cube c = new Cube();;
//
//        boolean errorless = true;
//        while (errorless) {
//        Cube c = new Cube();
        c.startRecording();
        c.scramble();

        c.printCubelets();

        String moves = c.moveDataRecord;
        System.out.println(SolverNewBroken.compress(moves));
//        c.resetMoveDataRecord();
        c.startRecording();
        SolverNewBroken solve = new SolverNewBroken(c);
        solve.getSolve();
        System.out.println("cases done");
        System.out.println("Scramble:");
        System.out.println(SolverNewBroken.compress(moves));
        System.out.println("Raw Solve:");
        System.out.println(solve.c.moveDataRecord);
        System.out.println("Compressed Solve:");
        System.out.println(SolverNewBroken.compress(solve.c.moveDataRecord));
        System.out.println("No Turn Solve:");
        String noTurnSolve = SolverNewBroken.compress(TextManipulator.stripTurns(solve.c.moveDataRecord));
        System.out.println(noTurnSolve);
        System.out.println("Solved in " + SolverNewBroken.compress(TextManipulator.stripTurns(solve.c.moveDataRecord)).replaceAll(" ", "").replaceAll("2", "").replaceAll("'", "").length() + " moves!");
        System.out.println("Arcus:");
        TextManipulator.printForArcus(SolverNewBroken.compress(moves + solve.c.moveDataRecord));
        System.out.println("No Turns \n" + TextManipulator.fixSpacing(SolverNewBroken.compress(TextManipulator.stripTurns(moves + solve.c.moveDataRecord))));
//        solve.c.printFaces();
    }
}
