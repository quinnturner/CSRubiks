package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This is the class that generates the move list that solves the cube.
 * It first solves the top cross, does 2 y's, then solves 3 of the bottom corners, and 
 * 3 of the middle edges, and that forms an F2L case, which then leads to an OLL case, and
 * finally, a PLL case. If a cube's positions are invalid, it will typically show on the PLL cases
 */
import java.util.ArrayList;
import java.util.Arrays;

public class SolverNewBroken {

    /**
     * Fields are placed with their corresponding methods because it would be
     * extremely confusing otherwise
     */
    Cube c;
    ArrayList<Cube> storedCubes = new ArrayList();
    // index 1 = number of U's away from NSS, index 2 = position that we're looking at, element = cubelet that's supposed to be in that position
    static final int[][] topCornerTreatAs = {{0, 1, 2, 3}, {3, 0, 1, 2}, {2, 3, 0, 1}, {1, 2, 3, 0}};
    static final int[][] topEdgeTreatAs = {{8, 9, 10, 11}, {11, 8, 9, 10}, {10, 11, 8, 9}, {9, 10, 11, 8}};

    // creates the solver, clears the cube's move history, and makes sure that it's
    // recording it's move history
    SolverNewBroken(Cube c) {
        this.c = new Cube(c);
        this.c.resetMoveDataRecord();
        this.c.startRecording();
    }

    /**
     * although it is technically a TextManipulator method, compress is heavily
     * used in solver, and thus was put there. It compresses a raw move string
     * that doesn't have suffixes to one that does. This allows us to be a
     * little more generous with repeating moves, and to just compress them down
     * to nothing (i.e. 4 U's makes for nothing)
     */
    static String compress(String s) {
        s = s.replace("R R R R", "");
        s = s.replace("L L L L", "");
        s = s.replace("U U U U", "");
        s = s.replace("D D D D", "");
        s = s.replace("F F F F", "");
        s = s.replace("B B B B", "");
        s = s.replace("x x x x", "");
        s = s.replace("y y y y", "");
        s = s.replace("z z z z", "");
        s = s.replace("R R R", "R'");
        s = s.replace("L L L", "L'");
        s = s.replace("U U U", "U'");
        s = s.replace("D D D", "D'");
        s = s.replace("F F F", "F'");
        s = s.replace("B B B", "B'");
        s = s.replace("x x x", "x'");
        s = s.replace("y y y", "y'");
        s = s.replace("z z z", "z'");
        s = s.replace("R R", "R2");
        s = s.replace("L L", "L2");
        s = s.replace("U U", "U2");
        s = s.replace("D D", "D2");
        s = s.replace("F F", "F2");
        s = s.replace("B B", "B2");
        s = s.replace("x x", "x2");
        s = s.replace("y y", "y2");
        s = s.replace("z z", "z2");
        return s;
    }

    // returns the number of moves the string represents
    static int getMoveLength(String s) {
        return compress(s).split(" ").length;
    }

    // solves the cube and returns the result, stripped of any and all rotates
    String getSolve() {
        checkCross(c);
//        getBestCross();
//        solveCorners();
//        getBestCorner();
//        storedCubes.clear();
//        checkEdges(c);
//        getBestEdge();
//        doF2L(c);
//        System.out.println("f2l");
//        doOLL(c);
////        System.out.println(TextManipulator.fixSpacing(SolverNewBroken.compress(TextManipulator.stripTurns(c.moveDataRecord))));
//        System.out.println("oll");
//        doPLL(c);
//        System.out.println("pll");
////        System.out.println(TextManipulator.fixSpacing(SolverNewBroken.compress(TextManipulator.stripTurns(c.moveDataRecord))));
//        System.out.println("solved");
        return TextManipulator.fixSpacing(SolverNewBroken.compress(TextManipulator.fixSpacing(c.moveDataRecord)));
    }

