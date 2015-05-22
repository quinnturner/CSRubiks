package CSRubik;

/* Date: Wednesday, June 19, 2013
 * Written by: Henry Han and Quinn Turner
 * Description: This is the frame that opens when you press moves on frameform.
 * It allows the user to select moves either by typing them in, or by clicking buttons.
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Moves extends javax.swing.JFrame {

    String addedMoveList = "";
    String moveList = "";
    CubeLogic logic;
    FrameForm parent;

    public Moves(java.awt.Frame parent, boolean modal) {
        super("Moves");
        this.parent = (FrameForm) parent;
        // Sets the location of the JDialog and the size
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // Adds a move to the moveList
    public void addToMoveList(String s) {
        moveList = TextManipulator.fixSpacing((moveList + s + " ").trim());
    }

    // Checks if only one move is left in the list, then erases list.
    // Otherwise, erase the last move
    public void undoFromMoveList() {
        if (moveList.replaceAll(" ", "").replaceAll("'", "").replaceAll("2", "").length() <= 1) {
            moveList = "";
        } else {
            moveList = moveList.substring(0, moveList.lastIndexOf(" ", moveList.length() - 2)) + " ";
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logic = CubeLogic.getCurrentInstance();
        this.setBounds(200, 434, 320, 365);
        moveU = new javax.swing.JButton();
        moveUPrime = new javax.swing.JButton();
        moveUSquared = new javax.swing.JButton();
        moveD = new javax.swing.JButton();
        moveDPrime = new javax.swing.JButton();
        moveDSquared = new javax.swing.JButton();
        moveF = new javax.swing.JButton();
        moveFPrime = new javax.swing.JButton();
        moveFSquared = new javax.swing.JButton();
        moveB = new javax.swing.JButton();
        moveBPrime = new javax.swing.JButton();
        moveBSquared = new javax.swing.JButton();
        moveL = new javax.swing.JButton();
        moveLPrime = new javax.swing.JButton();
        moveLSquared = new javax.swing.JButton();
        moveR = new javax.swing.JButton();
        moveRPrime = new javax.swing.JButton();
        moveRSquared = new javax.swing.JButton();
        rotateX = new javax.swing.JButton();
        rotateXPrime = new javax.swing.JButton();
        rotateXSquared = new javax.swing.JButton();
        rotateY = new javax.swing.JButton();
        rotateYPrime = new javax.swing.JButton();
        rotateYSquared = new javax.swing.JButton();
        rotateZ = new javax.swing.JButton();
        rotateZPrime = new javax.swing.JButton();
        rotateZSquared = new javax.swing.JButton();
        submit = new javax.swing.JButton();
        undoMove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaDialog = new javax.swing.JTextArea();
        inputField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setMinimumSize(new java.awt.Dimension(320, 365));
        setName("Move Buttons"); // NOI18N
        setResizable(false);

        moveU.setText("U");
        moveU.setFocusCycleRoot(true);
        moveU.setFocusTraversalPolicyProvider(true);
        moveU.setMaximumSize(null);
        moveU.setMinimumSize(null);
        moveU.setName("Move Buttons"); // NOI18N
        moveU.setPreferredSize(new java.awt.Dimension(45, 35));
        moveU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUActionPerformed(evt);
            }
        });

        moveUPrime.setText("U'");
        moveUPrime.setFocusCycleRoot(true);
        moveUPrime.setFocusTraversalPolicyProvider(true);
        moveUPrime.setMaximumSize(null);
        moveUPrime.setMinimumSize(null);
        moveUPrime.setName("Move Buttons"); // NOI18N
        moveUPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveUPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUPrimeActionPerformed(evt);
            }
        });

        moveUSquared.setText("U2");
        moveUSquared.setFocusCycleRoot(true);
        moveUSquared.setFocusTraversalPolicyProvider(true);
        moveUSquared.setMaximumSize(null);
        moveUSquared.setMinimumSize(null);
        moveUSquared.setName("Move Buttons"); // NOI18N
        moveUSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveUSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUSquaredActionPerformed(evt);
            }
        });

        moveD.setText("D");
        moveD.setFocusCycleRoot(true);
        moveD.setFocusTraversalPolicyProvider(true);
        moveD.setMaximumSize(null);
        moveD.setMinimumSize(null);
        moveD.setName("Move Buttons"); // NOI18N
        moveD.setPreferredSize(new java.awt.Dimension(45, 35));
        moveD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDActionPerformed(evt);
            }
        });

        moveDPrime.setText("D'");
        moveDPrime.setFocusCycleRoot(true);
        moveDPrime.setFocusTraversalPolicyProvider(true);
        moveDPrime.setMaximumSize(null);
        moveDPrime.setMinimumSize(null);
        moveDPrime.setName("Move Buttons"); // NOI18N
        moveDPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveDPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDPrimeActionPerformed(evt);
            }
        });

        moveDSquared.setText("D2");
        moveDSquared.setFocusCycleRoot(true);
        moveDSquared.setFocusTraversalPolicyProvider(true);
        moveDSquared.setMaximumSize(null);
        moveDSquared.setMinimumSize(null);
        moveDSquared.setName("Move Buttons"); // NOI18N
        moveDSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveDSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDSquaredActionPerformed(evt);
            }
        });

        moveF.setText("F");
        moveF.setFocusCycleRoot(true);
        moveF.setFocusTraversalPolicyProvider(true);
        moveF.setMaximumSize(null);
        moveF.setMinimumSize(null);
        moveF.setName("Move Buttons"); // NOI18N
        moveF.setPreferredSize(new java.awt.Dimension(45, 35));
        moveF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveFActionPerformed(evt);
            }
        });

        moveFPrime.setText("F'");
        moveFPrime.setFocusCycleRoot(true);
        moveFPrime.setFocusTraversalPolicyProvider(true);
        moveFPrime.setMaximumSize(null);
        moveFPrime.setMinimumSize(null);
        moveFPrime.setName("Move Buttons"); // NOI18N
        moveFPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveFPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveFPrimeActionPerformed(evt);
            }
        });

        moveFSquared.setText("F2");
        moveFSquared.setFocusCycleRoot(true);
        moveFSquared.setFocusTraversalPolicyProvider(true);
        moveFSquared.setMaximumSize(null);
        moveFSquared.setMinimumSize(null);
        moveFSquared.setName("Move Buttons"); // NOI18N
        moveFSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveFSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveFSquaredActionPerformed(evt);
            }
        });

        moveB.setText("B");
        moveB.setFocusCycleRoot(true);
        moveB.setFocusTraversalPolicyProvider(true);
        moveB.setMaximumSize(null);
        moveB.setMinimumSize(null);
        moveB.setName("Move Buttons"); // NOI18N
        moveB.setPreferredSize(new java.awt.Dimension(45, 35));
        moveB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveBActionPerformed(evt);
            }
        });

        moveBPrime.setText("B'");
        moveBPrime.setFocusCycleRoot(true);
        moveBPrime.setFocusTraversalPolicyProvider(true);
        moveBPrime.setMaximumSize(null);
        moveBPrime.setMinimumSize(null);
        moveBPrime.setName("Move Buttons"); // NOI18N
        moveBPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveBPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveBPrimeActionPerformed(evt);
            }
        });

        moveBSquared.setText("B2");
        moveBSquared.setFocusCycleRoot(true);
        moveBSquared.setFocusTraversalPolicyProvider(true);
        moveBSquared.setMaximumSize(null);
        moveBSquared.setMinimumSize(null);
        moveBSquared.setName("Move Buttons"); // NOI18N
        moveBSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveBSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveBSquaredActionPerformed(evt);
            }
        });

        moveL.setText("L");
        moveL.setFocusCycleRoot(true);
        moveL.setFocusTraversalPolicyProvider(true);
        moveL.setMaximumSize(null);
        moveL.setMinimumSize(null);
        moveL.setName("Move Buttons"); // NOI18N
        moveL.setPreferredSize(new java.awt.Dimension(45, 35));
        moveL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLActionPerformed(evt);
            }
        });

        moveLPrime.setText("L'");
        moveLPrime.setFocusCycleRoot(true);
        moveLPrime.setFocusTraversalPolicyProvider(true);
        moveLPrime.setMaximumSize(null);
        moveLPrime.setMinimumSize(null);
        moveLPrime.setName("Move Buttons"); // NOI18N
        moveLPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveLPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLPrimeActionPerformed(evt);
            }
        });

        moveLSquared.setText("L2");
        moveLSquared.setFocusCycleRoot(true);
        moveLSquared.setFocusTraversalPolicyProvider(true);
        moveLSquared.setMaximumSize(null);
        moveLSquared.setMinimumSize(null);
        moveLSquared.setName("Move Buttons"); // NOI18N
        moveLSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveLSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLSquaredActionPerformed(evt);
            }
        });

        moveR.setText("R");
        moveR.setFocusCycleRoot(true);
        moveR.setFocusTraversalPolicyProvider(true);
        moveR.setMaximumSize(null);
        moveR.setMinimumSize(null);
        moveR.setName("Move Buttons"); // NOI18N
        moveR.setPreferredSize(new java.awt.Dimension(45, 35));
        moveR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRActionPerformed(evt);
            }
        });

        moveRPrime.setText("R'");
        moveRPrime.setFocusCycleRoot(true);
        moveRPrime.setFocusTraversalPolicyProvider(true);
        moveRPrime.setMaximumSize(null);
        moveRPrime.setMinimumSize(null);
        moveRPrime.setName("Move Buttons"); // NOI18N
        moveRPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        moveRPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRPrimeActionPerformed(evt);
            }
        });

        moveRSquared.setText("R2");
        moveRSquared.setFocusCycleRoot(true);
        moveRSquared.setFocusTraversalPolicyProvider(true);
        moveRSquared.setMaximumSize(null);
        moveRSquared.setMinimumSize(null);
        moveRSquared.setName("Move Buttons"); // NOI18N
        moveRSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        moveRSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRSquaredActionPerformed(evt);
            }
        });

        rotateX.setText("x");
        rotateX.setFocusCycleRoot(true);
        rotateX.setFocusTraversalPolicyProvider(true);
        rotateX.setMaximumSize(null);
        rotateX.setMinimumSize(null);
        rotateX.setName("Move Buttons"); // NOI18N
        rotateX.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateXActionPerformed(evt);
            }
        });

        rotateXPrime.setText("x'");
        rotateXPrime.setFocusCycleRoot(true);
        rotateXPrime.setFocusTraversalPolicyProvider(true);
        rotateXPrime.setMaximumSize(null);
        rotateXPrime.setMinimumSize(null);
        rotateXPrime.setName("Move Buttons"); // NOI18N
        rotateXPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateXPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateXPrimeActionPerformed(evt);
            }
        });

        rotateXSquared.setText("x2");
        rotateXSquared.setFocusCycleRoot(true);
        rotateXSquared.setFocusTraversalPolicyProvider(true);
        rotateXSquared.setMaximumSize(null);
        rotateXSquared.setMinimumSize(null);
        rotateXSquared.setName("Move Buttons"); // NOI18N
        rotateXSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateXSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateXSquaredActionPerformed(evt);
            }
        });

        rotateY.setText("y");
        rotateY.setFocusCycleRoot(true);
        rotateY.setFocusTraversalPolicyProvider(true);
        rotateY.setMaximumSize(null);
        rotateY.setMinimumSize(null);
        rotateY.setName("Move Buttons"); // NOI18N
        rotateY.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateYActionPerformed(evt);
            }
        });

        rotateYPrime.setText("y'");
        rotateYPrime.setFocusCycleRoot(true);
        rotateYPrime.setFocusTraversalPolicyProvider(true);
        rotateYPrime.setMaximumSize(null);
        rotateYPrime.setMinimumSize(null);
        rotateYPrime.setName("Move Buttons"); // NOI18N
        rotateYPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateYPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateYPrimeActionPerformed(evt);
            }
        });

        rotateYSquared.setText("y2");
        rotateYSquared.setFocusCycleRoot(true);
        rotateYSquared.setFocusTraversalPolicyProvider(true);
        rotateYSquared.setMaximumSize(null);
        rotateYSquared.setMinimumSize(null);
        rotateYSquared.setName("Move Buttons"); // NOI18N
        rotateYSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateYSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateYSquaredActionPerformed(evt);
            }
        });

        rotateZ.setText("z");
        rotateZ.setFocusCycleRoot(true);
        rotateZ.setFocusTraversalPolicyProvider(true);
        rotateZ.setMaximumSize(null);
        rotateZ.setMinimumSize(null);
        rotateZ.setName("Move Buttons"); // NOI18N
        rotateZ.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateZActionPerformed(evt);
            }
        });

        rotateZPrime.setText("z'");
        rotateZPrime.setFocusCycleRoot(true);
        rotateZPrime.setFocusTraversalPolicyProvider(true);
        rotateZPrime.setMaximumSize(null);
        rotateZPrime.setMinimumSize(null);
        rotateZPrime.setName("Move Buttons"); // NOI18N
        rotateZPrime.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateZPrime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateZPrimeActionPerformed(evt);
            }
        });

        rotateZSquared.setText("z2");
        rotateZSquared.setFocusCycleRoot(true);
        rotateZSquared.setFocusTraversalPolicyProvider(true);
        rotateZSquared.setMaximumSize(null);
        rotateZSquared.setMinimumSize(null);
        rotateZSquared.setName("Move Buttons"); // NOI18N
        rotateZSquared.setPreferredSize(new java.awt.Dimension(45, 35));
        rotateZSquared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateZSquaredActionPerformed(evt);
            }
        });

        submit.setText("SUBMIT");
        submit.setAutoscrolls(true);
        submit.setPreferredSize(new java.awt.Dimension(69, 35));
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        undoMove.setText("UNDO");
        undoMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMoveActionPerformed(evt);
            }
        });

        textAreaDialog.setEditable(false);
        textAreaDialog.setColumns(20);
        textAreaDialog.setRows(5);
        jScrollPane1.setViewportView(textAreaDialog);
        BufferedImage image = null;
        try {
            File imageFile = new File("Resources//cube.png");
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
        }
        this.setIconImage(image);

        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Input moves");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(moveU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moveUPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moveUSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moveD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moveDPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(moveDSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(moveL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(moveF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(moveFPrime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(moveLPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(moveFSquared, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(moveLSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(moveB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moveBPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moveBSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(moveR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moveRPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moveRSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(rotateZ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rotateX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(rotateXPrime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rotateZPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(rotateXSquared, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rotateZSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rotateY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rotateYPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rotateYSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(undoMove)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel1)
                        .addGap(0, 126, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveUPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveUSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveDPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveDSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveFPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveFSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveBPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveBSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveLPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveLSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveRPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moveRSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotateX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateXPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateXSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateYPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateYSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotateZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateZPrime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotateZSquared, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(undoMove, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        moveU.getAccessibleContext().setAccessibleName("");
        moveUPrime.getAccessibleContext().setAccessibleName("");
        moveUSquared.getAccessibleContext().setAccessibleName("");
        moveD.getAccessibleContext().setAccessibleName("");
        moveDPrime.getAccessibleContext().setAccessibleName("");
        moveDSquared.getAccessibleContext().setAccessibleName("");
        moveF.getAccessibleContext().setAccessibleName("");
        moveFPrime.getAccessibleContext().setAccessibleName("");
        moveFSquared.getAccessibleContext().setAccessibleName("");
        moveB.getAccessibleContext().setAccessibleName("");
        moveBPrime.getAccessibleContext().setAccessibleName("");
        moveBSquared.getAccessibleContext().setAccessibleName("");
        moveL.getAccessibleContext().setAccessibleName("");
        moveLPrime.getAccessibleContext().setAccessibleName("");
        moveLSquared.getAccessibleContext().setAccessibleName("");
        moveR.getAccessibleContext().setAccessibleName("");
        moveRPrime.getAccessibleContext().setAccessibleName("");
        moveRSquared.getAccessibleContext().setAccessibleName("");
        rotateX.getAccessibleContext().setAccessibleName("");
        rotateXPrime.getAccessibleContext().setAccessibleName("");
        rotateXSquared.getAccessibleContext().setAccessibleName("");
        rotateY.getAccessibleContext().setAccessibleName("");
        rotateYPrime.getAccessibleContext().setAccessibleName("");
        rotateYSquared.getAccessibleContext().setAccessibleName("");
        rotateZ.getAccessibleContext().setAccessibleName("");
        rotateZPrime.getAccessibleContext().setAccessibleName("");
        rotateZSquared.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
// All the buttons for moving the cube
    private void moveFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveFActionPerformed
        addToMoveList("F");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveFActionPerformed

    private void moveUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUActionPerformed
        addToMoveList("U");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveUActionPerformed

    private void moveUPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUPrimeActionPerformed
        addToMoveList("U'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveUPrimeActionPerformed

    private void moveUSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUSquaredActionPerformed
        addToMoveList("U2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveUSquaredActionPerformed

    private void moveDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDActionPerformed
        addToMoveList("D");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveDActionPerformed

    private void moveDPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDPrimeActionPerformed
        addToMoveList("D'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveDPrimeActionPerformed

    private void moveDSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDSquaredActionPerformed
        addToMoveList("D2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveDSquaredActionPerformed

    private void moveFPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveFPrimeActionPerformed
        addToMoveList("F'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveFPrimeActionPerformed

    private void moveFSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveFSquaredActionPerformed
        addToMoveList("F2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveFSquaredActionPerformed

    private void moveBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveBActionPerformed
        addToMoveList("B");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveBActionPerformed

    private void moveBPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveBPrimeActionPerformed
        addToMoveList("B'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveBPrimeActionPerformed

    private void moveBSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveBSquaredActionPerformed
        addToMoveList("B2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveBSquaredActionPerformed

    private void moveLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveLActionPerformed
        addToMoveList("L");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveLActionPerformed

    private void moveLPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveLPrimeActionPerformed
        addToMoveList("L'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveLPrimeActionPerformed

    private void moveLSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveLSquaredActionPerformed
        addToMoveList("L'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveLSquaredActionPerformed

    private void moveRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRActionPerformed
        addToMoveList("R");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveRActionPerformed

    private void moveRPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRPrimeActionPerformed
        addToMoveList("R'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveRPrimeActionPerformed

    private void moveRSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRSquaredActionPerformed
        addToMoveList("R2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_moveRSquaredActionPerformed

    private void rotateXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateXActionPerformed
        addToMoveList("x");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateXActionPerformed

    private void rotateXPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateXPrimeActionPerformed
        addToMoveList("x'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateXPrimeActionPerformed

    private void rotateXSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateXSquaredActionPerformed
        addToMoveList("x2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateXSquaredActionPerformed

    private void rotateYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateYActionPerformed
        addToMoveList("y");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateYActionPerformed

    private void rotateYPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateYPrimeActionPerformed
        addToMoveList("y'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateYPrimeActionPerformed

    private void rotateYSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateYSquaredActionPerformed
        addToMoveList("y2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateYSquaredActionPerformed

    private void rotateZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateZActionPerformed
        addToMoveList("z");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateZActionPerformed

    private void rotateZPrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateZPrimeActionPerformed
        addToMoveList("z'");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateZPrimeActionPerformed

    private void rotateZSquaredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateZSquaredActionPerformed
        addToMoveList("z2");
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_rotateZSquaredActionPerformed

    private void undoMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMoveActionPerformed
        undoFromMoveList();
        textAreaDialog.setText(moveList);
    }//GEN-LAST:event_undoMoveActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        logic.setMoves(moveList);
        parent.updateMoves();
        setVisible(false);
    }//GEN-LAST:event_submitActionPerformed

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        String text = inputField.getText();
        if (text.length() == 0) {
        } else if (TextManipulator.movesValid(text)) {
            addToMoveList(text);
            textAreaDialog.setText(moveList);
        } else {
            inputField.setText("Invalid Moves");
        }
        inputField.selectAll();
    }//GEN-LAST:event_inputFieldActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;










                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Moves.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Moves.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Moves.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Moves.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Moves dialog = new Moves(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);


            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton moveB;
    private javax.swing.JButton moveBPrime;
    private javax.swing.JButton moveBSquared;
    private javax.swing.JButton moveD;
    private javax.swing.JButton moveDPrime;
    private javax.swing.JButton moveDSquared;
    private javax.swing.JButton moveF;
    private javax.swing.JButton moveFPrime;
    private javax.swing.JButton moveFSquared;
    private javax.swing.JButton moveL;
    private javax.swing.JButton moveLPrime;
    private javax.swing.JButton moveLSquared;
    private javax.swing.JButton moveR;
    private javax.swing.JButton moveRPrime;
    private javax.swing.JButton moveRSquared;
    private javax.swing.JButton moveU;
    private javax.swing.JButton moveUPrime;
    private javax.swing.JButton moveUSquared;
    private javax.swing.JButton rotateX;
    private javax.swing.JButton rotateXPrime;
    private javax.swing.JButton rotateXSquared;
    private javax.swing.JButton rotateY;
    private javax.swing.JButton rotateYPrime;
    private javax.swing.JButton rotateYSquared;
    private javax.swing.JButton rotateZ;
    private javax.swing.JButton rotateZPrime;
    private javax.swing.JButton rotateZSquared;
    private javax.swing.JButton submit;
    private javax.swing.JTextArea textAreaDialog;
    private javax.swing.JButton undoMove;
    // End of variables declaration//GEN-END:variables
}