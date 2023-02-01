package com.sjx.poi.config;

import com.sjx.annotation.poi.SortStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : hanvon
 * @Description: TODO
 * @date Date : 2021年01月15日 15:31
 **/
class RowDefinition<T extends CellDefinition> {

    private List<T>  cellDefinitions;
    private SortStrategy sortStrategy;
     private boolean sorted;
     private int height;
    public RowDefinition(){
        cellDefinitions=new ArrayList<>();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public final void registerCellDefinition(T cellDefinition){
        cellDefinitions.add(cellDefinition);
    }



    /**
     * get  cell num
     *
     * @return
     */
    public final int getCellNum(){
        if (cellDefinitions!=null){
            return cellDefinitions.size();
        }else {
            return 0;
        }
    }

    public final T getCellDefinition(int index){
          return cellDefinitions.get(index);
    }



   protected synchronized   void  sort(){
        if (sorted){
            return;
        }
        Collections.sort(cellDefinitions);
    }



}
