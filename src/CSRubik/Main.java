package CSRubik;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cube c = new Cube();
        c.B();
        SolverNewBroken s = new SolverNewBroken(c);
        s.checkCross(s.c);
        // s.getCrossMoves(1, 8, s.c);
        s.c.printFaces();
    }
}
