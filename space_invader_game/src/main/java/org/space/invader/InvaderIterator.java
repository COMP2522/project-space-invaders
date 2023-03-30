package org.space.invader;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Private class representing the custom iterator for the invader group.
 */
public class InvaderIterator implements Iterator<Invader> {

  private int row;
  private int col;
  private final int numRows;
  private final int numCols;
  private final Invader[][] tabInvader;


  /**
   * Constructor for InvaderIterator class.
   * Initializes the iterator to point to the first live invader.
   *
   * @param numRows    the number of rows in the invader 2D array
   * @param numCols    the number of columns in the invader 2D array
   * @param tabInvader the 2D array of Invader objects
   */
  public InvaderIterator(int numRows, int numCols, Invader[][] tabInvader) {
    this.row = 0;
    this.col = 0;
    this.numRows = numRows;
    this.numCols = numCols;
    this.tabInvader = tabInvader;
  }

  /**
   * Returns true if there is at least one more live invader to iterate over.
   */
  @Override
  public boolean hasNext() {
    while (row < numRows && tabInvader[row][col] == null) {
      if (++col == numCols) {
        col = 0;
        row++;
      }
    }
    return row < numRows && col < numCols;
  }

  /**
   * Returns the next live invader in the iteration.
   */
  @Override
  public Invader next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    Invader nextInvader = tabInvader[row][col];
    if (++col == numCols) {
      col = 0;
      row++;
    }
    return nextInvader;
  }

  /**
   * Unsupported operation in this implementation.
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}