package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This object is composed of 20 cubelets and a translator. It has every provision
 * needed to create, move, and then get information about a cube. It contains 21 methods for 
 * turning and rotating the cube
 */
public class Cube {

    // arrays meant to speed up calculations
    // all the cubelets that are true are associated with eachother, and all the false cubelets are also similar
    final static boolean[] isCorner = {true, false, true, false, false, true, false, true};
    // edge cubelets with numbers that return true are affected by a y rotation
    static final boolean[] WYExclusion = {false, false, false, false, true, true, true, true, false, false, false, false};
    // edge cubelets with numbers that return true are affected by a z rotation
    static final boolean[] ROExclusion = {false, true, false, true, false, false, false, false, false, true, false, true};
    // edge cubelets with numbers that return true are affected by a x rotation
    static final boolean[] BGExclusion = {true, false, true, false, false, false, false, false, true, false, true, false};
    static final boolean[] XExclusion = {true, false, true, false, false, false, false, false, true, false, true, false};// same as bg
    static final boolean[] YExclusion = {false, false, false, false, true, true, true, true, false, false, false, false};// same as wy
    // arrays that hold the cubelets, the index of each element is also it's position
    Cubelet[] cubeletPositions = new Cubelet[20];
    // a copy of the oldCorners and oldEdges
    Cubelet[] newCubelets = new Cubelet[20];
    // A sorted array of the same cublets, the index of each element is it's number
    Cubelet[] cubeletNumbers = new Cubelet[20];
    // cubePhase is a flag that signifies when isCorner rules are to be reversed
    boolean cubePhase = true;
    // this is the string that stores all the moves that the cube as taken
    String moveDataRecord = "";
    // flag to see if the current move is to be added to moveDataRecord
    boolean recording = false;
    // the translator that interperates the cube into letters and colors
    Translator translator;

    // constructs a cube in default NSS position
    Cube() {
        translator = new Translator();
        for (int i = 0; i < 20; i++) {
            cubeletPositions[i] = new Cubelet(i, translator);
        }
        newCubelets = cubeletPositions.clone();
        cubeletNumbers = cubeletPositions.clone();
    }

    // constructs a cube as the sum if its parts
    Cube(Cubelet[] cubelets, boolean cubePhase, Translator translator) {
        this.translator = new Translator(translator);
        this.cubeletPositions = cubelets.clone();
        newCubelets = cubelets.clone();
        this.cubePhase = cubePhase;
        cubeletNumbers = cubelets.clone();
        cubeletSelectionSort(cubeletNumbers);
    }

    // copying a cube and breaking reference
    Cube(Cube c) {
        for (int i = 0; i < 20; i++) {
            this.cubeletPositions[i] = new Cubelet(c.cubeletPositions[i]);
        }
        newCubelets = cubeletPositions.clone();
        cubeletNumbers = cubeletPositions.clone();
        cubeletSelectionSort(cubeletNumbers);
        this.cubePhase = c.cubePhase;
        this.translator = new Translator(c.translator);
        this.moveDataRecord = c.moveDataRecord;
        this.recording = c.recording;
    }

    // copying a cube without breaking reference
    void makeSame(Cube c) {
        for (int i = 0; i < 20; i++) {
            this.cubeletPositions[i] = new Cubelet(c.cubeletPositions[i]);
        }
        newCubelets = cubeletPositions.clone();
        cubeletNumbers = cubeletPositions.clone();
        cubeletSelectionSort(cubeletNumbers);
        this.cubePhase = c.cubePhase;
        this.translator = new Translator(c.translator);
        this.moveDataRecord = c.moveDataRecord;
        this.recording = c.recording;
    }

    // outputs which faces are facing up in order of cubelet position
    void printFaces() {
        int i = 0;
        for (Cubelet CU : cubeletPositions) {
            System.out.print(i++ + " ");
            CU.getFace();
        }
    }

    // outputs all the data about the cubelets, in order of position
    void printCubelets() {
        for (Cubelet CU : cubeletPositions) {
            CU.output();
        }
    }

    // clears the moveDataRecord
    void resetMoveDataRecord() {
        moveDataRecord = "";
    }

