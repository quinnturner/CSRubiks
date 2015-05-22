package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This panel's only purpose is to draw the cube. It reads colors off of 
 * CubeLogic, and matches them with the pre-programmed positional data to draw colored polygons
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CubeJPanel extends javax.swing.JPanel implements MouseListener {

    // white, yellow, red, orange, blue, green, grey
    final static Color[] colors = {Color.white, Color.yellow, Color.red, new Color(255, 128, 0), new Color(0, 10, 225), new Color(0, 153, 76), new Color(160, 160, 160)};
    // positional information for drawing the facelets
    final static int panelWidth = 350;
    final static int panelHeight = 500;
    final static int[][] xCoordinateOffsets = {{40, 54, 14}, {40, 40, 0}, {14, 14, 0}};
    final static int[][] yCoordinateOffsets = {{0, -14, -14}, {0, 40, 40}, {-14, 26, 40}};
    static final int baseX = 135;
    static final int baseY = 120;
    static final int horizontalShift = 43;
    static final int diagonalShift = 16;
    static final int xBackOffset = 190;
    static final int yBackOffset = -100;
    static final int yBottomOffset = 200;
    static final int xLeftOffset = -200;
    //  centers  final static int[][][] positionData = {{{top}, {bottom}, {front}, {back}, {right}, {left}},
    //  corners  {cubelet number-facelet number}    {{0-0}, {0-1}, {0-2}}, {{1-0}, {1-1}, {1-2}}, {{2-0}, {2-1}, {2-2}}, {{3-0}, {3-1}, {3-2}}, {{4-0}, {4-1}, {4-2}}, {{5-0}, {5-1}, {5-2}}, {{6-0}, {6-1}, {6-2}}, {{7-0}, {7-1}, {7-2}},
    //  edges    {{8-0}, {8-1}}, {{9-0}, {9-1}}, {{10-0}, {10-1}}, {{11-0}, {11-1}}, {{12-0}, {12-1}}, {{13-0}, {13-1}}, {{14-0}, {14-1}}, {{15-0}, {15-1}}, {{16-0}, {16-1}}, {{17-0}, {17-1}}, {{18-0}, {18-1}}, {{19-0}, {19-1}}};
    final static int[][][] positionData = {{{baseX + diagonalShift + horizontalShift, baseY - 3 - diagonalShift, 0}, {baseX + diagonalShift + horizontalShift, baseY - 3 + yBottomOffset - diagonalShift, 0},
            {baseX + horizontalShift, baseY + horizontalShift, 1,}, {baseX + xBackOffset + horizontalShift, baseY + yBackOffset + horizontalShift, 1},
            {baseX + horizontalShift * 3 + diagonalShift, baseY - 1 + horizontalShift - diagonalShift, 2}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift, baseY - 1 + horizontalShift - diagonalShift, 2}},
        {{baseX + diagonalShift * 2, baseY - 3 - diagonalShift * 2, 0}, {baseX + xBackOffset, baseY + yBackOffset, 1}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift * 2, baseY - 1 - diagonalShift * 2, 2}},
        {{baseX + diagonalShift * 2 + horizontalShift * 2, baseY - 3 - diagonalShift * 2, 0}, {baseX + xBackOffset + horizontalShift * 2, baseY + yBackOffset, 1}, {baseX + horizontalShift * 3 + diagonalShift * 2, baseY - 1 - diagonalShift * 2, 2}},
        {{baseX + horizontalShift * 2, baseY - 3, 0}, {baseX + horizontalShift * 2, baseY, 1}, {baseX + horizontalShift * 3, baseY - 1, 2}},
        {{baseX, baseY - 3, 0}, {baseX, baseY, 1}, {baseX + horizontalShift * 3 + xLeftOffset, baseY - 1, 2}},
        {{baseX + diagonalShift * 2, baseY - 3 + yBottomOffset - diagonalShift * 2, 0}, {baseX + xBackOffset, baseY + yBackOffset + horizontalShift * 2, 1}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift * 2, baseY - 1 + horizontalShift * 2 - diagonalShift * 2, 2}},
        {{baseX + diagonalShift * 2 + horizontalShift * 2, baseY - 3 + yBottomOffset - diagonalShift * 2, 0}, {baseX + xBackOffset + horizontalShift * 2, baseY + yBackOffset + horizontalShift * 2, 1}, {baseX + horizontalShift * 3 + diagonalShift * 2, baseY - 1 + horizontalShift * 2 - diagonalShift * 2, 2}},
        {{baseX + horizontalShift * 2, baseY - 3 + yBottomOffset, 0}, {baseX + horizontalShift * 2, baseY + horizontalShift * 2, 1}, {baseX + horizontalShift * 3, baseY - 1 + horizontalShift * 2, 2}},
        {{baseX, baseY - 3 + yBottomOffset, 0}, {baseX, baseY + horizontalShift * 2, 1}, {baseX + horizontalShift * 3 + xLeftOffset, baseY - 1 + horizontalShift * 2, 2}},
        {{baseX + diagonalShift * 2 + horizontalShift, baseY - 3 - diagonalShift * 2, 0}, {baseX + xBackOffset + horizontalShift, baseY + yBackOffset, 1}},
        {{baseX + diagonalShift + horizontalShift * 2, baseY - 3 - diagonalShift, 0}, {baseX + horizontalShift * 3 + diagonalShift, baseY - 1 - diagonalShift, 2}},
        {{baseX + horizontalShift, baseY - 3, 0}, {baseX + horizontalShift, baseY, 1}},
        {{baseX + diagonalShift, baseY - 3 - diagonalShift, 0}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift, baseY - 1 - diagonalShift, 2}},
        {{baseX + xBackOffset, baseY + yBackOffset + horizontalShift, 1}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift * 2, baseY - 1 + horizontalShift - diagonalShift * 2, 2}},
        {{baseX + xBackOffset + horizontalShift * 2, baseY + yBackOffset + horizontalShift, 1}, {baseX + horizontalShift * 3 + diagonalShift * 2, baseY - 1 + horizontalShift - diagonalShift * 2, 2}},
        {{baseX + horizontalShift * 2, baseY + horizontalShift, 1}, {baseX + horizontalShift * 3, baseY - 1 + horizontalShift, 2}},
        {{baseX, baseY + horizontalShift, 1}, {baseX + horizontalShift * 3 + xLeftOffset, baseY - 1 + horizontalShift, 2}},
        {{baseX + diagonalShift * 2 + horizontalShift, baseY - 3 + yBottomOffset - diagonalShift * 2, 0}, {baseX + xBackOffset + horizontalShift, baseY + yBackOffset + horizontalShift * 2, 1}},
        {{baseX + diagonalShift + horizontalShift * 2, baseY - 3 + yBottomOffset - diagonalShift, 0}, {baseX + horizontalShift * 3 + diagonalShift, baseY - 1 + horizontalShift * 2 - diagonalShift, 2}},
        {{baseX + horizontalShift, baseY - 3 + yBottomOffset, 0}, {baseX + horizontalShift, baseY + horizontalShift * 2, 1}},
        {{baseX + diagonalShift, baseY - 3 + yBottomOffset - diagonalShift, 0}, {baseX + horizontalShift * 3 + xLeftOffset + diagonalShift, baseY - 1 + horizontalShift * 2 - diagonalShift, 2}}};
    Polygon[] facelets = new Polygon[54];
    static CubeLogic logic;

    // constructs a new JPanel, along with assigning a logic
    public CubeJPanel() {
        setPreferredSize(new Dimension(panelHeight, panelWidth));
        this.setBackground(Color.white);
        addMouseListener(this);
        int faceletNumber = 0;
        // creates the polygons based on positional data
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < positionData[i].length; j++) {
                facelets[faceletNumber++] = createFacelet(positionData[i][j][0], positionData[i][j][1], positionData[i][j][2]);
            }
        }
        logic = CubeLogic.getCurrentInstance();
    }

    // helper method to create polygons
    private static Polygon createFacelet(int cornerX, int cornerY, int faceletType) {
        int[] xCoordinates = {cornerX, cornerX + xCoordinateOffsets[faceletType][0], cornerX + xCoordinateOffsets[faceletType][1], cornerX + xCoordinateOffsets[faceletType][2]};
        int[] yCoordinates = {cornerY, cornerY + yCoordinateOffsets[faceletType][0], cornerY + yCoordinateOffsets[faceletType][1], cornerY + yCoordinateOffsets[faceletType][2]};
        return new Polygon(xCoordinates, yCoordinates, 4);
    }

    // because the class implments mouselistener, these 4 methods are mandatory
    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    // upon mouse release, it take the mouse coordinates, and matches them with polygons to see if there's a match.
    // if there is, then the relevent element is changed in logic 
    public void mouseReleased(MouseEvent e) {
        Point releasePoint = e.getPoint();
        int faceletNumber = 0;
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < positionData[i].length; j++) {
                if (facelets[faceletNumber++].contains(releasePoint)) {
                    logic.setFaceletColor(i, j, logic.paintColor);
                }
            }
        }
        repaint();
    }

    // draws the polygons based on CubeLogic and the positionalData
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.BLACK);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < positionData[i].length; j++) {
                if (i == -1) {
                } else {
                    drawFacelet(positionData[i][j][0], positionData[i][j][1], positionData[i][j][2], logic.faceletColors[i][j], g2);
                }
            }
        }
    }

    // drawing polygon helper
    private void drawFacelet(int cornerX, int cornerY, int faceletType, int faceletColor, Graphics2D g2) {
        int[] xCoordinates = {cornerX, cornerX + xCoordinateOffsets[faceletType][0], cornerX + xCoordinateOffsets[faceletType][1], cornerX + xCoordinateOffsets[faceletType][2]};
        int[] yCoordinates = {cornerY, cornerY + yCoordinateOffsets[faceletType][0], cornerY + yCoordinateOffsets[faceletType][1], cornerY + yCoordinateOffsets[faceletType][2]};
        g2.setColor(colors[faceletColor]);
        g2.fillPolygon(xCoordinates, yCoordinates, 4);
        g2.setColor(Color.black);
//        g2.setStroke(new BasicStroke(2f));
        g2.drawPolygon(xCoordinates, yCoordinates, 4);
    }
}
