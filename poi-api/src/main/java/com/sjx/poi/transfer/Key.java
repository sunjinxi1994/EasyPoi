package com.sjx.poi.transfer;

public  class Key {
  private int sheetIndex;
  private int row;
  private int column;

  public Key(int sheetIndex, int row, int column) {
   this.sheetIndex = sheetIndex;
   this.row = row;
   this.column = column;
  }

  @Override
  public String toString() {
   return "Key{" +
           "sheetIndex=" + sheetIndex +
           ", row=" + row +
           ", column=" + column +
           '}';
  }

  @Override
  public boolean equals(Object o) {
   if (this == o) return true;
   if (!(o instanceof Key)) return false;
   Key key = (Key) o;
   return sheetIndex == key.sheetIndex &&
           row == key.row &&
           column == key.column;
  }

  @Override
  public int hashCode() {
   int result = sheetIndex;
   result = 31 * result + row;
   result = 31 * result + column;
   return result;
  }


 }