    // start recording moves
    void startRecording() {
        recording = true;
    }

    // stop recording moves
    void stopRecording() {
        recording = false;
    }

    // get orientation based on number
    int getCubeletOrientationN(int number) {
        return cubeletNumbers[number].orientation;
    }

    // get orientation based on position
    int getCubeletOrientationP(int position) {
        return cubeletPositions[position].orientation;
    }

    // get position based on number
    int getCubeletPosition(int number) {
        return cubeletNumbers[number].position;
    }

    // get number based on position
    int getCubeletNumber(int position) {
        return cubeletPositions[position].number;
    }

    // get the placement of the top-colored face (0 = top/bottom, 1 = front/back, 2 = right/left) based on number
    int getCornerFacePositionN(int number) { // 0 = facing up, 1 = facing towards the back, 2 = facing towards the sides
        if (cubePhase) {
            if ((isCorner[cubeletNumbers[number].originalNumber] && isCorner[getCubeletPosition(number)]) || (!isCorner[cubeletNumbers[number].originalNumber] && !isCorner[getCubeletPosition(number)])) { // is the cubelet's original number in corner and it's position is incorner?
                return getCubeletOrientationN(number) == 0 ? 0 : getCubeletOrientationN(number) == 1 ? 2 : 1;
            } else {
                return getCubeletOrientationN(number);
            }
        } else {
            if ((isCorner[cubeletNumbers[number].originalNumber] && isCorner[getCubeletPosition(number)]) || (!isCorner[cubeletNumbers[number].originalNumber] && !isCorner[getCubeletPosition(number)])) { // is the cubelet's original number in corner and it's position is incorner?
                return getCubeletOrientationN(number);
            } else {
                return getCubeletOrientationN(number) == 0 ? 0 : getCubeletOrientationN(number) == 1 ? 2 : 1;
            }
        }
    }

