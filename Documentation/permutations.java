/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Henry
 */
public class permutations {

    public static int[] swap(int[] i, int place) {
        int temp = i[place];
        i[place] = i[place + 1];
        i[place + 1] = temp;
        return i;
    }

    public static int factorial(int i) {
        int f = 1;
        for (int multiply = 2; multiply <= i; multiply++) {
            f *= multiply;
        }

        return f;
    }

    public static void main(String[] args) {
        int number = 5;
        int[][] master = new int[factorial(number)][number];
        int[] temp = {1, 2, 3, 4, 5};

        master[0] = temp;
        int place = 1;
        for (int p2 = 0; p2 < 5; p2++) {
            System.out.print(master[0][p2]);
        }
        System.out.println("");
        for (int stage1 = 0; stage1 < 5; stage1++) {
            for (int stage2 = 0; stage2 < 4; stage2++) {
                for (int stage3 = 0; stage3 < 3; stage3++) {
                    temp = master[place - 1];
                    master[place] = swap(master[place - 1], 3);
                    master[place - 1] = temp;
                    for (int p2 = 0; p2 < 5; p2++) {
                        System.out.print(master[place][p2]);
                    }
                    System.out.println("");
                    place++;


                    temp = master[place - 1];
                    master[place] = swap(master[place - 1], 2);
                    master[place - 1] = temp;
                    for (int p2 = 0; p2 < 5; p2++) {
                        System.out.print(master[place][p2]);
                    }
                    System.out.println("");
                    place++;
                }
                place--;
                temp = master[place - 1];
                master[place] = swap(master[place - 1], 1);
                master[place - 1] = temp;
                for (int p2 = 0; p2 < 5; p2++) {
                    System.out.print(master[place][p2]);
                }
                System.out.println("");
                place++;
            }
            place--;
            temp = master[place - 1];
            master[place] = swap(master[place - 1], 0);
            master[place - 1] = temp;
            for (int p2 = 0; p2 < 5; p2++) {
                System.out.print(master[place][p2]);
            }
            System.out.println("");
            place++;
        }

        for (int p1 = 0; p1 < 120; p1++) {
            for (int p2 = 0; p2 < 5; p2++) {
                System.out.print(master[p1][p2]);
            }
            System.out.println("");
        }

    }
}
