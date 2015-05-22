package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: Each instance of cubelet represents a single piece of the cube.
 * A cube consists of 20 cubelets, 8 corners and 12 edges
 */
public class Cubelet {

    // the orientation of a cubelet
    int orientation;
    // the position of the cubelet on the cube
    int position;
    // the number of the cubelet, based on the current position of the faces
    int number;
    // the number of the cubelet when the cube is in the orientation top = white, front = red
    int originalNumber;
    // a translator that is constant over the cube and it's cubelets
    Translator translator;

    // constructs the cubelet in it's solved state
    Cubelet(int number, Translator translator) {
        this.number = number;
        this.originalNumber = number;
        orientation = 0;
        this.position = number;
        this.translator = translator;
    }

    // constructs the cubelet from the sum it its fields
    Cubelet(int number, int originalNumber, int orientation, int position, Translator translator) {
        this.number = number;
        this.orientation = orientation;
        this.position = position;
        this.originalNumber = originalNumber;
        this.translator = translator;
    }

    // clones and breaks the referencing of the cubelet
    Cubelet(Cubelet c) {
        this.orientation = c.orientation;
        this.number = c.number;
        this.position = c.position;
        this.originalNumber = c.originalNumber;
        this.translator = c.translator;
    }

    // mutator methods
    void setNumber(int number) {
        this.number = number;
    }

    void setPosition(int position) {
        this.position = position;
    }

    void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    // prints the face that is currently facing up or down (since the two are mutually exclusive)
    void getFace() {

        System.out.println("COLOR FACING UP: " + translator.getFace(originalNumber, orientation));
        // for middle edges, facing up means front or back
    }

    // returns an int[] with the number (representing colors) with index 0 = facing up, 1 = facing to the side
    int[] getEdgeFaceColors() {
        int[] faceColors = new int[2];
        // checks what color is facing up, then which one is facing to the side
        for (int i = 0; i < 2; i++) {
            if (translator.translation[originalNumber][i].equalsIgnoreCase("W")) {
                faceColors[i] = 0;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("Y")) {
                faceColors[i] = 1;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("R")) {
                faceColors[i] = 2;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("O")) {
                faceColors[i] = 3;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("B")) {
                faceColors[i] = 4;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("G")) {
                faceColors[i] = 5;
            }
        }
        // reverses the two if the cubelet's orientation is 1
        if (orientation == 1) {
            int temp = faceColors[0];
            faceColors[0] = faceColors[1];
            faceColors[1] = temp;
        }
        return faceColors;
    }

    // returns an int[] with the number (representing colors) with 
    // index 0 = facing up, 1 = facing to the front or back, 2 = facing to the side
    int[] getCornerFaceColors() {
        int[] faceColors = new int[3];
        // checks what color is facing up, then which one is facing front, and finally to the side
        // in an orientation 0 state
        for (int i = 0; i < 3; i++) {
            if (translator.translation[originalNumber][i].equalsIgnoreCase("W")) {
                faceColors[i] = 0;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("Y")) {
                faceColors[i] = 1;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("R")) {
                faceColors[i] = 2;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("O")) {
                faceColors[i] = 3;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("B")) {
                faceColors[i] = 4;
            } else if (translator.translation[originalNumber][i].equalsIgnoreCase("G")) {
                faceColors[i] = 5;
            }
        }
        int temp;
        // it then switches the colors around based on orientations
        switch (orientation) {
            case 0:
                break;
            case 1:
                temp = faceColors[0];
                faceColors[0] = faceColors[1];
                faceColors[1] = temp;
                temp = faceColors[2];
                faceColors[2] = faceColors[1];
                faceColors[1] = temp;
                break;
            case 2:
                temp = faceColors[0];
                faceColors[0] = faceColors[1];
                faceColors[1] = temp;
                temp = faceColors[2];
                faceColors[2] = faceColors[0];
                faceColors[0] = temp;
                break;
        }
        return faceColors;
    }

    // this method prints all the relevant information about the cubelet
    void output() {
        System.out.println("Position: " + position);
        System.out.println("Cubelet number: " + number);
        System.out.println("originalNumber: " + originalNumber);
        System.out.print("Cubelet faces: ");
        for (String s : translator.getCubelet(originalNumber)) {
            System.out.print(s);
        }
        System.out.println("");
        System.out.println("Orientation: " + orientation);
        System.out.println("COLOR FACING UP: " + translator.getFace(originalNumber, orientation));
    }
}
