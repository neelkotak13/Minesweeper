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
import java.util.Random;
public class Cell{
  private boolean bomb; //SORRY I PUT BOMB INSTEAD OF MINE
  private boolean flag; //if the cell is flagged;
  private boolean hidden;
  private int count; //number of surrounding cells that are bombs
  private Cell BLANK;
  private Cell N = BLANK; //North Cell
  private Cell NE = BLANK;//North East Cell
  private Cell E = BLANK; //East Cell...and so forth
  private Cell SE = BLANK;
  private Cell S = BLANK;
  private Cell SW = BLANK;
  private Cell W = BLANK;
  private Cell NW = BLANK;

    /*********************************************************
    *
    * Method: Cell
    *
    * Description: Constructor, sets up a tile
    *
    * @param: None
    *
    * @return: None
    *
    *********************************************************/
  public Cell(){
    bomb = false;
    flag = false;
    hidden = true;
    //BLANK = new Cell();
  }
  /*********************************************************
  *
  * Method: getHidden
  *
  * Description: returns if a tile is hidden or not
  *
  * @param: None
  *
  * @return: boolean
  *
  *********************************************************/
  public boolean getHidden(){
    return hidden;
  }
  /*********************************************************
  *
  * Method: setHidden
  *
  * Description: sets a cell to be hidden/unseen
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  public void setHidden(){
    hidden = false;
  }
  /*********************************************************
  *
  * Method: setBomb
  *
  * Description: sets a bomb to be on the tile
  *
  * @param: None
  *
  * @return: None
  *
  *********************************************************/
  //the cell will become a bomb
  public void setBomb(){
    bomb = true;
  }
  /*********************************************************
  *
  * Method: getBomb
  *
  * Description: returns if there is a bomb or not
  *
  * @param: None
  *
  * @return: boolean
  *
  *********************************************************/
  public boolean getBomb(){
    return bomb;
  }
  /*********************************************************
  *
  * Method: getCount
  *
  * Description: Checks if bombs are near, and if so, increments counter
  *
  * @param: None
  *
  * @return: int
  *
  *********************************************************/
   public int getCount(){
    count = 0;
    if(N!=BLANK){
      if(N.getBomb()){//if the North cell is a bomb the counter will increase
        count++;
      }
    }
    if(NE!=BLANK){
      if(NE.getBomb()){
        count++;
      }
    }
    if(E!=BLANK){
      if(E.getBomb()){
        count++;
      }
    }
    if(SE!=BLANK){
      if(SE.getBomb()){
        count++;
      }
    }
    if(S!=BLANK){
      if(S.getBomb()){
        count++;
      }
    }
    if(SW!=BLANK){
      if(SW.getBomb()){
        count++;
      }
    }
    if(W!=BLANK){
      if(W.getBomb()){
        count++;
      }
    }
    if(NW!=BLANK){
      if(NW.getBomb()){
        count++;
      }
    }
    return count;
  }
  /*********************************************************
  *
  * Method: setAllDirections
  *
  * Description: Assigns al directions
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setAllDirections(Cell North, Cell NEast, Cell East, Cell SEast, Cell South, Cell SWest, Cell West, Cell NWest){
    N = North;
    NE = NEast;
    E = East;
    SE = SEast;
    S = South;
    SW = SWest;
    W = West;
    NW = NWest;
  }
  /*********************************************************
  *
  * Method: setNorth
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setNorth(Cell North){
    N = North;
  }
  /*********************************************************
  *
  * Method: setNEast
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setNEast(Cell NEast){
    NE = NEast;
  }
  /*********************************************************
  *
  * Method: setEast
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setEast(Cell East){
    E = East;
  }
  /*********************************************************
  *
  * Method: setSEast
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setSEast(Cell SEast){
    SE = SEast;
  }
  /*********************************************************
  *
  * Method: setSouth
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setSouth(Cell South){
    S = South;
  }
  /*********************************************************
  *
  * Method: setSWest
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setSWest(Cell SWest){
    SE = SWest;
  }
  /*********************************************************
  *
  * Method: setWest
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setWest(Cell West){
    W = West;
  }
  /*********************************************************
  *
  * Method: setNWest
  *
  * Description: takes cell and assigns it a value, setting a direction
  *
  * @param: Cell
  *
  * @return: None
  *
  *********************************************************/
  public void setNWest(Cell NWest){
    NW = NWest;
  }
}
