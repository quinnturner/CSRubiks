package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This is an object that translates between the numbers that Solver, Cube, and Cubelet uses
 * and the letters and colors that have to eventually be output. It played a major role in debugging, since
 * it acts as the text-based interpretation of the cube
 */
public class Translator {

    // The translation variant series represents the possible arrangemets of the 3 axes (0 = top/bottom axis, 1 = front/back axis, 2 = right/left axis
    // Axis: {0,1,2}
    static final String[][] translationVariant1 = {{"W", "O", "G"}, {"W", "O", "B"}, {"W", "R", "B"}, {"W", "R", "G"}, {"Y", "O", "G"}, {"Y", "O", "B"}, {"Y", "R", "B"}, {"Y", "R", "G"},
        {"W", "O"}, {"W", "B"}, {"W", "R"}, {"W", "G"}, {"O", "G"}, {"O", "B"}, {"R", "B"}, {"R", "G"}, {"Y", "O"}, {"Y", "B"}, {"Y", "R"}, {"Y", "G"}};
    // Axis: {0,2,1}
    static final String[][] translationVariant2 = {{"W", "G", "O"}, {"W", "B", "O"}, {"W", "B", "R"}, {"W", "G", "R"}, {"Y", "G", "O"}, {"Y", "B", "O"}, {"Y", "B", "R"}, {"Y", "G", "R"},
        {"W", "O"}, {"W", "B"}, {"W", "R"}, {"W", "G"}, {"G", "O"}, {"B", "O"}, {"B", "R"}, {"G", "R"}, {"Y", "O"}, {"Y", "B"}, {"Y", "R"}, {"Y", "G"}};
    // Axis: {1,0,2}
    static final String[][] translationVariant3 = {{"O", "W", "G"}, {"O", "W", "B"}, {"R", "W", "B"}, {"R", "W", "G"}, {"O", "Y", "G"}, {"O", "Y", "B"}, {"R", "Y", "B"}, {"R", "Y", "G"},
        {"O", "W"}, {"W", "B"}, {"R", "W"}, {"W", "G"}, {"O", "G"}, {"O", "B"}, {"R", "B"}, {"R", "G"}, {"O", "Y"}, {"Y", "B"}, {"R", "Y"}, {"Y", "G"}};
    // Axis: {1,2,0}
    static final String[][] translationVariant4 = {{"O", "G", "W"}, {"O", "B", "W"}, {"R", "B", "W"}, {"R", "G", "W"}, {"O", "G", "Y"}, {"O", "B", "Y"}, {"R", "B", "Y"}, {"R", "G", "Y"},
        {"O", "W"}, {"B", "W"}, {"R", "W"}, {"G", "W"}, {"O", "G"}, {"O", "B"}, {"R", "B"}, {"R", "G"}, {"O", "Y"}, {"B", "Y"}, {"R", "Y"}, {"G", "Y"}};
    // Axis: {2,0,1}
    static final String[][] translationVariant5 = {{"G", "W", "O"}, {"B", "W", "O"}, {"B", "W", "R"}, {"G", "W", "R"}, {"G", "Y", "O"}, {"B", "Y", "O"}, {"B", "Y", "R"}, {"G", "Y", "R"},
        {"W", "O"}, {"B", "W"}, {"W", "R"}, {"G", "W"}, {"G", "O"}, {"B", "O"}, {"B", "R"}, {"G", "R"}, {"Y", "O"}, {"B", "Y"}, {"Y", "R"}, {"G", "Y"}};
    // Axis: {2,1,0}
    static final String[][] translationVariant6 = {{"G", "O", "W"}, {"B", "O", "W"}, {"B", "R", "W"}, {"G", "R", "W"}, {"G", "O", "Y"}, {"B", "O", "Y"}, {"B", "R", "Y"}, {"G", "R", "Y"},
        {"O", "W"}, {"B", "W"}, {"R", "W"}, {"G", "W"}, {"G", "O"}, {"B", "O"}, {"B", "R"}, {"G", "R"}, {"O", "Y"}, {"B", "Y"}, {"R", "Y"}, {"G", "Y"}};
    // translation holds the currently selected translationVarient
    String[][] translation;
    // axis lets the program know which axis is facing which way, which affects the orientation of the cubelets
    // W-Y = 0, R-O = 1, B-G = 2
    int[] axes;
    // first index refers to axis, second index refers to it's precedence in NSS (W before Y, etc.), element refers to the face
    String[][] Hierarchy;
    // index of 0 = top, 1 = bottom, 2 = front, 3 = back, 4 = right, 5 = left
    // element refers to color (0,1,2,3,4,5) meaning (w,y,r,o,b,g)
    int[] faces = {0, 1, 2, 3, 4, 5};

    // creates the default cube in NSS
    Translator() {
        translation = translationVariant1.clone();
        int[] NSSAxes = {0, 1, 2};
        this.axes = NSSAxes;
        String[][] NSSHierarchy = {{"W", "Y"}, {"R", "O"}, {"B", "G"}};
        this.Hierarchy = NSSHierarchy;
        int[] NSSFaces = {0, 1, 2, 3, 4, 5};
        this.faces = NSSFaces;
    }

