/***************************************************************************************
 *
 * NAME: Zachary Heth
 *
 * HOMEWORK: Group Project
 *
 * CLASS: ICS 211
 *
 * INSTRUCTOR: Scott Robertson
 *
 * DATE: May 4, 2016
 *
 * FILE: Cell.java
 *
 * DESCRIPTION: For each tile, there must be a east,west,south,north,northeast,southwest,southeast,northwest
                this is to keep track of bombs adjacent to itself
 ***************************************************************************************/
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.io.*;
public class Grid extends JFrame{

  Cell[][] board; //2D array to hold the cells
  JButton[][] buttons;
  private final long start = System.nanoTime();
  int size; //grid size;
  String level;
  JButton highscoreBtn = new JButton("High Scores");
  JButton menuBtn = new JButton("Back to Main Menu");
  JButton resetBtn = new JButton("Reset Squares");
  // JScrollPane scroll = new JScrollPane();
  /*********************************************************
  *
  * Method: Grid
  *
  * Description: Constructor, creates a grid, depending on if
  *
  * @param: String
  *
  * @return: None
  *
  *********************************************************/
  public Grid(String difficulty){
    Container cp = getContentPane();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    cp.setLayout(new FlowLayout());
    level =  difficulty;
    // cp.add(title);
    // cp.add(new Frame("Minesweeper "+difficulty+" Mode"));
    switch(difficulty){
      case "Easy":
        board = new Cell[10][10];
        buttons = new JButton[10][10];
        size = 10;
        createCells();
        createButtons();
        addButtons(cp);
        setAdjacentCells();
        addBombs(9);
        setSize(500,600);
      break;
      case "Medium":
        board = new Cell[15][15];
        buttons = new JButton[15][15];
        size = 15;
        createCells();
        createButtons();
        addButtons(cp);
        setAdjacentCells();
        addBombs(50);
        setSize(750,800);
      break;
      case "Hard":
        board = new Cell[20][20];
        buttons = new JButton[20][20];
        size = 20;
        createCells();
        createButtons();
        addButtons(cp);
        // scroll.setViewportView(resetBtn);
        // add(scroll);
        setAdjacentCells();
        addBombs(100);
        setSize(950,1100);
      break;
    }
    setVisible(true);
  }
  /*********************************************************
  *
  * Method: createCells
  *
  * Description: creates cells relative to size of grid
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void createCells(){
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
        board[i][j] = new Cell();
      }
    }
  }
  /*********************************************************
  *
  * Method: createButtons
  *
  * Description: creates buttons relative to size of grid
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void createButtons(){
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
        buttons[i][j] = new JButton();
        buttons[i][j].setBackground(Color.WHITE);
        actionListener(i, j);
      }
    }
  }
  /*********************************************************
  *
  * Method: addButtons
  *
  * Description: adds buttons to grid
  *
  * @param: Container
  *
  * @return: None
  *
  *********************************************************/
  public void addButtons(Container cp){
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
        buttons[i][j].setPreferredSize(new Dimension(41,41));
        cp.add(buttons[i][j],BorderLayout.CENTER);
      }
    }
    cp.add(menuBtn, BorderLayout.SOUTH);
    menuBtn.addActionListener(new ActionListener() {
           @Override
           /*********************************************************
           *
           * Method: actionPerformed
           *
           * Description: goes back to main menu in menuBtn is pressed
           *
           * @param: ActionEvent
           *
           * @return: None
           *
           *********************************************************/
           public void actionPerformed(ActionEvent evt) {
             setVisible(false);
             Minesweeper mine = new Minesweeper();
           }
        });
    cp.add(resetBtn, BorderLayout.NORTH);
    resetBtn.addActionListener(new ActionListener() {
      @Override
      /*********************************************************
      *
      * Method: setAdjacentCells
      *
      * Description: resets grid if resetBtn is pressed
      *
      * @param: ActionEvent
      *
      * @return: None
      *
      *********************************************************/
      public void actionPerformed(ActionEvent evt) {
        setVisible(false);
        Grid gridz = new Grid(level);
      }
    });
  }
  /*********************************************************
  *
  * Method: actionListener
  *
  * Description: listens for if buttons on grid are pressed
  *
  * @param: int, int
  *
  * @return: None
  *
  *********************************************************/
  public void actionListener(int column, int row){
    buttons[column][row].addActionListener(new ActionListener(){
      @Override
      /*********************************************************
      *
      * Method: actionPerformed
      *
      * Description: performs action after listener is triggered
      *
      * @param: ActionEvent
      *
      * @return: None
      *
      *********************************************************/
      public void actionPerformed(ActionEvent evt){
        if(buttons[column][row].getBackground() != Color.RED && buttons[column][row].getBackground() != Color.BLACK){
        reveal(column,row);
        }
      }
    });
    buttons[column][row].addMouseListener(new MouseAdapter(){
      @Override
      /*********************************************************
      *
      * Method: mouseClicked
      *
      * Description: checks if a button on grid has been pressed
      *
      * @param: MouseEvent
      *
      * @return: None
      *
      *********************************************************/
      public void mouseClicked(MouseEvent e){
        if(buttons[column][row].getBackground() != Color.RED && board[column][row].getHidden() == true){
          if(SwingUtilities.isRightMouseButton(e) && buttons[column][row].getBackground() != Color.BLACK){
            buttons[column][row].setBackground(Color.RED);
            buttons[column][row].setOpaque(true);
            checkForWin();
          }
        }
        else if(buttons[column][row].getBackground() == Color.RED ){
          if(SwingUtilities.isRightMouseButton(e)){
            buttons[column][row].setBackground(Color.WHITE);
            buttons[column][row].setOpaque(true);
          }
        }
      }
    });
  }
  /*********************************************************
  *
  * Method: addBombs
  *
  * Description: adds bombs to grid
  *
  * @param: int
  *
  * @return: None
  *
  *********************************************************/
  public void addBombs(int numOfBombs){
    int bombsAdded = 0;
    int row;
    int column;
    Random a = new Random();
    Random b = new Random();
    while(bombsAdded<numOfBombs){
      row = a.nextInt(size);
      column = b.nextInt(size);
      if(!board[column][row].getBomb()){//if the cell is not already a bomb...
        board[column][row].setBomb();
        bombsAdded++;
      }
    }
  }
  /*********************************************************
  *
  * Method: setBombCount
  *
  * Description: sets bomb count
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void setBombCount(){
    //after the cells have all been added and set
    //cells need to know which one of its adjacent squares are bombs
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
        board[i][j].getCount();
      }
    }
  }
  /*********************************************************
  *
  * Method: reveal
  *
  * Description: reveals tiles when clicked on
  *
  * @param: int, int
  *
  * @return: None
  *
  *********************************************************/
  public void reveal(int column, int row){
  //  System.out.println("Revealed "+column+" "+row);
    if(board[column][row].getBomb()){
        buttons[column][row].setLabel("B");
        buttons[column][row].setBackground(Color.YELLOW);
        checkForLoss();
      }
    else if(board[column][row].getCount()>0){
      revealNumber(column,row);
      checkForWin();
    }
    else{
      revealSpace(column, row);
      checkForWin();
    //  buttons[column][row].set("B");
    //  setVisible(true);
    }
  }
  /*********************************************************
  *
  * Method: checkForWin
  *
  * Description: checks for winning conditions
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void checkForWin(){
    int count =0;
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
          if(buttons[i][j].getBackground() != Color.RED && board[i][j].getHidden() == true){
            count++;
          }
        }
      }
      if(count ==0){
        JFrame cp1 = new JFrame("Minesweeper");
        cp1.setVisible(true);
        // cp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp1.setLayout(new BorderLayout());
        JButton name = new JButton("Submit name");
        JLabel label = new JLabel(
        "                                   You Won! Click New Game to start again. Write your name to save your highscore.");
        JTextField highscore = new JTextField("Enter name here.", 100);
        cp1.setSize(1000,1000);
        cp1.add(label, BorderLayout.CENTER);
        cp1.add(name, BorderLayout.SOUTH);
        cp1.add(highscore,BorderLayout.NORTH);
        long score = System.nanoTime() - start;
        name.addActionListener(new ActionListener() {
          @Override
      /*********************************************************
      *
      * Method: actionPerformed
      *
      * Description: listens for click on button
      *
      * @param: None
      *
      * @return: None
      *
      *********************************************************/
        public void actionPerformed(ActionEvent evt) {
          try{
            saveFile(highscore, score);
          }
          catch(Exception e){
            System.out.println("Check Files");
          }
          cp1.setVisible(false);
          // setVisible(false);
          Grid gridz = new Grid("Easy");
        }
      });
      }
    }
    /*********************************************************
    *
    * Method: setAdjacentCells
    *
    * Description: saves textField text to file
    *
    * @param: JTextField
    *
    * @return: None
    *
    *********************************************************/
    public static void saveFile(JTextField textField, long score) throws Exception{
      FileOutputStream out = new FileOutputStream("highscores.txt", true);
      out.write((textField.getText() + " " + (score/10000000)).getBytes());
    }
  /*********************************************************
  *
  * Method: checkForLoss
  *
  * Description: checks for loss
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void checkForLoss(){
    JFrame  cp1   = new JFrame("Minesweeper");
    JButton quit  = new JButton("New Game");
    JLabel  label = new JLabel("Game Over");

    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    cp1.setVisible(true);
    cp1.setLayout(new FlowLayout());
    cp1.setSize(300,300);
    cp1.add(label, BorderLayout.NORTH);
    cp1.add(quit);

    for ( int j = 0; j < size; j++ )
    {
      for ( int i = 0; i < size; i++)
      {
        buttons[i][j].setBackground(Color.BLACK);
      }
    }

    quit.addActionListener(new ActionListener() {

      /*********************************************************
      *
      * Method: actionPerformed
      *
      * Description: action lister for quit button
      *
      * @param: ActionEvent
      *
      * @return: None
      *
      *********************************************************/

      @Override
      public void actionPerformed(ActionEvent evt)
      {
        cp1.setVisible(false);
        setVisible(false);
        Grid gridz = new Grid(level);
      }

    });
  }
  /*********************************************************
  *
  * Method: revealNumber
  *
  * Description: Reveals number when clicked on tile
  *
  * @param: int, int
  *
  * @return: None
  *
  *********************************************************/
  public void revealNumber(int column, int row){
    if ( board[column][row].getHidden() )
    {
      buttons[column][row]
        .setLabel(Integer
        .toString(board[column][row]
        .getCount()));

      board[column][row].setHidden();
    }
  }
  /*********************************************************
  *
  * Method: revealSpace
  *
  * Description: reveals space in clicked tile
  *
  * @param: int, int
  *
  * @return: None
  *
  *********************************************************/
  public void revealSpace(int column, int row){
      revealNumber(column, row);
      revealSpaceRecursion(column, row-1, "N");
      revealSpaceRecursion(column+1, row-1, "NE");
      revealSpaceRecursion(column+1, row, "E");
      revealSpaceRecursion(column+1, row+1, "SE");
      revealSpaceRecursion(column, row+1, "S");
      revealSpaceRecursion(column-1, row+1, "SW");
      revealSpaceRecursion(column-1, row, "W");
      revealSpaceRecursion(column-1, row-1,"NW");
  }
  /*********************************************************
  *
  * Method: revealSpaceRecursion
  *
  * Description: reveals spaces if adjacent to a tile with no bombs near it.
  *
  * @param: int, int, String
  *
  * @return: None
  *
  *********************************************************/
  public void revealSpaceRecursion(int column, int row, String direc){
      if(column>=0 && column<size && row>=0 && row<size){
        if(board[column][row].getHidden()){
          revealNumber(column, row);
          if(board[column][row].getCount()==0){
            switch(direc){ //this is to prevent a stack overflow
              case "N":
                revealSpaceRecursion(column, row-1, "N");
                break;
              case "NE":
                revealSpaceRecursion(column, row-1, "N");
                revealSpaceRecursion(column+1, row-1, "NE");
                revealSpaceRecursion(column+1, row, "E");
                break;
              case "E":
                revealSpaceRecursion(column+1, row, "E");
                break;
              case "SE":
                revealSpaceRecursion(column+1, row, "E");
                revealSpaceRecursion(column+1, row+1, "SE");
                revealSpaceRecursion(column, row+1, "S");
                break;
              case "S":
                revealSpaceRecursion(column, row+1, "S");
                break;
              case "SW":
                revealSpaceRecursion(column, row+1, "S");
                revealSpaceRecursion(column-1, row+1, "SW");
                revealSpaceRecursion(column-1, row, "W");
                break;
              case "W":
                revealSpaceRecursion(column-1, row, "W");
                break;
              case "NW":
                revealSpaceRecursion(column-1, row, "W");
                revealSpaceRecursion(column-1, row-1,"NW");
                revealSpaceRecursion(column, row-1, "N");
                break;
            }
          }
        }
      }
    }
    /*********************************************************
    *
    * Method: setAdjacentCells
    *
    * Description: sets the directional nodes (N, NE, E...)
    *
    * @param: None
    *
    * @return: None
    *
    *********************************************************/
  public void setAdjacentCells(){
    //i is for column, j is for row
    for(int j=0;j<size;j++){
      for(int i=0;i<size;i++){
        if(j==0){ //Top Row
          if(i==0){//Left Corner
            board[i][j].setEast(board[i+1][j]);
            board[i][j].setSEast(board[i+1][j+1]);
            board[i][j].setSouth(board[i][j+1]);
          }
          else if(i==size-1){//Right Corner
            board[i][j].setSouth(board[i][j+1]);
            board[i][j].setSWest(board[i-1][j+1]);
            board[i][j].setWest(board[i-1][j]);
          }
          else{ //anywhere else along top row
            board[i][j].setEast(board[i+1][j]);
            board[i][j].setSEast(board[i+1][j+1]);
            board[i][j].setSouth(board[i][j+1]);
            board[i][j].setSWest(board[i-1][j+1]);
            board[i][j].setWest(board[i-1][j]);
          }
        }
        else if(j==size-1){//Bottom Row
          if(i==0){//Left Corner
            board[i][j].setNorth(board[i][j-1]);
            board[i][j].setNEast(board[i+1][j-1]);
            board[i][j].setEast(board[i+1][j]);
          }
          else if(i==size-1){//Right Corner
            board[i][j].setNorth(board[i][j-1]);
            board[i][j].setWest(board[i-1][j]);
            board[i][j].setNWest(board[i-1][j-1]);
          }
          else{//anywhere else along bottom row
            board[i][j].setNorth(board[i][j-1]);
            board[i][j].setNEast(board[i+1][j-1]);
            board[i][j].setEast(board[i+1][j]);
            board[i][j].setWest(board[i-1][j]);
            board[i][j].setNWest(board[i-1][j-1]);
          }
        }
        else if(i==0){//Leftmost column
          //note that the corners have already been covered in previous cases so no need to do them again
          board[i][j].setNorth(board[i][j-1]);
          board[i][j].setNWest(board[i+1][j-1]);
          board[i][j].setWest(board[i+1][j]);
          board[i][j].setSWest(board[i+1][j+1]);
          board[i][j].setSouth(board[i][j+1]);
        }
        else if(i==size-1){//Rightmost column
          board[i][j].setNorth(board[i][j-1]);
          board[i][j].setSouth(board[i][j+1]);
          board[i][j].setSWest(board[i-1][j+1]);
          board[i][j].setWest(board[i-1][j]);
          board[i][j].setNWest(board[i-1][j-1]);
        }
        else{//Everywhere in between
          board[i][j].setAllDirections(board[i][j-1], board[i+1][j-1], board[i+1][j], board[i+1][j+1], board[i][j+1], board[i-1][j+1], board[i-1][j], board[i-1][j-1]);
        }
      }//j loop
    }//i loop
  }
}