    // returns the number of moves the string represents using a more robust method
    int getNumMoves() {
        return SolverNewBroken.compress(TextManipulator.stripTurns(c.moveDataRecord)).replaceAll(" ", "").replaceAll("2", "").replaceAll("'", "").length();
    }
    // solving the cross
    // index = edge's position, element = if it's in orientation 1, is it one move away from a solved state
    static final boolean[] oneStepPositions = {false, false, false, false, true, true, true, true, false, false, false, false};
    // index = edge's position, element = if the cubelet's position is on the top face
    static boolean[] inPosition = {true, true, true, true, false, false, false, false, false, false, false, false};
    // index = edge's position, element = the cubelet's level
    static int[] level = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,};
    // index = current position of the cubelet + it's orientation*8, element= the ending positions of the cubelet after it's moves have been executed
    static final int[] crossAssociations = {8, 9, 10, 11, 11, 9, 9, 11, 8, 9, 10, 11, 8, 8, 10, 10}; // the last 4 are for o1's if o1, add 8 to number

    /**
     * treatAs is a concept used all over the class. It is the idea that
     * cubelets can be in the right place in relation to eachother, and only be
     * a U away from the solve spot. This concept lets us take advantage and not
     * be forced to solve cubelets to their correct face, but merely so that
     * they're all good in relation to eachother
     */
    // this method checks how many cubelets are in position, establishes treatAs, and test them
    void checkCross(Cube testCube) {
        // checks how many hits each treatAs gets,
        int edgeZeroMoves = 0;
        int[] treatAs = {0, 0, 0, 0};
        for (int find = 0; find < 4; find++) {
            for (int pass1 = 8; pass1 < 12; pass1++) {
                if (topEdgeTreatAs[find][pass1 - 8] == testCube.getCubeletNumber(pass1) && testCube.getCubeletOrientationN(pass1) == 0) {
                    treatAs[find]++;
                }
            }
            if (treatAs[find] >= edgeZeroMoves) {
                edgeZeroMoves = treatAs[find];

            }
        }
        if (edgeZeroMoves == 4) {
            System.out.println("solved");
            System.out.println(TextManipulator.fixSpacing(SolverNewBroken.compress(TextManipulator.fixSpacing(testCube.moveDataRecord))));
            storedCubes.add(testCube);
        } else if (edgeZeroMoves == 0) {
            for (int pass1 = 8; pass1 < 12; pass1++) {
                getCrossMoves(-1, pass1, testCube);
            }
        } else {
            for (int find = 0; find < 4; find++) {
                System.out.println(Arrays.toString(treatAs));
                if (treatAs[find] == edgeZeroMoves) {
                    for (int position = 8; position < 12; position++) {
                        if (topEdgeTreatAs[find][position - 8] != testCube.getCubeletNumber(position) || testCube.getCubeletOrientationP(position) != 0) {
                            System.out.println("solving: " + find + " " + topEdgeTreatAs[find][position - 8]);
                            getCrossMoves(find, topEdgeTreatAs[find][position - 8], testCube);
                        }
                    }
                }
            }
        }
    }
    // this method will solve the targetCubelet to the assigned treatAs

    void getCrossMoves(int treatAs, int targetCubelet, Cube testCube) {
        if (level[testCube.getCubeletPosition(targetCubelet) - 8] == 0 || (level[testCube.getCubeletPosition(targetCubelet) - 8] == 2 && testCube.getCubeletOrientationN(targetCubelet) == 1)) {
            // if the cubelet is on level 0 or on level 2 in with orientation 0, then it will need to be moved out
            // there are two ways to move a cubelet out, and so both of them are tried
            // after a cubelet is moved out, then it needs to be put back in again, so getCrossMoves calls itself because it also has
            // the provisions to put a cubelet back in

            Cube testCube1 = new Cube(testCube);
            Cube testCube2 = new Cube(testCube);
            // this does a specialized move based on where the cubelet is,
            switch (testCube.getCubeletPosition(targetCubelet) > 11 ? testCube.getCubeletPosition(targetCubelet) - 8 : testCube.getCubeletPosition(targetCubelet)) {
                case 8:
                    testCube1.B();
                    getCrossMoves(treatAs, targetCubelet, testCube1);
                    testCube2.BPrime();
                    getCrossMoves(treatAs, targetCubelet, testCube2);
                    break;
                case 9:
                    testCube1.R();
                    getCrossMoves(treatAs, targetCubelet, testCube1);
                    testCube2.RPrime();
                    getCrossMoves(treatAs, targetCubelet, testCube2);
                    break;
                case 10:
                    testCube1.F();
                    getCrossMoves(treatAs, targetCubelet, testCube1);
                    testCube2.FPrime();
                    getCrossMoves(treatAs, targetCubelet, testCube2);
                    break;
                case 11:
                    testCube1.L();
                    getCrossMoves(treatAs, targetCubelet, testCube1);
                    testCube2.LPrime();
                    getCrossMoves(treatAs, targetCubelet, testCube2);
                    break;
            }
        } else {
            // crossTreatAsPossibilities[treatAs][targetCubelet - 8] is where the cubelet wants to be
            // associations[testCube.getCubeletPosition(targetCubelet)] is the position that the cubelet will end up in
            // therefore, keep turning until where the cubelet is gonna go where it wants to be
            if (treatAs == -1) {
            } else {
                while (topEdgeTreatAs[treatAs][targetCubelet - 8] != (crossAssociations[testCube.getCubeletPosition(targetCubelet) - 8 + 8 * testCube.getCubeletOrientationN(targetCubelet)])) {
                    System.out.println("u");
                    testCube.UPrime();
                    treatAs = ++treatAs > 3 ? 0 : treatAs;
                }
            }
            // keep turning the cubelet until it's in the right position
            while (level[testCube.getCubeletPosition(targetCubelet) - 8] != 0) {
                switch (crossAssociations[testCube.getCubeletPosition(targetCubelet) - 8 + (8 * testCube.getCubeletOrientationN(targetCubelet))]) {
                    case 8:
                        testCube.B();
                        checkCross(testCube);
                        break;
                    case 9:
                        testCube.R();
                        checkCross(testCube);
                        break;
                    case 10:
                        testCube.F();
                        checkCross(testCube);
                        break;
                    case 11:
                        testCube.L();
                        checkCross(testCube);
                        break;
                }
            }
        }
    }

    // this method goes through the various cube histories and chooses the best weighted one
    int getBestCross() {
        int best = -1;
        int bestMark = Integer.MAX_VALUE;
        for (int i = 0; i < storedCubes.size(); i++) {
            // a treatAs is established so we can compare corners
            int treatAs = 0;
            for (int find = 0; find < 4; find++) {
                if (topEdgeTreatAs[find][0] == storedCubes.get(i).getCubeletPosition(8)) {
                    treatAs = find;
                    break;
                }
            }
            // weighting is based on the movelength of the cross solve
            int weighting = getMoveLength(storedCubes.get(i).moveDataRecord);
            // if a corner is in position, then we give the cube a -3 bonus, if it's edge is also in position, then we give
            // the cube a further -4 bonus, and if the corner is on the bottom level and it's orientation isn't zero, it gets a -1 bonus
            int[][] TreatAsPossibilities = {{0, 1, 2, 3}, {1, 2, 3, 0}, {2, 3, 0, 1}, {3, 0, 1, 2}};
            for (int find = 0; find < 4; find++) {
                if (onBottomLevel[storedCubes.get(i).getCubeletPosition(find)] && storedCubes.get(i).getCubeletOrientationP(find) != 0) {
                    weighting -= 1;
                }
                if (TreatAsPossibilities[treatAs][find] == storedCubes.get(i).getCubeletPosition(find) && storedCubes.get(i).getCubeletOrientationN(find) == 0) {
                    weighting -= 3;
                    if (storedCubes.get(i).getCubeletOrientationN(find + 12) == 0 && storedCubes.get(i).getCubeletPosition(find + 12) == (find + 12)) {
                        weighting -= 4;
                    }
                }
            }
            if (treatAs == 0) {
                weighting -= 1;
            }
            // we then test it against the current best
            if (weighting < bestMark) {
                bestMark = weighting;
                best = i;
            }
        }
        // we merge the best choice into c
        c.makeSame(storedCubes.get(best));
        return best;
    }
    // solving corners     
    static final int[] cornerAssociations = {4, 5, 6, 7, 4, 5, 6, 7};
    static final boolean[] onBottomLevel = {false, false, false, false, true, true, true, true};

    // preps the cube for solving corners
    void solveCorners() {
        // rotates the cross so it is in line with the rest of the cube
        int treatAs = 0;
        int largest = 0;
        for (int find = 0; find < 4; find++) {
            int largestCounter = 0;
            for (int pass1 = 8; pass1 < 12; pass1++) {
                if (c.getCubeletOrientationN(pass1) == 0 && inPosition[c.getCubeletPosition(pass1) - 8] && topEdgeTreatAs[find][pass1 - 8] == c.getCubeletPosition(pass1)) {
                    largestCounter++;
                }
            }
            if (largestCounter > largest) {
                largest = largestCounter;
                treatAs = find;
            }
        }
        while (treatAs-- != 0) {
            c.U();
        }
        // flips the cube over 
        c.z();
        c.z();
        // clears the stored cross cubes
        storedCubes.clear();
        checkCorners(c);
    }

    // find how many cubelets are in position, solve all the ones that aren't in position
    void checkCorners(Cube testCube) {
        // if the cubelet isn't in a solved state, then it's solve element is set to true
        int zeroMoves = 0;
        boolean[] solve = {false, false, false, false};
        for (int pass1 = 4; pass1 < 8; pass1++) {
            if (pass1 == testCube.getCubeletPosition(pass1) && testCube.getCubeletOrientationN(pass1) == 0) {
                zeroMoves++;
            } else {
                solve[pass1 - 4] = true;
            }
        }
        // if 3 or 4 corners are solved, then continue, else solve all the cubelets that aren't solved
        switch (zeroMoves) {
            case 3:
                storedCubes.add(testCube);
                break;
            case 4:
                storedCubes.add(testCube);
                break;
            default:
                for (int find = 0; find < 4; find++) {
                    if (solve[find]) {
                        solveCorner(find + 4, new Cube(testCube));
                    }
                }
                break;
        }
    }

    void solveCorner(int targetCubelet, Cube testCube) {
        // this half is for if the cubelet is on to bottom level, and thus must be taken out and put on the top level
        if (onBottomLevel[testCube.getCubeletPosition(targetCubelet)]) {
            // there are 4 possible ways to take a cubelet out from it's wrong position
            // the solver tries all 4, unless the move results in the cubelet having an orientation 0,
            // which guarentees that the path is wrong
            Cube tempCube;
            switch (testCube.getCubeletPosition(targetCubelet)) {
                case 4:
                    tempCube = new Cube(testCube);
                    tempCube.L();
                    tempCube.U();
                    tempCube.LPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    // variation 2
                    tempCube = new Cube(testCube);
                    tempCube.L();
                    tempCube.UPrime();
                    tempCube.LPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    // variation 3
                    tempCube = new Cube(testCube);
                    tempCube.BPrime();
                    tempCube.U();
                    tempCube.B();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    // variation 4
                    tempCube = new Cube(testCube);
                    tempCube.BPrime();
                    tempCube.UPrime();
                    tempCube.B();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    return;
                case 5:
                    tempCube = new Cube(testCube);
                    tempCube.B();
                    tempCube.U();
                    tempCube.BPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.B();
                    tempCube.UPrime();
                    tempCube.BPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.RPrime();
                    tempCube.U();
                    tempCube.R();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.RPrime();
                    tempCube.UPrime();
                    tempCube.R();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    return;
                case 6:
                    tempCube = new Cube(testCube);
                    tempCube.R();
                    tempCube.U();
                    tempCube.RPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.R();
                    tempCube.UPrime();
                    tempCube.RPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.FPrime();
                    tempCube.U();
                    tempCube.F();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.FPrime();
                    tempCube.UPrime();
                    tempCube.F();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    return;
                case 7:
                    tempCube = new Cube(testCube);
                    tempCube.F();
                    tempCube.U();
                    tempCube.FPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.F();
                    tempCube.UPrime();
                    tempCube.FPrime();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.LPrime();
                    tempCube.U();
                    tempCube.L();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
                    tempCube = new Cube(testCube);
                    tempCube.LPrime();
                    tempCube.UPrime();
                    tempCube.L();
                    if (tempCube.getCubeletOrientationN(targetCubelet) != 0) {
                        solveCorner(targetCubelet, tempCube);
                    }
            }
        } else {
            // this half is for when the cubelet is on the top level, and must be put onto the bottom level.
            // if the cubelet has orientation 1 or 2, it can easily be slotted into place
            // if the cubelet has orientation 0, it's orientation must be switched
            while (cornerAssociations[testCube.getCubeletPosition(targetCubelet)] != targetCubelet) {
                testCube.U();
            }
            switch (testCube.getCubeletPosition(targetCubelet)) {
                case 0:
                    switch (testCube.getCubeletOrientationN(targetCubelet)) {
                        case 0:
                            Cube tempCube = new Cube(testCube);
                            tempCube.L();
                            tempCube.UPrime();
                            tempCube.LPrime();
                            solveCorner(targetCubelet, tempCube);
                            testCube.BPrime();
                            testCube.U();
                            testCube.B();
                            solveCorner(targetCubelet, testCube);
                            return;
                        case 1:
                            testCube.BPrime();
                            testCube.UPrime();
                            testCube.B();
                            checkCorners(testCube);
                            return;
                        case 2:
                            testCube.L();
                            testCube.U();
                            testCube.LPrime();
                            checkCorners(testCube);
                            return;
                    }
                case 1:
                    switch (testCube.getCubeletOrientationN(targetCubelet)) {
                        case 0:
                            Cube tempCube = new Cube(testCube);
                            tempCube.RPrime();
                            tempCube.U();
                            tempCube.R();
                            solveCorner(targetCubelet, tempCube);
                            testCube.B();
                            testCube.UPrime();
                            testCube.BPrime();
                            solveCorner(targetCubelet, testCube);
                            return;
                        case 1:
                            testCube.B();
                            testCube.U();
                            testCube.BPrime();
                            checkCorners(testCube);
                            return;
                        case 2:
                            testCube.RPrime();
                            testCube.UPrime();
                            testCube.R();
                            checkCorners(testCube);
                            return;
                    }
                case 2:
                    switch (testCube.getCubeletOrientationN(targetCubelet)) {
                        case 0:
                            Cube tempCube = new Cube(testCube);
                            tempCube.R();
                            tempCube.UPrime();
                            tempCube.RPrime();
                            solveCorner(targetCubelet, tempCube);
                            testCube.FPrime();
                            testCube.U();
                            testCube.F();
                            solveCorner(targetCubelet, testCube);
                            return;
                        case 1:
                            testCube.FPrime();
                            testCube.UPrime();
                            testCube.F();
                            checkCorners(testCube);
                            return;
                        case 2:
                            testCube.R();
                            testCube.U();
                            testCube.RPrime();
                            checkCorners(testCube);
                            return;
                    }
                case 3:
                    switch (testCube.getCubeletOrientationN(targetCubelet)) {
                        case 0:
                            Cube tempCube = new Cube(testCube);
                            tempCube.LPrime();
                            tempCube.U();
                            tempCube.L();
                            solveCorner(targetCubelet, tempCube);
                            testCube.F();
                            testCube.UPrime();
                            testCube.FPrime();
                            solveCorner(targetCubelet, testCube);
                            return;
                        case 1:
                            testCube.F();
                            testCube.U();
                            testCube.FPrime();
                            checkCorners(testCube);
                            return;
                        case 2:
                            testCube.LPrime();
                            testCube.UPrime();
                            testCube.L();
                            checkCorners(testCube);
                    }
            }
        }
    }

    // selects and merges the best weighted corner
    int getBestCorner() {
        int best = -1;
        int bestMark = Integer.MAX_VALUE;
        for (int i = 0; i < storedCubes.size(); i++) {
            // weighting is again based on moves, and if an edge is in position, there is a bonus of -4
            // granted to the cube
            int weighting = getMoveLength(storedCubes.get(i).moveDataRecord);
            for (int find = 4; find < 8; find++) {
                if (storedCubes.get(i).getCubeletOrientationN(find + 12) == 0 && storedCubes.get(i).getCubeletPosition(find + 12) == (find + 12)) {
                    weighting -= 4;
                }

            }
            if (weighting < bestMark) {
                bestMark = weighting;
                best = i;
            }
        }
        c.makeSame(storedCubes.get(best));
        return best;
    }

    // checks edges, uses the same structure as checkCorners
    void checkEdges(Cube testCube) {
        // if it's in position, then that gets noted, if a cubelet isn't in position,
        // it gets flagged to be solved
        int zeroMoves = 0;
        boolean[] solve = {false, false, false, false};
        for (int pass1 = 12; pass1 < 16; pass1++) {
            if (pass1 == testCube.getCubeletPosition(pass1) && testCube.getCubeletOrientationN(pass1) == 0) {
                zeroMoves++;
            } else {
                solve[pass1 - 12] = true;
            }
        }
        // once 3 ore more cubelets have been solved,we can stop
        // try solving each of the out if position cubelets
        switch (zeroMoves) {
            case 3:
                storedCubes.add(testCube);
                break;
            case 4:
                storedCubes.add(testCube);
                break;
            default:
                for (int find = 0; find < 4; find++) {
                    if (solve[find]) {
                        Cube tempCube = new Cube(testCube);
                        solveEdge(find + 12, new Cube(tempCube));
                    }
                }
                break;
        }
    }
    //first index = cubelet number - 12. second index = current position - 8, third index = orientation, element = keep turning or not
    static final boolean[][][] edgeAssociations = {{{true, false}, {false, false}, {false, false}, {false, true}}, {{true, false}, {false, true}, {false, false}, {false, false}},
        {{false, false}, {false, true}, {true, false}, {false, false}}, {{false, false}, {false, false}, {true, false}, {false, true}}};
    // first index= number of d's away from rest position, second index = cubelet's position, element = cubelet's number
    static final int[][] bottomTreatAsPossibilities = {{4, 5, 6, 7}, {5, 6, 7, 4}, {6, 7, 4, 5}, {7, 4, 5, 6}};

    void solveEdge(int targetCubelet, Cube testCube) {
        if (testCube.getCubeletPosition(targetCubelet) > 11) {
            // this half is for when the edge is on the middle layer and must be taken out
            // if one of the corners is unsolved, we rotate it to be under targetCubelet
            int targetCorner = -1;
            findTreatAs:
            for (int find = 0; find < 4; find++) {
                int treatAs = 0;
                targetCorner = -1;
                for (int pass1 = 4; pass1 < 8; pass1++) {
                    if (bottomTreatAsPossibilities[find][pass1 - 4] == testCube.cubeletPositions[pass1].number && testCube.getCubeletOrientationN(testCube.getCubeletNumber(pass1)) == 0) {
                        treatAs++;
                    } else {
                        targetCorner = testCube.cubeletPositions[pass1].number;
                    }
                }
                if (treatAs == 3) {
                    break findTreatAs;
                }
            }
            while (targetCorner != -1 && testCube.getCubeletPosition(targetCubelet) - 8 != testCube.getCubeletPosition(targetCorner)) {
                testCube.D();
            }
            // then, we again try each of the 4 possibilities for taking a cubelet out
            Cube tempCube;
            switch (testCube.getCubeletPosition(targetCubelet)) {
                case 12:
                    tempCube = new Cube(testCube);
                    tempCube.L();
                    tempCube.U();
                    tempCube.LPrime();
                    solveEdge(targetCubelet, tempCube);
                    // variation 2
                    tempCube = new Cube(testCube);
                    tempCube.L();
                    tempCube.UPrime();
                    tempCube.LPrime();
                    solveEdge(targetCubelet, tempCube);
                    // variation 3
                    tempCube = new Cube(testCube);
                    tempCube.BPrime();
                    tempCube.U();
                    tempCube.B();
                    solveEdge(targetCubelet, tempCube);
                    // variation 4
                    tempCube = new Cube(testCube);
                    tempCube.BPrime();
                    tempCube.UPrime();
                    tempCube.B();
                    solveEdge(targetCubelet, tempCube);
                    return;
                case 13:
                    tempCube = new Cube(testCube);
                    tempCube.B();
                    tempCube.U();
                    tempCube.BPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.B();
                    tempCube.UPrime();
                    tempCube.BPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.RPrime();
                    tempCube.U();
                    tempCube.R();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.RPrime();
                    tempCube.UPrime();
                    tempCube.R();
                    solveEdge(targetCubelet, tempCube);
                    return;
                case 14:
                    tempCube = new Cube(testCube);
                    tempCube.R();
                    tempCube.U();
                    tempCube.RPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.R();
                    tempCube.UPrime();
                    tempCube.RPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.FPrime();
                    tempCube.U();
                    tempCube.F();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.FPrime();
                    tempCube.UPrime();
                    tempCube.F();
                    solveEdge(targetCubelet, tempCube);
                    return;
                case 15:
                    tempCube = new Cube(testCube);
                    tempCube.F();
                    tempCube.U();
                    tempCube.FPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.F();
                    tempCube.UPrime();
                    tempCube.FPrime();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.LPrime();
                    tempCube.U();
                    tempCube.L();
                    solveEdge(targetCubelet, tempCube);
                    tempCube = new Cube(testCube);
                    tempCube.LPrime();
                    tempCube.UPrime();
                    tempCube.L();
                    solveEdge(targetCubelet, tempCube);
                    return;
            }
        } else {
            // if the cubelet is in the top layer, it is ready to be slotted in
            // first, we rotate the top layer until the edge is sitting in it's associated spot
            while (!edgeAssociations[targetCubelet - 12][testCube.getCubeletPosition(targetCubelet) - 8][testCube.getCubeletOrientationN(targetCubelet)]) {
                testCube.U();
            }
            // then, we find treatAs for the corners, and rotate the unsolved corner to be under the edge
            int targetCorner = -1;
            findTreatAs:
            for (int find = 0; find < 4; find++) {
                int treatAs = 0;
                targetCorner = -1;
                for (int pass1 = 4; pass1 < 8; pass1++) {
                    if (bottomTreatAsPossibilities[find][pass1 - 4] == testCube.cubeletPositions[pass1].number && testCube.getCubeletOrientationN(testCube.getCubeletNumber(pass1)) == 0) {
                        treatAs++;
                    } else {
                        targetCorner = testCube.cubeletPositions[pass1].number;
                    }
                }
                if (treatAs == 3) {
                    break findTreatAs;
                }
            }
            while (targetCorner != -1 && targetCubelet - 8 != testCube.getCubeletPosition(targetCorner)) {
                testCube.D();
            }
            // then, depending on the cubelet and it's orientation, then we do a certain case to put the edge in
            switch (targetCubelet) {
                case 12:
                    if (testCube.getCubeletOrientationN(targetCubelet) == 0) {
                        testCube.L();
                        testCube.UPrime();
                        testCube.LPrime();
                    } else {
                        testCube.BPrime();
                        testCube.U();
                        testCube.B();
                    }
                    checkEdges(testCube);
                    return;
                case 13:
                    if (testCube.getCubeletOrientationN(targetCubelet) == 0) {
                        testCube.RPrime();
                        testCube.U();
                        testCube.R();
                    } else {
                        testCube.B();
                        testCube.UPrime();
                        testCube.BPrime();
                    }
                    checkEdges(testCube);
                    return;
                case 14:
                    if (testCube.getCubeletOrientationN(targetCubelet) == 0) {
                        testCube.R();
                        testCube.UPrime();
                        testCube.RPrime();
                    } else {
                        testCube.FPrime();
                        testCube.U();
                        testCube.F();
                    }
                    checkEdges(testCube);
                    return;
                case 15:
                    if (testCube.getCubeletOrientationN(targetCubelet) == 0) {
                        testCube.LPrime();
                        testCube.U();
                        testCube.L();
                    } else {
                        testCube.F();
                        testCube.UPrime();
                        testCube.FPrime();
                    }
                    checkEdges(testCube);
                    return;
            }
        }
    }

    // selects the best edge and merges it into c
    int getBestEdge() {
        int best = -1;
        int bestMark = Integer.MAX_VALUE;
        for (int i = 0; i < storedCubes.size(); i++) {
            // because we have no idea which f2l, oll, and pll case it's gonna be, it's 
            // pretty much impossible to say that one cube is more solved than another
            // therefore, we go by move length alone
            int weighting = getMoveLength(storedCubes.get(i).moveDataRecord);
            if (weighting < bestMark) {
                bestMark = weighting;
                best = i;
            }
        }
        c.makeSame(storedCubes.get(best));
        return best;
    }

    // solving F2L (First2Layers) since there's only one, we directly do the case on c
    void doF2L(Cube testCube) {
        // we rotate the cube until the unsolved edge is in position 14
        for (int turn = 0; turn < 4; turn++) {
            if (testCube.getCubeletPosition(14) == 14 && testCube.getCubeletOrientationN(14) == 0) {
                testCube.y();
            }
        }
        // then, we rotate the unsolved corner into position 6
        int targetCorner = 0;
        findTreatAs:
        for (int find = 0; find < 4; find++) {
            int treatAs = 0;
            targetCorner = 6;
            for (int pass1 = 4; pass1 < 8; pass1++) {
                if (bottomTreatAsPossibilities[find][pass1 - 4] == testCube.cubeletPositions[pass1].number && testCube.getCubeletOrientationN(testCube.getCubeletNumber(pass1)) == 0) {
                    treatAs++;
                } else {
                    targetCorner = testCube.getCubeletNumber(pass1);
                }
            }
            if (treatAs == 3) {
                break findTreatAs;
            }
        }
        // if all the corners are solved, then we rotate cubelet 6 into position 6, since we will eventually have to do this anyways
        while (testCube.getCubeletPosition(targetCorner) != 6) {
            testCube.D();
        }
        // next, we pick a case that solves the first two layers of the cube.
        // we first choose based on the position of the unsolved (or solved) corner (this cuts out either 12 or 30 cases)
        // then, we pick based on the corner's orientation (this cuts the remaning cases down by 2/3rds
        // then, rotate the edge and we pick based on the edge's position (this narrows it down to either 5 or 3
        // finally, we pick based on the edge's orientation (this determines the case)
        if (targetCorner < 4) {
            targetCorner = testCube.getCubeletNumber(5) == 7 ? 4 : testCube.getCubeletNumber(5) + 1;
            while (testCube.getCubeletPosition(targetCorner) != 2) {
                testCube.U();
            }
            switch (testCube.getCornerFacePositionN(targetCorner)) {
                case 0:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 32
                                testCube.RSquared();
                                testCube.U();
                                testCube.RSquared();
                                testCube.U();
                                testCube.RSquared();
                                testCube.USquared();
                                testCube.RSquared();
                            } else { // case 33
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.FPrime();
                                testCube.USquared();
                                testCube.F();
                            }
                            break;
                        case 8:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 38
                                testCube.U();
                                testCube.R();
                                testCube.USquared();
                                testCube.RSquared();
                                testCube.F();
                                testCube.R();
                                testCube.FPrime();
                            } else { // case 39
                                testCube.FPrime();
                                testCube.LPrime();
                                testCube.USquared();
                                testCube.L();
                                testCube.F();
                            }
                            break;
                        case 9:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 40
                                testCube.R();
                                testCube.USquared();
                                testCube.RPrime();
                                testCube.UPrime();
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                            } else { // case 41
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.FPrime();
                                testCube.L();
                                testCube.F();
                                testCube.LPrime();
                                testCube.USquared();
                                testCube.F();
                            }
                            break;
                        case 10:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 35
                                testCube.USquared();
                                testCube.RSquared();
                                testCube.USquared();
                                testCube.RPrime();
                                testCube.UPrime();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RSquared();
                            } else { // case 34
                                testCube.FPrime();
                                testCube.USquared();
                                testCube.F();
                                testCube.U();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 11:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 37
                                testCube.R();
                                testCube.B();
                                testCube.USquared();
                                testCube.BPrime();
                                testCube.RPrime();
                            } else { // case 36
                                testCube.UPrime();
                                testCube.FPrime();
                                testCube.USquared();
                                testCube.FSquared();
                                testCube.RPrime();
                                testCube.FPrime();
                                testCube.R();
                            }
                            break;
                    }
                    break;
                case 1:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 22
                                testCube.BPrime();
                                testCube.D();
                                testCube.B();
                                testCube.UPrime();
                                testCube.BPrime();
                                testCube.DPrime();
                                testCube.B();
                            } else { // case 23
                                testCube.UPrime();
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                                testCube.U();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 8:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 28
                                testCube.FSquared();
                                testCube.LPrime();
                                testCube.UPrime();
                                testCube.L();
                                testCube.U();
                                testCube.FSquared();
                            } else { // case 29
                                testCube.RSquared();
                                testCube.U();
                                testCube.B();
                                testCube.UPrime();
                                testCube.BPrime();
                                testCube.RSquared();
                            }
                            break;
                        case 9:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 30
                                testCube.U();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                            } else { // case 31
                                testCube.F();
                                testCube.USquared();
                                testCube.FSquared();
                                testCube.UPrime();
                                testCube.FSquared();
                                testCube.UPrime();
                                testCube.FPrime();
                            }
                            break;
                        case 10:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 25
                                testCube.FPrime();
                                testCube.U();
                                testCube.F();
                                testCube.USquared();
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                            } else { // case 24
                                testCube.U();
                                testCube.FPrime();
                                testCube.U();
                                testCube.F();
                                testCube.UPrime();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 11:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 27
                                testCube.FSquared();
                                testCube.USquared();
                                testCube.RPrime();
                                testCube.FSquared();
                                testCube.R();
                                testCube.USquared();
                                testCube.FSquared();
                            } else { // case 26
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                    }
                    break;
                case 2:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) {// case 12
                                testCube.U();
                                testCube.R();
                                testCube.USquared();
                                testCube.B();
                                testCube.USquared();
                                testCube.BPrime();
                                testCube.RPrime();
                            } else { // case 13
                                testCube.U();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                                testCube.UPrime();
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                            }
                            break;
                        case 8:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 18
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                            } else { // case 19
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.LPrime();
                                testCube.USquared();
                                testCube.L();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 9:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 20
                                testCube.UPrime();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.U();
                                testCube.R();
                                testCube.U();
                                testCube.RPrime();
                            } else { // case 21
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.USquared();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 10:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 15
                                testCube.RPrime();
                                testCube.USquared();
                                testCube.RSquared();
                                testCube.U();
                                testCube.RSquared();
                                testCube.U();
                                testCube.R();
                            } else { // case 14
                                testCube.F();
                                testCube.RPrime();
                                testCube.FPrime();
                                testCube.R();
                            }
                            break;
                        case 11:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 17
                                testCube.FSquared();
                                testCube.UPrime();
                                testCube.LPrime();
                                testCube.U();
                                testCube.L();
                                testCube.FSquared();
                            } else { // case 16
                                testCube.RSquared();
                                testCube.B();
                                testCube.U();
                                testCube.BPrime();
                                testCube.UPrime();
                                testCube.RSquared();
                            }
                            break;
                    }
                    break;
            }
        } else {
            if (testCube.getCubeletPosition(14) != 14) {
                if (testCube.getCubeletOrientationN(14) == 0) {
                    while (testCube.getCubeletPosition(14) != 9) {
                        testCube.U();
                    }
                } else {
                    while (testCube.getCubeletPosition(14) != 10) {
                        testCube.U();
                    }
                }
            }
            switch (testCube.getCornerFacePositionN(targetCorner)) {
                case 0:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) { // solved
                            } else { // case 1
                                testCube.FPrime();
                                testCube.U();
                                testCube.FPrime();
                                testCube.USquared();
                                testCube.RPrime();
                                testCube.FSquared();
                                testCube.R();
                                testCube.USquared();
                                testCube.FSquared();
                            }
                            break;
                        case 9: // case 3
                            testCube.RPrime();
                            testCube.USquared();
                            testCube.BPrime();
                            testCube.RPrime();
                            testCube.B();
                            testCube.USquared();
                            testCube.R();
                            break;
                        case 10: // case 2
                            testCube.RPrime();
                            testCube.USquared();
                            testCube.BPrime();
                            testCube.R();
                            testCube.B();
                            testCube.USquared();
                            testCube.R();
                            break;
                    }
                    break;
                case 2:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 8
                                testCube.FSquared();
                                testCube.USquared();
                                testCube.F();
                                testCube.U();
                                testCube.FPrime();
                                testCube.U();
                                testCube.F();
                                testCube.USquared();
                                testCube.F();
                            } else { // case 9
                                testCube.FPrime();
                                testCube.U();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.LPrime();
                                testCube.U();
                                testCube.L();
                                testCube.FSquared();
                            }
                            break;
                        case 9: // case 11 (modified)
                            testCube.USquared();
                            testCube.FPrime();
                            testCube.U();
                            testCube.F();
                            testCube.R();
                            testCube.U();
                            testCube.RPrime();
                            break;
                        case 10: // case 10
                            testCube.FPrime();
                            testCube.U();
                            testCube.FSquared();
                            testCube.RPrime();
                            testCube.FPrime();
                            testCube.R();
                            break;
                    }
                    break;
                case 1:
                    switch (testCube.getCubeletPosition(14)) {
                        case 14:
                            if (testCube.getCubeletOrientationN(14) == 0) { // case 4
                                testCube.RSquared();
                                testCube.USquared();
                                testCube.RPrime();
                                testCube.UPrime();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.USquared();
                                testCube.RPrime();
                            } else { // case 5 
                                testCube.FSquared();
                                testCube.LPrime();
                                testCube.UPrime();
                                testCube.L();
                                testCube.U();
                                testCube.F();
                                testCube.UPrime();
                                testCube.F();
                            }
                            break;
                        case 9: // case 6
                            testCube.R();
                            testCube.UPrime();
                            testCube.RSquared();
                            testCube.F();
                            testCube.R();
                            testCube.FPrime();
                            break;
                        case 10: // case 7 (modified)
                            testCube.USquared();
                            testCube.R();
                            testCube.UPrime();
                            testCube.RPrime();
                            testCube.FPrime();
                            testCube.UPrime();
                            testCube.F();
                            break;
                    }
                    break;
            }
        }
        while (testCube.getCubeletPosition(6) != 6) {
            testCube.D();
        }
    }

    // solving OLL (OrientLastLayer) since there's only one, we directly do the case on c
    void doOLL(Cube testCube) {
        // we first seperate the cubelets based on how many edges and corners are facing up
        int edgesFacingUp = 0;
        int cornersFacingUp = 0;
        for (int pass1 = 0; pass1 < 4; pass1++) {
            if (testCube.getCubeletOrientationN(pass1 + 8) == 0) {
                edgesFacingUp++;
            }
            if (testCube.getCubeletOrientationN(pass1) == 0) {
                cornersFacingUp++;
            }
        }
        /* The biggest challenge here is rotations of the same case. We try to exclusively use Y rotations instead of U's,
         * because they are significantly easier to strip out of a move string. 
         * If 0 edges are facing up, then the cube can't be locked based on edges, and must be locked by corners
         * If 2 edges are facing up, there are 2 patters: an I, which we rotate to be a horizontal line, or an L shape,
         * which we rotate counter-clockwise 90 degrees. We lock I to one of 2 possible orientations, and then start checking
         * corners. The easiest to lock (but most cases to check for) is L's since there's only 1 way to lock them.
         * Finally, we treat 4 edges facing up exactly the same way we treat 0 edges, since they have the same corner patterns.
         */
        switch (edgesFacingUp) {
            case 0:
                switch (cornersFacingUp) {
                    case 0:
                        while (testCube.getCornerFacePositionP(1) != 2 || testCube.getCornerFacePositionP(2) != 2) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(3) == 2) { // case 1
                            testCube.R();
                            testCube.USquared();
                            testCube.RSquared();
                            testCube.F();
                            testCube.R();
                            testCube.FPrime();
                            testCube.USquared();
                            testCube.RPrime();
                            testCube.F();
                            testCube.R();
                            testCube.FPrime();
                        } else { // case 2
                            testCube.F();
                            testCube.RPrime();
                            testCube.FPrime();
                            testCube.R();
                            testCube.U();
                            testCube.RSquared();
                            testCube.BPrime();
                            testCube.RPrime();
                            testCube.B();
                            testCube.UPrime();
                            testCube.RPrime();
                        }
                        break;
                    case 1:
                        while (testCube.getCornerFacePositionP(3) != 0) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(0) == 1) { // case 3
                            testCube.LPrime();
                            testCube.RSquared();
                            testCube.B();
                            testCube.RPrime();
                            testCube.B();
                            testCube.L();
                            testCube.USquared();
                            testCube.LPrime();
                            testCube.B();
                            testCube.RPrime();
                            testCube.L();
                        } else { // case 4
                            testCube.BPrime();
                            testCube.F();
                            testCube.RPrime();
                            testCube.B();
                            testCube.USquared();
                            testCube.BPrime();
                            testCube.RPrime();
                            testCube.F();
                            testCube.RPrime();
                            testCube.FSquared();
                            testCube.B();
                        }
                        break;
                    case 2:
                        while (testCube.getCornerFacePositionP(3) != 0 || testCube.getCornerFacePositionP(0) == 0) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(0) == 1 && testCube.getCornerFacePositionP(2) == 0) { // case 18
                            testCube.F();
                            testCube.R();
                            testCube.U();
                            testCube.RPrime();
                            testCube.U();
                            testCube.FPrime();
                            testCube.USquared();
                            testCube.FPrime();
                            testCube.L();
                            testCube.F();
                            testCube.LPrime();
                        } else if (testCube.getCornerFacePositionP(0) == 2 && testCube.getCornerFacePositionP(2) == 0) { // case 19
                            testCube.RPrime();
                            testCube.L();
                            testCube.FPrime();
                            testCube.RPrime();
                            testCube.FPrime();
                            testCube.R();
                            testCube.F();
                            testCube.LPrime();
                            testCube.RSquared();
                            testCube.BPrime();
                            testCube.RPrime();
                            testCube.B();
                        } else { // case 17
                            if (testCube.getCornerFacePositionP(0) == 2) {
                                testCube.y();
                                testCube.y();
                            }
                            testCube.LPrime();
                            testCube.F();
                            testCube.USquared();
                            testCube.BPrime();
                            testCube.R();
                            testCube.USquared();
                            testCube.RPrime();
                            testCube.B();
                            testCube.USquared();
                            testCube.FPrime();
                            testCube.L();
                        }
                        break;
                    case 4: // case 20
                        testCube.R();
                        testCube.B();
                        testCube.U();
                        testCube.BPrime();
                        testCube.RPrime();
                        testCube.FSquared();
                        testCube.B();
                        testCube.DPrime();
                        testCube.LPrime();
                        testCube.D();
                        testCube.BPrime();
                        testCube.FSquared();
                        break;
                }
                break;
            case 2:
                while (testCube.getCubeletOrientationP(11) != 0 || testCube.getCubeletOrientationP(10) == 0) {
                    testCube.y();
                }
                if (testCube.getCubeletOrientationP(9) == 0) { // I/Line case
                    switch (cornersFacingUp) {
                        case 0:
                            if (testCube.getCornerFacePositionP(0) != testCube.getCornerFacePositionP(1)) { // case 51
                                if (testCube.getCornerFacePositionP(0) != 1) {
                                    testCube.y();
                                    testCube.y();
                                }
                                testCube.F();
                                testCube.U();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.U();
                                testCube.R();
                                testCube.UPrime();
                                testCube.RPrime();
                                testCube.FPrime();
                            } else if (testCube.getCornerFacePositionP(0) != testCube.getCornerFacePositionP(3)) { // case 52
                                if (testCube.getCornerFacePositionP(0) != 2) {
                                    testCube.y();
                                    testCube.y();
                                }
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.F();
                                testCube.UPrime();
                                testCube.FPrime();
                                testCube.U();
                                testCube.LPrime();
                                testCube.U();
                                testCube.L();
                                testCube.F();
                            } else {
                                if (testCube.getCornerFacePositionP(0) == 1) { // case 55                                    
                                    testCube.F();
                                    testCube.UPrime();
                                    testCube.RSquared();
                                    testCube.D();
                                    testCube.RPrime();
                                    testCube.USquared();
                                    testCube.R();
                                    testCube.DPrime();
                                    testCube.RSquared();
                                    testCube.U();
                                    testCube.FPrime();
                                } else { // case 56
                                    testCube.F();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.FPrime();
                                    testCube.L();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.LPrime();
                                }
                            }
                            break;
                        case 1:
                            while (testCube.getCornerFacePositionP(0) == 0 || testCube.getCornerFacePositionP(1) == 0) {
                                testCube.y();
                                testCube.y();
                            }
                            switch (testCube.getCornerFacePositionP(3)) {
                                case 0:
                                    if (testCube.getCornerFacePositionP(2) == 1) { // case 13
                                        testCube.F();
                                        testCube.U();
                                        testCube.R();
                                        testCube.USquared();
                                        testCube.RPrime();
                                        testCube.UPrime();
                                        testCube.R();
                                        testCube.U();
                                        testCube.RPrime();
                                        testCube.FPrime();
                                    } else { // case 16
                                        testCube.R();
                                        testCube.B();
                                        testCube.RPrime();
                                        testCube.L();
                                        testCube.U();
                                        testCube.LPrime();
                                        testCube.UPrime();
                                        testCube.R();
                                        testCube.BPrime();
                                        testCube.RPrime();
                                    }
                                    break;
                                case 1: // case 14
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                    testCube.F();
                                    testCube.UPrime();
                                    testCube.FPrime();
                                    break;
                                case 2: // case 15
                                    testCube.LPrime();
                                    testCube.BPrime();
                                    testCube.L();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.U();
                                    testCube.LPrime();
                                    testCube.B();
                                    testCube.L();
                                    break;
                            }
                            break;
                        case 2:
                            if (testCube.getCornerFacePositionP(0) == testCube.getCornerFacePositionP(1)) {
                                if (testCube.getCornerFacePositionP(0) == 0) {
                                    testCube.y();
                                    testCube.y();
                                }
                                if (testCube.getCornerFacePositionP(0) == 1) { // case 46
                                    testCube.BPrime();
                                    testCube.UPrime();
                                    testCube.BPrime();
                                    testCube.R();
                                    testCube.B();
                                    testCube.RPrime();
                                    testCube.U();
                                    testCube.B();
                                } else { // case 34
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.BPrime();
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.R();
                                    testCube.FPrime();
                                    testCube.B();
                                }
                            } else if (testCube.getCornerFacePositionP(0) == testCube.getCornerFacePositionP(3)) {
                                if (testCube.getCornerFacePositionP(0) == 1) {
                                    testCube.y();
                                    testCube.y();
                                }
                                if (testCube.getCornerFacePositionP(1) == 2) {
                                    testCube.y();
                                    testCube.y();
                                }
                                if (testCube.getCornerFacePositionP(0) == 0) { // case 33
                                    testCube.L();
                                    testCube.U();
                                    testCube.LPrime();
                                    testCube.UPrime();
                                    testCube.LPrime();
                                    testCube.B();
                                    testCube.L();
                                    testCube.BPrime();
                                } else { // case 45
                                    testCube.F();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.FPrime();
                                }
                            } else {
                                if (testCube.getCornerFacePositionP(0) == 0) {
                                    if (testCube.getCornerFacePositionP(1) != 1) {
                                        testCube.y();
                                        testCube.y();
                                    }// case 40
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.FPrime();
                                    testCube.U();
                                    testCube.R();
                                } else {
                                    if (testCube.getCornerFacePositionP(0) != 1) {
                                        testCube.y();
                                        testCube.y();
                                    }// case 39
                                    testCube.L();
                                    testCube.FPrime();
                                    testCube.LPrime();
                                    testCube.UPrime();
                                    testCube.L();
                                    testCube.U();
                                    testCube.F();
                                    testCube.UPrime();
                                    testCube.LPrime();
                                }
                            }
                            break;
                        case 4: // case 57
                            testCube.RPrime();
                            testCube.UPrime();
                            testCube.R();
                            testCube.U();
                            testCube.R();
                            testCube.LPrime();
                            testCube.BPrime();
                            testCube.RPrime();
                            testCube.B();
                            testCube.L();
                            break;
                    }
                } else { // L case
                    switch (cornersFacingUp) {
                        case 0:
                            if (testCube.getCornerFacePositionP(0) == 1) {
                                if (testCube.getCornerFacePositionP(1) == 1) {
                                    if (testCube.getCornerFacePositionP(2) == 1) { // case 54
                                        testCube.L();
                                        testCube.FSquared();
                                        testCube.RPrime();
                                        testCube.FPrime();
                                        testCube.R();
                                        testCube.F();
                                        testCube.RPrime();
                                        testCube.FPrime();
                                        testCube.R();
                                        testCube.FPrime();
                                        testCube.LPrime();
                                    } else { // case 47
                                        testCube.RPrime();
                                        testCube.FPrime();
                                        testCube.UPrime();
                                        testCube.F();
                                        testCube.U();
                                        testCube.FPrime();
                                        testCube.UPrime();
                                        testCube.F();
                                        testCube.U();
                                        testCube.R();
                                    }
                                } else {// case 50
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.RSquared();
                                    testCube.BPrime();
                                    testCube.RSquared();
                                    testCube.FPrime();
                                    testCube.RSquared();
                                    testCube.B();
                                    testCube.RPrime();
                                }
                            } else {
                                if (testCube.getCornerFacePositionP(1) == 1) { // case 48
                                    testCube.F();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.U();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.FPrime();
                                } else {
                                    if (testCube.getCornerFacePositionP(2) == 1) { // case 49
                                        testCube.F();
                                        testCube.RPrime();
                                        testCube.FSquared();
                                        testCube.L();
                                        testCube.FSquared();
                                        testCube.R();
                                        testCube.FSquared();
                                        testCube.LPrime();
                                        testCube.F();
                                    } else { // case 53
                                        testCube.RPrime();
                                        testCube.FPrime();
                                        testCube.L();
                                        testCube.FPrime();
                                        testCube.LPrime();
                                        testCube.F();
                                        testCube.L();
                                        testCube.FPrime();
                                        testCube.LPrime();
                                        testCube.FSquared();
                                        testCube.R();
                                    }
                                }
                            }
                            break;
                        case 1:
                            if (testCube.getCornerFacePositionP(0) == 0) {
                                if (testCube.getCornerFacePositionP(1) == 1) { // case 6
                                    testCube.F();
                                    testCube.RSquared();
                                    testCube.BPrime();
                                    testCube.RPrime();
                                    testCube.B();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                } else { // case 5
                                    testCube.RPrime();
                                    testCube.FSquared();
                                    testCube.L();
                                    testCube.F();
                                    testCube.LPrime();
                                    testCube.F();
                                    testCube.R();
                                }
                            } else if (testCube.getCornerFacePositionP(1) == 0) {
                                if (testCube.getCornerFacePositionP(2) == 1) { // case 11
                                    testCube.L();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.D();
                                    testCube.R();
                                    testCube.DPrime();
                                    testCube.R();
                                    testCube.FSquared();
                                    testCube.LPrime();
                                } else { // case 8
                                    testCube.BPrime();
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.RSquared();
                                    testCube.B();
                                }
                            } else if (testCube.getCornerFacePositionP(2) == 0) {
                                if (testCube.getCornerFacePositionP(3) == 1) { // case 9
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.U();
                                    testCube.F();
                                    testCube.R();
                                    testCube.FPrime();
                                } else { // case 10
                                    testCube.F();
                                    testCube.U();
                                    testCube.FPrime();
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.R();
                                    testCube.UPrime();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                }
                            } else if (testCube.getCornerFacePositionP(3) == 0) {
                                if (testCube.getCornerFacePositionP(0) == 1) { // case 7
                                    testCube.L();
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.F();
                                    testCube.R();
                                    testCube.FSquared();
                                    testCube.LPrime();
                                } else { // case 12
                                    testCube.L();
                                    testCube.F();
                                    testCube.U();
                                    testCube.FSquared();
                                    testCube.L();
                                    testCube.F();
                                    testCube.LSquared();
                                    testCube.U();
                                    testCube.L();
                                    testCube.USquared();
                                    testCube.LPrime();
                                }
                            }
                            break;
                        case 2:
                            if (testCube.getCornerFacePositionP(0) == 0 && testCube.getCornerFacePositionP(1) == 0) {
                                if (testCube.getCornerFacePositionP(2) == 1) { // case 43
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.FPrime();
                                    testCube.U();
                                    testCube.F();
                                    testCube.R();
                                } else { // case 31
                                    testCube.BPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.U();
                                    testCube.B();
                                    testCube.UPrime();
                                    testCube.BPrime();
                                    testCube.RPrime();
                                    testCube.B();
                                }
                            } else if (testCube.getCornerFacePositionP(1) == 0 && testCube.getCornerFacePositionP(2) == 0) {
                                if (testCube.getCornerFacePositionP(3) == 1) { // case 29
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                    testCube.BPrime();
                                    testCube.RPrime();
                                    testCube.FSquared();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.RSquared();
                                    testCube.B();
                                } else { // case 42
                                    testCube.BPrime();
                                    testCube.FSquared();
                                    testCube.D();
                                    testCube.R();
                                    testCube.DPrime();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                    testCube.FPrime();
                                    testCube.B();
                                }
                            } else if (testCube.getCornerFacePositionP(2) == 0 && testCube.getCornerFacePositionP(3) == 0) {
                                if (testCube.getCornerFacePositionP(0) == 1) { // case 41
                                    testCube.L();
                                    testCube.RSquared();
                                    testCube.DPrime();
                                    testCube.FPrime();
                                    testCube.D();
                                    testCube.F();
                                    testCube.R();
                                    testCube.FPrime();
                                    testCube.LPrime();
                                    testCube.R();
                                } else { // case 30
                                    testCube.F();
                                    testCube.U();
                                    testCube.R();
                                    testCube.UPrime();
                                    testCube.B();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                    testCube.BPrime();
                                    testCube.RPrime();
                                }
                            } else if (testCube.getCornerFacePositionP(3) == 0 && testCube.getCornerFacePositionP(0) == 0) {
                                if (testCube.getCornerFacePositionP(1) == 1) { // case 32
                                    testCube.L();
                                    testCube.U();
                                    testCube.FPrime();
                                    testCube.UPrime();
                                    testCube.LPrime();
                                    testCube.U();
                                    testCube.L();
                                    testCube.F();
                                    testCube.LPrime();
                                } else { // case 44
                                    testCube.F();
                                    testCube.U();
                                    testCube.R();
                                    testCube.UPrime();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                }
                            } else if (testCube.getCornerFacePositionP(0) == 0 && testCube.getCornerFacePositionP(2) == 0) {
                                if (testCube.getCornerFacePositionP(1) == 1) { // case 35
                                    testCube.L();
                                    testCube.USquared();
                                    testCube.LSquared();
                                    testCube.B();
                                    testCube.L();
                                    testCube.BPrime();
                                    testCube.L();
                                    testCube.USquared();
                                    testCube.LPrime();
                                } else { // case 37
                                    testCube.F();
                                    testCube.RPrime();
                                    testCube.FPrime();
                                    testCube.R();
                                    testCube.U();
                                    testCube.R();
                                    testCube.UPrime();
                                    testCube.RPrime();
                                }
                            } else if (testCube.getCornerFacePositionP(1) == 0 && testCube.getCornerFacePositionP(3) == 0) {
                                if (testCube.getCornerFacePositionP(0) == 1) { // case 38
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                    testCube.U();
                                    testCube.B();
                                    testCube.USquared();
                                    testCube.BPrime();
                                    testCube.UPrime();
                                    testCube.RPrime();
                                    testCube.UPrime();
                                    testCube.R();
                                } else { // case 36
                                    testCube.F();
                                    testCube.U();
                                    testCube.FPrime();
                                    testCube.UPrime();
                                    testCube.LPrime();
                                    testCube.USquared();
                                    testCube.L();
                                    testCube.U();
                                    testCube.F();
                                    testCube.U();
                                    testCube.FPrime();
                                }
                            }
                            break;
                        case 4: // case 28
                            testCube.L();
                            testCube.F();
                            testCube.RPrime();
                            testCube.FPrime();
                            testCube.LPrime();
                            testCube.R();
                            testCube.U();
                            testCube.R();
                            testCube.UPrime();
                            testCube.RPrime();
                            break;
                    }
                }
                break;
            case 4:
                switch (cornersFacingUp) {
                    case 0:
                        while (testCube.getCornerFacePositionP(0) != 2 || testCube.getCornerFacePositionP(3) != 2) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(1) == 2) { // case 21
                            testCube.R();
                            testCube.U();
                            testCube.RPrime();
                            testCube.U();
                            testCube.R();
                            testCube.UPrime();
                            testCube.RPrime();
                            testCube.U();
                            testCube.R();
                            testCube.USquared();
                            testCube.RPrime();
                        } else { // case 22
                            testCube.R();
                            testCube.USquared();
                            testCube.RSquared();
                            testCube.UPrime();
                            testCube.RSquared();
                            testCube.UPrime();
                            testCube.RSquared();
                            testCube.USquared();
                            testCube.R();
                        }
                        break;
                    case 1:
                        while (testCube.getCornerFacePositionP(3) != 0) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(0) == 1) { // case 27
                            testCube.R();
                            testCube.U();
                            testCube.RPrime();
                            testCube.U();
                            testCube.R();
                            testCube.USquared();
                            testCube.RPrime();
                        } else { // case 26
                            testCube.L();
                            testCube.USquared();
                            testCube.LPrime();
                            testCube.UPrime();
                            testCube.L();
                            testCube.UPrime();
                            testCube.LPrime();
                        }
                        break;
                    case 2:
                        while (testCube.getCornerFacePositionP(3) != 0 || testCube.getCornerFacePositionP(0) == 0) {
                            testCube.y();
                        }
                        if (testCube.getCornerFacePositionP(0) == 1 && testCube.getCornerFacePositionP(2) == 0) { // case 23
                            testCube.RSquared();
                            testCube.DPrime();
                            testCube.R();
                            testCube.USquared();
                            testCube.RPrime();
                            testCube.D();
                            testCube.R();
                            testCube.USquared();
                            testCube.R();
                        } else if (testCube.getCornerFacePositionP(0) == 2 && testCube.getCornerFacePositionP(2) == 0) { // case 24
                            testCube.B();
                            testCube.L();
                            testCube.FPrime();
                            testCube.LPrime();
                            testCube.BPrime();
                            testCube.L();
                            testCube.F();
                            testCube.LPrime();
                        } else { // case 25
                            if (testCube.getCornerFacePositionP(0) == 2) {
                                testCube.y();
                                testCube.y();
                            }
                            testCube.FPrime();
                            testCube.LPrime();
                            testCube.BPrime();
                            testCube.L();
                            testCube.F();
                            testCube.LPrime();
                            testCube.B();
                            testCube.L();
                        }
                        break;
                    case 4: // solved
                        break;
                }
                break;
        }

    }

    // solving PLL (PositionLastLayer) since there's only one, we directly do the case on c
    void doPLL(Cube testCube) {
        // first, we establish a treatAs to figure out how many corners and edges are in position
        int cornerZeroMoves = 1;
        int edgeZeroMoves = 1;
        int treatAs = 0;
        for (int find = 0; find < 4; find++) {
            int tempCornerZeroMoves = 0;
            int tempEdgeZeroMoves = 0;
            for (int pass1 = 0; pass1 < 4; pass1++) {
                if (topCornerTreatAs[find][pass1] == testCube.getCubeletNumber(pass1)) {
                    tempCornerZeroMoves++;
                }
            }
            for (int pass1 = 8; pass1 < 12; pass1++) {
                if (topEdgeTreatAs[find][pass1 - 8] == testCube.getCubeletNumber(pass1)) {
                    tempEdgeZeroMoves++;
                }
            }
            // if either are 4, then they are severely limited in cases, so we break and solve immediately 
            if (tempCornerZeroMoves == 4 || tempEdgeZeroMoves == 4) {
                cornerZeroMoves = tempCornerZeroMoves;
                edgeZeroMoves = tempEdgeZeroMoves;
                treatAs = find;
                break;
            }
            if (tempCornerZeroMoves >= cornerZeroMoves && tempEdgeZeroMoves >= edgeZeroMoves) {
                cornerZeroMoves = tempCornerZeroMoves;
                edgeZeroMoves = tempEdgeZeroMoves;
                treatAs = find;
            }
        }
        System.out.println(compress(testCube.moveDataRecord));
        System.out.println(edgeZeroMoves);
        System.out.println(cornerZeroMoves);
        /* The challenge with PLL is the same as OLL, there's 4 different rotations of every single case.
         * They fall under 4 categories: corner-only switch, edge-only switch, double double switch, and double triple switch
         * Both corner-only and edge-only switches are easy to figure out, since they feature a solved state of either
         * corners or edges.
         * Double double switch are characterized by at least 2 of both corners and edges are share a treatAs. Then, it's really a
         * matter of locking the solved pieces into a corner and checking for the case
         * Finally, the hardest is double triple switches. They feature a 3-way switch in both the edges and corners, the same
         * as doing both a corner-only and edge-only switch. The main difficulty is that they are very random looking, and thus 
         * take alot of searching to figure out
         */

        if (cornerZeroMoves + edgeZeroMoves == 8) {
            // if 4 edges and 4 corners are solved, then we are incrediably lucky and don't have to add PLL to the solve
        } else if (cornerZeroMoves == 4) {
            if (edgeZeroMoves == 1) {
                while (testCube.getCubeletNumber(8) != topEdgeTreatAs[treatAs][8 - 8]) {
                    testCube.y();
                }
                if (testCube.getCubeletNumber(11) == topEdgeTreatAs[treatAs][10 - 8]) { // case 6
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.L();
                    testCube.RPrime();
                    testCube.FSquared();
                    testCube.LPrime();
                    testCube.R();
                    testCube.UPrime();
                    testCube.FSquared();
                } else { // case 7
                    testCube.FSquared();
                    testCube.U();
                    testCube.L();
                    testCube.RPrime();
                    testCube.FSquared();
                    testCube.LPrime();
                    testCube.R();
                    testCube.U();
                    testCube.FSquared();
                }
            } else {
                if (testCube.getCubeletNumber(8) == topEdgeTreatAs[treatAs][10 - 8]) { // case 5
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.RSquared();
                    testCube.D();
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.RSquared();
                } else {
                    while (testCube.getCubeletNumber(8) != topEdgeTreatAs[treatAs][11 - 8]) {
                        testCube.y();
                    }// case 4
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.LSquared();
                    testCube.RSquared();
                    testCube.D();
                    testCube.L();
                    testCube.RPrime();
                    testCube.DSquared();
                    testCube.USquared();
                    testCube.L();
                    testCube.RPrime();
                }
            }
        } else if (edgeZeroMoves == 4) {
            if (cornerZeroMoves == 1) {
                while (testCube.getCubeletNumber(3) != topCornerTreatAs[treatAs][3]) {
                    testCube.y();
                }
                if (testCube.getCubeletNumber(0) == topCornerTreatAs[treatAs][1]) { // case 1
                    testCube.BSquared();
                    testCube.RSquared();
                    testCube.BPrime();
                    testCube.LPrime();
                    testCube.B();
                    testCube.RSquared();
                    testCube.BPrime();
                    testCube.L();
                    testCube.BPrime();
                } else { // case 2
                    testCube.B();
                    testCube.LPrime();
                    testCube.B();
                    testCube.RSquared();
                    testCube.BPrime();
                    testCube.L();
                    testCube.B();
                    testCube.RSquared();
                    testCube.BSquared();
                }
            } else {
                boolean case3 = false;
                for (int i = 0; i < 4; i++) {
                    if (testCube.getCubeletNumber(0) != topCornerTreatAs[treatAs][1]) {
                        testCube.y();
                    } else {
                        case3 = true;
                        // case 3
                        testCube.L();
                        testCube.UPrime();
                        testCube.R();
                        testCube.DSquared();
                        testCube.RPrime();
                        testCube.U();
                        testCube.LPrime();
                        testCube.R();
                        testCube.UPrime();
                        testCube.L();
                        testCube.DSquared();
                        testCube.LPrime();
                        testCube.U();
                        testCube.RPrime();
                    }
                }// in rare cases, a case 5 looks like a case 3, so it needs to be checked
                if (!case3) {// case 5
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.RSquared();
                    testCube.D();
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.RSquared();
                }
            }
        } else if (edgeZeroMoves + cornerZeroMoves >= 4) {
            boolean rightCornerPossible = false;
            for (int i = 0; i < 4; i++) {
                if (testCube.getCubeletNumber(0) != topCornerTreatAs[treatAs][0] || testCube.getCubeletNumber(8) != topEdgeTreatAs[treatAs][8 - 8]) {
                    testCube.y();
                } else {
                    rightCornerPossible = true;
                }
            }
            if (rightCornerPossible) {
                testCube.y();
                if (testCube.getCubeletNumber(0) == topCornerTreatAs[treatAs][0] && testCube.getCubeletNumber(8) == topEdgeTreatAs[treatAs][8 - 8]) { // case 9
                    testCube.RSquared();
                    testCube.B();
                    testCube.U();
                    testCube.BPrime();
                    testCube.RSquared();
                    testCube.F();
                    testCube.DPrime();
                    testCube.F();
                    testCube.D();
                    testCube.FSquared();
                } else {
                    testCube.y();
                    testCube.y();
                    if (testCube.getCubeletNumber(0) == topCornerTreatAs[treatAs][0] && testCube.getCubeletNumber(8) == topEdgeTreatAs[treatAs][8 - 8]) { // case 9
                        testCube.RSquared();
                        testCube.B();
                        testCube.U();
                        testCube.BPrime();
                        testCube.RSquared();
                        testCube.F();
                        testCube.DPrime();
                        testCube.F();
                        testCube.D();
                        testCube.FSquared();
                    } else {
                        testCube.y();
                        if (testCube.getCubeletNumber(11) == topEdgeTreatAs[treatAs][9 - 8]) {
                            if (testCube.getCubeletNumber(1) == topCornerTreatAs[treatAs][2]) { // case 10
                                testCube.RSquared();
                                testCube.UPrime();
                                testCube.RSquared();
                                testCube.D();
                                testCube.BSquared();
                                testCube.LSquared();
                                testCube.U();
                                testCube.LSquared();
                                testCube.DPrime();
                                testCube.BSquared();
                            } else if (testCube.getCubeletNumber(2) == topCornerTreatAs[treatAs][3]) { // case 13
                                testCube.R();
                                testCube.LPrime();
                                testCube.BSquared();
                                testCube.R();
                                testCube.DPrime();
                                testCube.L();
                                testCube.BSquared();
                                testCube.RPrime();
                                testCube.U();
                                testCube.RPrime();
                                testCube.LSquared();
                                testCube.FSquared();
                                testCube.LSquared();
                            } else if (testCube.getCubeletNumber(1) == topCornerTreatAs[treatAs][3]) { // case 19
                                testCube.RSquared();
                                testCube.FSquared();
                                testCube.USquared();
                                testCube.FSquared();
                                testCube.U();
                                testCube.FSquared();
                                testCube.RSquared();
                                testCube.USquared();
                                testCube.RSquared();
                                testCube.U();
                                testCube.FSquared();
                                testCube.USquared();
                                testCube.RSquared();
                            }
                        } else if (testCube.getCubeletNumber(10) == topEdgeTreatAs[treatAs][9 - 8]) {
                            if (testCube.getCubeletNumber(3) == topCornerTreatAs[treatAs][2]) { // case 8
                                testCube.LSquared();
                                testCube.BPrime();
                                testCube.UPrime();
                                testCube.B();
                                testCube.LSquared();
                                testCube.FPrime();
                                testCube.D();
                                testCube.FPrime();
                                testCube.DPrime();
                                testCube.FSquared();
                            } else if (testCube.getCubeletNumber(3) == topCornerTreatAs[treatAs][1]) { // case 18
                                testCube.R();
                                testCube.U();
                                testCube.DSquared();
                                testCube.LPrime();
                                testCube.U();
                                testCube.L();
                                testCube.USquared();
                                testCube.FSquared();
                                testCube.D();
                                testCube.R();
                                testCube.DPrime();
                                testCube.FSquared();
                                testCube.DSquared();
                                testCube.RPrime();
                            }
                        } else if (testCube.getCubeletNumber(10) == topEdgeTreatAs[treatAs][11 - 8]) {
                            if (testCube.getCubeletNumber(1) == topCornerTreatAs[treatAs][2]) { // case 11
                                testCube.FPrime();
                                testCube.USquared();
                                testCube.F();
                                testCube.USquared();
                                testCube.FPrime();
                                testCube.L();
                                testCube.F();
                                testCube.U();
                                testCube.FPrime();
                                testCube.UPrime();
                                testCube.FPrime();
                                testCube.LPrime();
                                testCube.FSquared();
                            } else if (testCube.getCubeletNumber(1) == topCornerTreatAs[treatAs][3]) { // case 21
                                testCube.RSquared();
                                testCube.U();
                                testCube.RSquared();
                                testCube.U();
                                testCube.RSquared();
                                testCube.UPrime();
                                testCube.BPrime();
                                testCube.UPrime();
                                testCube.B();
                                testCube.RSquared();
                                testCube.BPrime();
                                testCube.U();
                                testCube.B();
                            }
                        }
                    }
                }
            } else {
                while (testCube.getCubeletNumber(1) != topCornerTreatAs[treatAs][1] || testCube.getCubeletNumber(8) != topEdgeTreatAs[treatAs][8 - 8]) {
                    testCube.y();
                }
                if (testCube.getCubeletNumber(2) == topCornerTreatAs[treatAs][2]) { // case 12
                    testCube.F();
                    testCube.USquared();
                    testCube.B();
                    testCube.FPrime();
                    testCube.U();
                    testCube.BPrime();
                    testCube.RPrime();
                    testCube.F();
                    testCube.USquared();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.F();
                    testCube.R();
                } else { // case 20
                    testCube.FSquared();
                    testCube.RSquared();
                    testCube.USquared();
                    testCube.RSquared();
                    testCube.UPrime();
                    testCube.RSquared();
                    testCube.FSquared();
                    testCube.USquared();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.RSquared();
                    testCube.USquared();
                    testCube.FSquared();
                }
            }
        } else {
            boolean rightCornerPossible = false;
            for (int i = 0; i < 4; i++) {
                if (testCube.getCubeletFaceColor(8, 1) != testCube.getCubeletFaceColor(0, 1)) {
                    testCube.y();
                } else {
                    rightCornerPossible = true;
                    break;
                }
            }

            if (rightCornerPossible) {
                for (int find = 0; find < 4; find++) {
                    if (topCornerTreatAs[find][0] == testCube.getCubeletNumber(0) && topEdgeTreatAs[find][8 - 8] == testCube.getCubeletNumber(8)) {
                        treatAs = find;
                    }
                }
                if (testCube.getCubeletNumber(9) == topEdgeTreatAs[treatAs][11 - 8]) { // case 16
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.U();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.FSquared();
                    testCube.D();
                    testCube.FSquared();
                    testCube.DPrime();
                    testCube.BSquared();
                    testCube.LSquared();
                } else if (testCube.getCubeletNumber(11) == topEdgeTreatAs[treatAs][9 - 8]) { // case 15
                    testCube.LSquared();
                    testCube.BSquared();
                    testCube.D();
                    testCube.FSquared();
                    testCube.DPrime();
                    testCube.FSquared();
                    testCube.U();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.FSquared();
                    testCube.BSquared();
                    testCube.LSquared();
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    if (testCube.getCubeletFaceColor(8, 1) != testCube.getCubeletFaceColor(1, 1)) {
                        testCube.y();
                    }
                }
                for (int find = 0; find < 4; find++) {
                    if (topCornerTreatAs[find][1] == testCube.getCubeletNumber(1) && topEdgeTreatAs[find][8 - 8] == testCube.getCubeletNumber(8)) {
                        treatAs = find;
                    }
                }
                if (testCube.getCubeletNumber(9) == topEdgeTreatAs[treatAs][11 - 8]) { // case 17
                    testCube.RSquared();
                    testCube.BSquared();
                    testCube.DPrime();
                    testCube.FSquared();
                    testCube.D();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.FSquared();
                    testCube.U();
                    testCube.FSquared();
                    testCube.BSquared();
                    testCube.RSquared();
                } else if (testCube.getCubeletNumber(11) == topEdgeTreatAs[treatAs][9 - 8]) { // case 14
                    testCube.RSquared();
                    testCube.BSquared();
                    testCube.FSquared();
                    testCube.UPrime();
                    testCube.FSquared();
                    testCube.U();
                    testCube.FSquared();
                    testCube.DPrime();
                    testCube.FSquared();
                    testCube.D();
                    testCube.BSquared();
                    testCube.RSquared();
                }
            }
        }
        while (testCube.getCubeletPosition(0) != 0) {
            testCube.U();
        }

    }
}