    // this clones and breaks the referencing between methods, allowing the cloning of the instance
    Translator(Translator translator) {
        this.translation = translator.translation;
        this.axes = translator.axes;
        this.Hierarchy = translator.Hierarchy;
        this.faces = translator.faces.clone();
    }

    // returns the color that is currently facing up of a given cubelet at a given orientation
    String getFace(int number, int orientation) {
        return translation[number][orientation];
    }

    // returns the array holding all the faces of the given cubelet
    String[] getCubelet(int number) {
        return translation[number];
    }

    // tells the translator to rotate, thus taking on a new set of translations
    void faceChange(int rotationType) {
        // determines how to reorder axes and faces based on the rotation type
        int[] newAxesOrder = {};
        int[] newFaceOrder = {};
        switch (rotationType) {
            case 0:
                int[] tempAxesOrder0 = {1, 0, 2};
                newAxesOrder = tempAxesOrder0;
                int[] tempFaceOrder0 = {2, 3, 1, 0, 4, 5};
                newFaceOrder = tempFaceOrder0;
                break;
            case 1:
                int[] tempAxesOrder1 = {0, 2, 1};
                newAxesOrder = tempAxesOrder1;
                int[] tempFaceOrder1 = {0, 1, 4, 5, 3, 2};
                newFaceOrder = tempFaceOrder1;
                break;
            case 2:
                int[] tempAxesOrder2 = {2, 1, 0};
                newAxesOrder = tempAxesOrder2;
                int[] tempFaceOrder2 = {5, 4, 2, 3, 0, 1};
                newFaceOrder = tempFaceOrder2;
                break;
        }

        // the newOrder arrays contains the new positions of the various axes and faces
        // it is applied in the same way that cubelet changes are applied
        int[] facesCopy = faces.clone();
        for (int i = 0; i < 6; i++) {
            faces[i] = facesCopy[newFaceOrder[i]];
        }
        int[] tempAxes = axes.clone();
        for (int i = 0; i < 3; i++) {
            axes[i] = tempAxes[newAxesOrder[i]];
        }

        // based on the new positions of the axes, a new translationVariant is chosen
        switch (axes[0]) {
            case 0:
                translation = axes[1] == 1 ? translationVariant1.clone() : translationVariant2.clone();
                break;
            case 1:
                translation = axes[1] == 0 ? translationVariant3.clone() : translationVariant4.clone();
                break;
            case 2:
                translation = axes[1] == 0 ? translationVariant5.clone() : translationVariant6.clone();
                break;
        }

        // changes hierarchy around to match the new axes
        String[][] temp = Hierarchy.clone();
        for (int i = 0; i < 3; i++) {
            Hierarchy[i] = temp[newAxesOrder[i]].clone();
        }
    }

    // this method sets a new translator, and also returns if the cube is inPhase or out of phase
    int setTranslator(int[] newAxes, String[] faceColors) {
        // this loop derives the 6 elements of faces based on the 3 of faceColors
        for (int i = 0; i < 3; i++) {
            switch (newAxes[i]) {
                case 0:
                    if (faceColors[i].equalsIgnoreCase("w")) {
                        faces[i * 2] = 0;
                        faces[i * 2 + 1] = 1;
                    } else {
                        faces[i * 2] = 1;
                        faces[i * 2 + 1] = 0;
                    }
                    break;
                case 1:
                    if (faceColors[i].equalsIgnoreCase("r")) {
                        faces[i * 2] = 2;
                        faces[i * 2 + 1] = 3;
                    } else {
                        faces[i * 2] = 3;
                        faces[i * 2 + 1] = 2;
                    }
                    break;
                case 2:
                    if (faceColors[i].equalsIgnoreCase("b")) {
                        faces[i * 2] = 4;
                        faces[i * 2 + 1] = 5;
                    } else {
                        faces[i * 2] = 5;
                        faces[i * 2 + 1] = 4;
                    }
                    break;
            }
        }
        // updates axes to the new one
        axes = newAxes;

        // updates hierarchy based on the new axes
        String[][] temp = {{"W", "Y"}, {"R", "O"}, {"B", "G"}};
        for (int i = 0; i < 3; i++) {
            Hierarchy[i] = temp[axes[i]].clone();
        }
        // selects a translationVariant based on the new axes
        switch (axes[0]) {
            case 0:
                translation = axes[1] == 1 ? translationVariant1.clone() : translationVariant2.clone();
                return (axes[1] == 1 ? 1 : 2) - 1;
            case 1:
                translation = axes[1] == 0 ? translationVariant3.clone() : translationVariant4.clone();
                return (axes[1] == 0 ? 3 : 4) - 1;
            case 2:
                translation = axes[1] == 0 ? translationVariant5.clone() : translationVariant6.clone();
                return (axes[1] == 0 ? 5 : 6) - 1;
        }
        return -1;

    }
}