    // get the placement of the top-colored face (0 = top/bottom, 1 = front/back, 2 = right/left) based on position
    int getCornerFacePositionP(int position) { // 0 = facing up, 1 = facing towards the back, 2 = facing towards the sides
        if (cubePhase) {
            if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                return getCubeletOrientationP(position) == 0 ? 0 : getCubeletOrientationP(position) == 1 ? 2 : 1;
            } else {
                return getCubeletOrientationP(position);
            }
        } else {
            if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                return getCubeletOrientationP(position);
            } else {
                return getCubeletOrientationP(position) == 0 ? 0 : getCubeletOrientationP(position) == 1 ? 2 : 1;
            }
        }
    }

    // get the color of a cubelet's face based on position
    int getCubeletFaceColor(int position, int face) {
        int[] NSSstate;
        if (position < 8) {
            NSSstate = cubeletPositions[position].getCornerFaceColors();
            if (cubePhase) {
                if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                } else {
                    int temp = NSSstate[2];
                    NSSstate[2] = NSSstate[1];
                    NSSstate[1] = temp;
                }
            } else {
                if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                } else {
                    int temp = NSSstate[2];
                    NSSstate[2] = NSSstate[1];
                    NSSstate[1] = temp;
                }
            }
        } else {
            NSSstate = cubeletPositions[position].getEdgeFaceColors();
        }
        return NSSstate[face];
    }

    // get the color representation of a cubelet based on position
    int[] getCubeletFaceColors(int position) {
        int[] NSSstate;
        if (position < 8) {
            NSSstate = cubeletPositions[position].getCornerFaceColors();
            if (cubePhase) {
                if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                } else {
                    int temp = NSSstate[2];
                    NSSstate[2] = NSSstate[1];
                    NSSstate[1] = temp;
                }
            } else {
                if ((isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position])) { // is the cubelet's original number in corner and it's position is incorner?
                    int temp = NSSstate[2];
                    NSSstate[2] = NSSstate[1];
                    NSSstate[1] = temp;
                } else {
                }
            }
        } else {
            NSSstate = cubeletPositions[position].getEdgeFaceColors();
        }
        return NSSstate;
    }

    // sorts cubelets by number using a selectionsort
    private static void cubeletSelectionSort(Cubelet[] list) {
        for (int top = list.length - 1; top > 0; top--) {
            int largeIndex = 0;
            for (int i = 1; i <= top; i++) {
                if (list[i].number > list[largeIndex].number) {
                    largeIndex = i;
                }
            }
            Cubelet temp = list[top];
            list[top] = list[largeIndex];
            list[largeIndex] = temp;
        }
    }

    // executes the move inside the string
    void doMove(String s) {
        s = s.toUpperCase().trim();
        if (s.length() == 0) {
            s = " ";

        }
        switch (s.charAt(0)) {
            case 32:
                break;
            case 70: // F
                if (s.length() == 1) {
                    F();
                } else if (s.charAt(1) == 50) {
                    FSquared();
                } else {
                    FPrime();
                }
                break;
            case 66: // B
                if (s.length() == 1) {
                    B();
                } else if (s.charAt(1) == 50) {
                    BSquared();
                } else {
                    BPrime();
                }
                break;
            case 85: // U
                if (s.length() == 1) {
                    U();
                } else if (s.charAt(1) == 50) {
                    USquared();
                } else {
                    UPrime();
                }
                break;
            case 68: // D
                if (s.length() == 1) {
                    D();
                } else if (s.charAt(1) == 50) {
                    DSquared();
                } else {
                    DPrime();
                }
                break;
            case 82: // R
                if (s.length() == 1) {
                    R();
                } else if (s.charAt(1) == 50) {
                    RSquared();
                } else {
                    RPrime();
                }
                break;
            case 76: // L
                if (s.length() == 1) {
                    L();
                } else if (s.charAt(1) == 50) {
                    LSquared();
                } else {
                    LPrime();
                }
                break;
            case 88:  // x
                if (s.length() == 1) {
                    x();
                } else if (s.charAt(1) == 50) {
                    x();
                    x();
                } else {
                    x();
                    x();
                    x();
                }
                break;
            case 89:  // y
                if (s.length() == 1) {
                    y();
                } else if (s.charAt(1) == 50) {
                    y();
                    y();
                } else {
                    y();
                    y();
                    y();
                }
                break;
            case 90:// z
                if (s.length() == 1) {
                    z();
                } else if (s.charAt(1) == 50) {
                    z();
                    z();
                } else {
                    z();
                    z();
                    z();
                }
                break;
        }

    }

    // executes the reverse of the move inside the string
    void doReverseMove(String s) {
        s = s.toUpperCase().trim();
        if (s.length() == 0) {
            s = " ";
        }
        switch (s.charAt(0)) {
            case 32:
                break;
            case 70: // F
                if (s.length() == 1) {
                    FPrime();
                } else if (s.charAt(1) == 50) {
                    FSquared();
                } else {
                    F();
                }
                break;
            case 66: // B
                if (s.length() == 1) {
                    BPrime();
                } else if (s.charAt(1) == 50) {
                    BSquared();
                } else {
                    B();
                }
                break;
            case 85: // U
                if (s.length() == 1) {
                    UPrime();
                } else if (s.charAt(1) == 50) {
                    USquared();
                } else {
                    U();
                }
                break;
            case 68: // D
                if (s.length() == 1) {
                    DPrime();
                } else if (s.charAt(1) == 50) {
                    DSquared();
                } else {
                    D();
                }
                break;
            case 82: // R
                if (s.length() == 1) {
                    RPrime();
                } else if (s.charAt(1) == 50) {
                    RSquared();
                } else {
                    R();
                }
                break;
            case 76: // L
                if (s.length() == 1) {
                    LPrime();
                } else if (s.charAt(1) == 50) {
                    LSquared();
                } else {
                    L();
                }
                break;
            case 88:  // x
                if (s.length() == 1) {
                    x();
                    x();
                    x();
                } else if (s.charAt(1) == 50) {
                    x();
                    x();
                } else {

                    x();
                }
                break;
            case 89:  // y
                if (s.length() == 1) {
                    y();
                    y();
                    y();
                } else if (s.charAt(1) == 50) {
                    y();
                    y();
                } else {

                    y();
                }
                break;
            case 90:// z
                if (s.length() == 1) {
                    z();
                    z();
                    z();
                } else if (s.charAt(1) == 50) {
                    z();
                    z();
                } else {
                    z();
                }
                break;
        }
    }

    // returns true if the cube is in a solved state
    boolean isSolved() {
        for (int position = 0; position < 20; position++) {
            if (getCubeletNumber(position) != position || getCubeletOrientationP(position) != 0) {
                return false;
            }
        }
        return true;

    }

    // swap the cubelets around based on a new order
    private void swapCubelets(int[] newOrder) {
        newCubelets = cubeletPositions.clone();
        for (int i = 0; i < 20; i++) {
            newCubelets[i] = cubeletPositions[newOrder[i]];
            newCubelets[i].setPosition(i);
        }
        cubeletPositions = newCubelets.clone();
    }

    // switch the edges of the cubelets in positions listed in i
    private void orientEdges(int[] i) {
        for (int j : i) {
            cubeletPositions[j].setOrientation(cubeletPositions[j].orientation == 0 ? 1 : 0);
        }
    }

    // returns that, if the corner in question was on orientation 0, would it be
    // the front/back color that faces front/back?
    private boolean cornerInPosition(int position) {
        if (cubePhase) {
            return (isCorner[cubeletPositions[position].originalNumber] && isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && !isCorner[position]);
        } else {
            return (isCorner[cubeletPositions[position].originalNumber] && !isCorner[position]) || (!isCorner[cubeletPositions[position].originalNumber] && isCorner[position]);
        }
    }

    // reorienting corners based on turn type and inPositions
    private void orientCornersLR(int[] j) {
        for (int i : j) {
            if (cornerInPosition(i)) {
                if (cubeletPositions[i].orientation == 0) {
                    cubeletPositions[i].setOrientation(2);
                } else if (cubeletPositions[i].orientation == 1) {
                    cubeletPositions[i].setOrientation(0);
                } else if (cubeletPositions[i].orientation == 2) {
                    cubeletPositions[i].setOrientation(1);
                }
            } else {
                if (cubeletPositions[i].orientation == 0) {
                    cubeletPositions[i].setOrientation(1);
                } else if (cubeletPositions[i].orientation == 1) {
                    cubeletPositions[i].setOrientation(2);
                } else if (cubeletPositions[i].orientation == 2) {
                    cubeletPositions[i].setOrientation(0);
                }
            }
        }
    }

    private void orientCornersFB(int[] j) {
        for (int i : j) {
            if (cornerInPosition(i)) {
                if (cubeletPositions[i].orientation == 0) {
                    cubeletPositions[i].setOrientation(1);
                } else if (cubeletPositions[i].orientation == 1) {
                    cubeletPositions[i].setOrientation(2);
                } else if (cubeletPositions[i].orientation == 2) {
                    cubeletPositions[i].setOrientation(0);
                }
            } else {
                if (cubeletPositions[i].orientation == 0) {
                    cubeletPositions[i].setOrientation(2);
                } else if (cubeletPositions[i].orientation == 1) {
                    cubeletPositions[i].setOrientation(0);
                } else if (cubeletPositions[i].orientation == 2) {
                    cubeletPositions[i].setOrientation(1);
                }
            }
        }
    }

    // 18 non-rotational moves. Corners need to be reoriented on F/B/L/R, while
    // edges need to be reoriented on F and B's only
    void R() {
        if (recording) {
            moveDataRecord = moveDataRecord + " R";
        }
        int[] newCubeletPositions = {0, 2, 6, 3, 4, 1, 5, 7, 8, 14, 10, 11, 12, 9, 17, 15, 16, 13, 18, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {1, 2, 5, 6};
        orientCornersLR(changedCorners);
    }

    void RPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " R R R";
        }
        int[] newCubeletPositions = {0, 5, 1, 3, 4, 6, 2, 7, 8, 13, 10, 11, 12, 17, 9, 15, 16, 14, 18, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {1, 2, 5, 6};
        orientCornersLR(changedCorners);
    }

    void RSquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " R R";
        }
        int[] newCubeletPositions = {0, 6, 5, 3, 4, 2, 1, 7, 8, 17, 10, 11, 12, 14, 13, 15, 16, 9, 18, 19};
        swapCubelets(newCubeletPositions);
    }

    void L() {
        if (recording) {
            moveDataRecord = moveDataRecord + " L";
        }
        int[] newCubeletPositions = {4, 1, 2, 0, 7, 5, 6, 3, 8, 9, 10, 12, 19, 13, 14, 11, 16, 17, 18, 15};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {0, 3, 4, 7};
        orientCornersLR(changedCorners);
    }

    void LPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " L L L";
        }
        int[] newCubeletPositions = {3, 1, 2, 7, 0, 5, 6, 4, 8, 9, 10, 15, 11, 13, 14, 19, 16, 17, 18, 12};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {0, 3, 4, 7};
        orientCornersLR(changedCorners);
    }

    void LSquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " L L";
        }
        int[] newCubeletPositions = {7, 1, 2, 4, 3, 5, 6, 0, 8, 9, 10, 19, 15, 13, 14, 12, 16, 17, 18, 11};
        swapCubelets(newCubeletPositions);
    }

    void F() {
        if (recording) {
            moveDataRecord = moveDataRecord + " F";
        }
        int[] newCubeletPositions = {0, 1, 3, 7, 4, 5, 2, 6, 8, 9, 15, 11, 12, 13, 10, 18, 16, 17, 14, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {2, 3, 6, 7};
        orientCornersFB(changedCorners);

        int[] changedEdges = {10, 14, 15, 18};
        orientEdges(changedEdges);
    }

    void FPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " F F F";
        }
        int[] newCubeletPositions = {0, 1, 6, 2, 4, 5, 7, 3, 8, 9, 14, 11, 12, 13, 18, 10, 16, 17, 15, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {2, 3, 6, 7};
        orientCornersFB(changedCorners);

        int[] changedEdges = {10, 14, 15, 18};
        orientEdges(changedEdges);
    }

    void FSquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " F F";
        }
        int[] newCubeletPositions = {0, 1, 7, 6, 4, 5, 3, 2, 8, 9, 18, 11, 12, 13, 15, 14, 16, 17, 10, 19};
        swapCubelets(newCubeletPositions);
    }

    void B() {
        if (recording) {
            moveDataRecord = moveDataRecord + " B";
        }
        int[] newCubeletPositions = {1, 5, 2, 3, 0, 4, 6, 7, 13, 9, 10, 11, 8, 16, 14, 15, 12, 17, 18, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {0, 1, 4, 5};
        orientCornersFB(changedCorners);

        int[] changedEdges = {8, 12, 13, 16};
        orientEdges(changedEdges);
    }

    void BPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " B B B";
        }
        int[] newCubeletPositions = {4, 0, 2, 3, 5, 1, 6, 7, 12, 9, 10, 11, 16, 8, 14, 15, 13, 17, 18, 19};
        swapCubelets(newCubeletPositions);

        int[] changedCorners = {0, 1, 4, 5};
        orientCornersFB(changedCorners);

        int[] changedEdges = {8, 12, 13, 16};
        orientEdges(changedEdges);
    }

    void BSquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " B B";
        }
        int[] newCubeletPositions = {5, 4, 2, 3, 1, 0, 6, 7, 16, 9, 10, 11, 13, 12, 14, 15, 8, 17, 18, 19};
        swapCubelets(newCubeletPositions);
    }

    void U() {
        if (recording) {
            moveDataRecord = moveDataRecord + " U";
        }
        int[] newCubeletPositions = {3, 0, 1, 2, 4, 5, 6, 7, 11, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19};
        swapCubelets(newCubeletPositions);
    }

    void UPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " U U U";
        }
        int[] newCubeletPositions = {1, 2, 3, 0, 4, 5, 6, 7, 9, 10, 11, 8, 12, 13, 14, 15, 16, 17, 18, 19};
        swapCubelets(newCubeletPositions);
    }

    void USquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " U U";
        }
        int[] newCubeletPositions = {2, 3, 0, 1, 4, 5, 6, 7, 10, 11, 8, 9, 12, 13, 14, 15, 16, 17, 18, 19};
        swapCubelets(newCubeletPositions);
    }

    void D() {
        if (recording) {
            moveDataRecord = moveDataRecord + " D";
        }
        int[] newCubeletPositions = {0, 1, 2, 3, 5, 6, 7, 4, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 16};
        swapCubelets(newCubeletPositions);
    }

    void DPrime() {
        if (recording) {
            moveDataRecord = moveDataRecord + " D D D";
        }
        int[] newCubeletPositions = {0, 1, 2, 3, 7, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 19, 16, 17, 18};
        swapCubelets(newCubeletPositions);
    }

    void DSquared() {
        if (recording) {
            moveDataRecord = moveDataRecord + " D D";
        }
        int[] newCubeletPositions = {0, 1, 2, 3, 6, 7, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19, 16, 17};
        swapCubelets(newCubeletPositions);
    }

    // 3 rotational moves, all take significantly more to execute than a single move. 
    void x() {
        if (recording) {
            moveDataRecord = moveDataRecord + " x";
        }
        // reorients corners
        for (int i = 0; i < 8; i++) {
            if (cubeletPositions[i].orientation == 2) {
                if (cornerInPosition(i)) {
                    cubeletPositions[i].setOrientation(1);
                } else {
                    cubeletPositions[i].setOrientation(0);
                }
            } else {
                if (cornerInPosition(i)) {
//                    if (oldCorners[i].orientation == 0) {
//                        oldCorners[i].setOrientation(0);
//                    }
                    if (cubeletPositions[i].orientation == 1) {
                        cubeletPositions[i].setOrientation(2);
                    }
                } else {
                    if (cubeletPositions[i].orientation == 0) {
                        cubeletPositions[i].setOrientation(2);
                    }
//                    if (oldCorners[i].orientation == 1) {
//                        oldCorners[i].setOrientation(1);
//                    }
                }
            }
        }
        // reorients edges
        orientEdgesOnRotate(true);
        // change cubephase
        cubePhase = cubePhase ? false : true;
        // rename and order cubelets
        int[] newCubeletPositions = {3, 2, 6, 7, 0, 1, 5, 4, 10, 14, 18, 15, 11, 9, 17, 19, 8, 13, 16, 12};
        swapCubelets(newCubeletPositions);
        renameCubelets(newCubeletPositions);

        // facechange the translator
        translator.faceChange(0);
    }

    void y() {
        if (recording) {
            moveDataRecord = moveDataRecord + " y";
        }
        // reorients corners
        for (int i = 0; i < 8; i++) {
            if (cubeletPositions[i].orientation != 0) {
                cubeletPositions[i].setOrientation(cubeletPositions[i].orientation == 1 ? 2 : 1);
            }
        }
        // reorients edges
        orientEdgesOnRotate(false);
        // change cubephase
        cubePhase = cubePhase ? false : true;
        // rename and order cubelets
        int[] newCubeletPositions = {3, 0, 1, 2, 7, 4, 5, 6, 11, 8, 9, 10, 15, 12, 13, 14, 19, 16, 17, 18};
        renameCubelets(newCubeletPositions);
        swapCubelets(newCubeletPositions);
        // facechange the translator
        translator.faceChange(1);

    }

    // z rotations are exempt from needing to reorient edges
    void z() {
        if (recording) {
            moveDataRecord = moveDataRecord + " z";
        }
        // reorients corners
        for (int i = 0; i < 8; i++) {
            if (cubeletPositions[i].orientation == 1) {
                if (cornerInPosition(i)) {
                    cubeletPositions[i].setOrientation(2);
                } else {
                    cubeletPositions[i].setOrientation(0);
                }
            } else {
                if (cornerInPosition(i)) {
//                    if (oldCorners[i].orientation == 0) {
//                        oldCorners[i].setOrientation(0);
//                    }
                    if (cubeletPositions[i].orientation == 2) {
                        cubeletPositions[i].setOrientation(1);
                    }
                } else {
                    if (cubeletPositions[i].orientation == 0) {
                        cubeletPositions[i].setOrientation(1);
                    }
//                    if (oldCorners[i].orientation == 2) {
//                        oldCorners[i].setOrientation(2);
//                    }
                }
            }
        }
        // change cubephase
        cubePhase = cubePhase ? false : true;
        // rename and order cubelets
        int[] newCubeletPositions = {4, 0, 3, 7, 5, 1, 2, 6, 12, 11, 15, 19, 16, 8, 10, 18, 13, 9, 14, 17};
        renameCubelets(newCubeletPositions);
        swapCubelets(newCubeletPositions);
        // facechange the translator
        translator.faceChange(2);
    }

    // assigns the cubelets new orders based on the new faces
    private void renameCubelets(int[] newOrder) {
        newCubelets = cubeletNumbers.clone();
        for (int i = 0; i < 20; i++) {
            newCubelets[i] = cubeletNumbers[newOrder[i]];
            newCubelets[i].setNumber(i);
        }
        cubeletNumbers = newCubelets.clone();
    }

    // chooses which two exclusions to compare in the helper
    private void orientEdgesOnRotate(boolean whichRotate) {// X is true, Y is false
        if (whichRotate) {
            if (translator.Hierarchy[2][0].equalsIgnoreCase("W")) {
                orientEdgesOnRotateHelper(WYExclusion, XExclusion);
            } else if (translator.Hierarchy[2][0].equalsIgnoreCase("R")) {
                orientEdgesOnRotateHelper(ROExclusion, XExclusion);
            } else if (translator.Hierarchy[2][0].equalsIgnoreCase("B")) {
                orientEdgesOnRotateHelper(BGExclusion, XExclusion);
            }
        } else {
            if (translator.Hierarchy[0][0].equalsIgnoreCase("W")) {
                orientEdgesOnRotateHelper(WYExclusion, YExclusion);
            } else if (translator.Hierarchy[0][0].equalsIgnoreCase("R")) {
                orientEdgesOnRotateHelper(ROExclusion, YExclusion);
            } else if (translator.Hierarchy[0][0].equalsIgnoreCase("B")) {
                orientEdgesOnRotateHelper(BGExclusion, YExclusion);
            }
        }
    }

    // reorients the cubelets based on how the two exclusions compare
    private void orientEdgesOnRotateHelper(boolean[] exclusionCubelets, boolean[] exclusionPositions) {
        for (int i = 8; i < 20; i++) {
            if (exclusionPositions[i - 8]) {
                if (!exclusionCubelets[cubeletPositions[i].originalNumber - 8]) {
                    cubeletPositions[i].setOrientation(cubeletPositions[i].orientation == 0 ? 1 : 0);
                }
            } else {
                if (exclusionCubelets[cubeletPositions[i].originalNumber - 8]) {
                    cubeletPositions[i].setOrientation(cubeletPositions[i].orientation == 0 ? 1 : 0);
                }
            }
        }
    }

    // scrambles the cube
    String[] scramble() {
        int numMoves = (int) Math.floor(Math.random() * 10) + 9;
        String[] sequence = new String[numMoves];
        int previousAxis = -1;
        int moveAxis;
        int moveType;
        for (int selection = 0; selection < (numMoves); selection++) {
            do {
                moveAxis = (int) Math.floor(Math.random() * 3);
            } while (moveAxis == previousAxis);
            previousAxis = moveAxis;
            moveType = (int) Math.floor(Math.random() * 6);
            switch (moveAxis) {
                case 0:
                    switch (moveType) {
                        case 0:
                            sequence[selection] = "R";
                            R();
                            break;
                        case 1:
                            sequence[selection] = "RPrime";
                            RPrime();
                            break;
                        case 2:
                            sequence[selection] = "RSquared";
                            RSquared();
                            break;
                        case 3:
                            sequence[selection] = "L";
                            L();
                            break;
                        case 4:
                            sequence[selection] = "LPrime";
                            LPrime();
                            break;
                        case 5:
                            sequence[selection] = "LSquared";
                            LSquared();
                            break;
                    }
                    break;
                case 1:
                    switch (moveType) {
                        case 0:
                            sequence[selection] = "U";
                            U();
                            break;
                        case 1:
                            sequence[selection] = "UPrime";
                            UPrime();
                            break;
                        case 2:
                            sequence[selection] = "USquared";
                            USquared();
                            break;
                        case 3:
                            sequence[selection] = "D";
                            D();
                            break;
                        case 4:
                            sequence[selection] = "DPrime";
                            DPrime();
                            break;
                        case 5:
                            sequence[selection] = "DSquared";
                            DSquared();
                            break;
                    }
                    break;
                case 2:
                    switch (moveType) {
                        case 0:
                            sequence[selection] = "F";
                            F();
                            break;
                        case 1:
                            sequence[selection] = "FPrime";
                            FPrime();
                            break;
                        case 2:
                            sequence[selection] = "FSquared";
                            FSquared();
                            break;
                        case 3:
                            sequence[selection] = "B";
                            B();
                            break;
                        case 4:
                            sequence[selection] = "BPrime";
                            BPrime();
                            break;
                        case 5:
                            sequence[selection] = "BSquared";
                            BSquared();
                            break;
                    }
                    break;
            }
        }
        return sequence;
    }

    // scrambles the cube with rotations included
    String[] scrambleAndRotate() {
        int numMoves = (int) Math.floor(Math.random() * 8) + 5;
        String[] sequence = new String[numMoves];
        int previousAxis = -1;
        int moveAxis;
        int moveType;
        for (int selection = 0; selection < (numMoves); selection++) {
            if (Math.floor(Math.random() * 5) == 0) {
                switch ((int) Math.floor(Math.random() * 3)) {
                    case 0:
                        sequence[selection] = "x";
                        x();
                        break;
                    case 1:
                        sequence[selection] = "y";
                        y();
                        break;
                    case 2:
                        sequence[selection] = "z";
                        z();
                        break;
                }
            } else {
                do {
                    moveAxis = (int) Math.floor(Math.random() * 3);
                } while (moveAxis == previousAxis);
                previousAxis = moveAxis;
                moveType = (int) Math.floor(Math.random() * 6);
                switch (moveAxis) {
                    case 0:
                        switch (moveType) {
                            case 0:
                                sequence[selection] = "R";
                                R();
                                break;
                            case 1:
                                sequence[selection] = "RPrime";
                                RPrime();
                                break;
                            case 2:
                                sequence[selection] = "RSquared";
                                RSquared();
                                break;

                            case 3:
                                sequence[selection] = "L";
                                L();
                                break;
                            case 4:
                                sequence[selection] = "LPrime";
                                LPrime();
                                break;
                            case 5:
                                sequence[selection] = "LSquared";
                                LSquared();
                                break;
                        }
                        break;
                    case 2:
                        switch (moveType) {
                            case 0:
                                sequence[selection] = "U";
                                U();
                                break;
                            case 1:
                                sequence[selection] = "UPrime";
                                UPrime();
                                break;
                            case 2:
                                sequence[selection] = "USquared";
                                USquared();
                                break;

                            case 3:
                                sequence[selection] = "D";
                                D();
                                break;
                            case 4:
                                sequence[selection] = "DPrime";
                                DPrime();
                                break;
                            case 5:
                                sequence[selection] = "DSquared";
                                DSquared();
                                break;
                        }
                        break;
                    case 4:
                        switch (moveType) {
                            case 0:
                                sequence[selection] = "F";
                                F();
                                break;
                            case 1:
                                sequence[selection] = "FPrime";
                                FPrime();
                                break;
                            case 2:
                                sequence[selection] = "FSquared";
                                FSquared();
                                break;

                            case 3:
                                sequence[selection] = "B";
                                B();
                                break;
                            case 4:
                                sequence[selection] = "BPrime";
                                BPrime();
                                break;
                            case 5:
                                sequence[selection] = "BSquared";
                                BSquared();
                                break;
                        }
                        break;
                }
            }
        }
        return sequence;
    }
}
