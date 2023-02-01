package com.sjx.poi.transfer.poihssf;

import com.sjx.poi.transfer.ImportTable;
import com.sjx.poi.transfer.Key;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author : jingo
 * @Description: TODO
 * @date Date : 2021年01月11日 10:45
 **/
public  class HSSFImportTable implements ImportTable {
    private static final String TAG="HSSFImportTable";
    private HSSFWorkbook hssfWorkbook;
    private HashMap<Key, HSSFPictureData> pictureDataMap;
    private FileInputStream fileInputStream;
    @Override
    public void createTable(String path) throws Throwable {
        fileInputStream = new FileInputStream(path);
        hssfWorkbook = new HSSFWorkbook(fileInputStream);
    }

    @Override
    public int getSheetNum() {
        return hssfWorkbook.getNumberOfSheets();
    }

    @Override
    public int getRowNum(int sheetIndex) {
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(sheetIndex);
        return sheetAt.getPhysicalNumberOfRows();
    }

    @Override
    public String getTextValue(int sheetIndex, int row, int column) {
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetIndex);
        HSSFRow hssfRow = hssfSheet.getRow(row);
        HSSFCell hssfCell = hssfRow.getCell(column);
        return hssfCell.getStringCellValue();
    }

    @Override
    public byte[] getImageValue(int sheetIndex, int row, int column) {
        if (pictureDataMap==null){
            loadImage();
        }
        HSSFPictureData hssfPictureData = pictureDataMap.get(new Key(sheetIndex,row,column));
        if (hssfPictureData!=null){
            return hssfPictureData.getData();
        }
        return null;
    }



    private void loadImage(){
        pictureDataMap=new HashMap<>();
        int sheetNum = getSheetNum();
        for (int i = 0; i <sheetNum ; i++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
            List<HSSFShape> children = hssfSheet.getDrawingPatriarch().getChildren();
            for (int j = 0; j < children.size(); j++) {
                HSSFShape hssfShape = children.get(j);
                if (hssfShape instanceof HSSFPicture) {
                    HSSFPicture hssfPicture = (HSSFPicture) hssfShape;
                    HSSFClientAnchor hssfClientAnchor = (HSSFClientAnchor) hssfPicture.getAnchor();
                    HSSFPictureData pictureData = hssfPicture.getPictureData();
                    Key key=new Key(i,hssfClientAnchor.getRow1(),hssfClientAnchor.getCol1());
                    pictureDataMap.put(key,pictureData);
                }
            }
        }

    }
    @Override
    public void finish() throws Throwable {
        if (fileInputStream!=null){
            fileInputStream.close();
        }
    }
}
