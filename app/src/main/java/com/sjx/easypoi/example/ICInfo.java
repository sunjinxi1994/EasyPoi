package com.sjx.easypoi.example;


import com.sjx.annotation.convert.ConvertClass;
import com.sjx.annotation.poi.CellType;
import com.sjx.annotation.poi.DataCell;
import com.sjx.annotation.poi.DataRow;
import com.sjx.annotation.poi.CellScope;
import com.sjx.annotation.poi.SortStrategy;
import com.sjx.annotation.poi.TitleCell;
import com.sjx.annotation.poi.TitleRow;
import com.sjx.annotation.poi.TableConfig;
import com.sjx.poi.convert.date.DateConvert;
import com.sjx.poi.convert.image.FileImageConvert;

import java.io.Serializable;
import java.util.Date;
@TableConfig
public class ICInfo implements Serializable {



	@TitleRow(
			height = 200,
			sortStrategy = SortStrategy.SORT_INDEX,
			titles = {
					@TitleCell(index = 0,name = "姓名"),
					@TitleCell(index = 1,name = "身份证号"),
					@TitleCell(index = 2,name = "人员类型"),
					@TitleCell(index = 4,name = "ID号"),
					@TitleCell(index = 5,name = "IC卡号"),
					@TitleCell(index = 6,name = "打卡时间"),
					@TitleCell(index = 7,name = "设备名称"),
					@TitleCell(index = 8,name = "识别方式"),
					@TitleCell(index = 9,name = "对比得分"),
					@TitleCell(index = 10,name = "是否通过"),
					@TitleCell(index = 11,name = "是否上传"),
					@TitleCell(index = 12,name = "人体温度"),
					@TitleCell(index = 13,name = "口罩佩戴"),
					@TitleCell(index = 14,name = "登记照片",mergeColumnNum = 1),
					@TitleCell(index = 15,name = "实时照片",mergeColumnNum = 1),
			}
	)
	public void configTitleRow(){

	}

	@DataRow(height =100,sortStrategy = SortStrategy.SORT_INDEX)
	public void configDataRow(){

	}



	// 身份证号_性别_出生年月日_发证机关_姓名_民族_有效期
	/**
	 * 姓名
	 */
	@DataCell(index = 1,type = CellType.Text)
	private String name;
	/**
	 * 身份证号
	 */
	@DataCell(index = 2,type = CellType.Text)
	private String idNum;
	/**
	 * 人员类型
	 */
	@DataCell(index = 3,type = CellType.Text)
	private char userType;
	/**
	 * 工号
	 */
	@DataCell(index =4,type = CellType.Text)
	private String jobNum;
	/**
	 * ic卡号
	 */
	@DataCell(index =4,type = CellType.Text)
	private String icNum;

	/**
	 * 比对时间
	 */
	@ConvertClass(convertClass = DateConvert.class,simpleArg ="YYYY-MM-dd HH:mm:ss")
	@DataCell(index =5,type = CellType.Text)
	private Date compareTime;


	/**
	 * 设备名称
	 */
	@DataCell(index =5,type = CellType.Text)
	private String deviceName;

	/**
	 * 识别方式
	 */
	@DataCell(index =7,type = CellType.Text)
	private int recognitionType;

	/**
	 * 对比得分
	 */
	@DataCell(index =8,type = CellType.Text)
	private float compareScore;

	/**
	 * 是否通过
	 */
	@DataCell(index =9,type = CellType.Text)
	private byte isPass;

	/**
	 * 是否上传
	 */
	@DataCell(index =10,type = CellType.Text)
	private byte isUpload;;


	/**
	 * 人体温度
	 */
	@DataCell(index =10,type = CellType.Text)
	private float tamper;;
	/**
	 * 口罩佩戴
	 */
	@DataCell(index =9,type = CellType.Text)
	private short mask;
	/**
	 * 登记照片
	 */
	@ConvertClass(convertClass = FileImageConvert.class,simpleArg ="50")
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.EXPORT)
	private String registerPhoto;
	/**
	 * 实时照片
	 */
	@ConvertClass(convertClass =  FileImageConvert.class,simpleArg ="50")
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.EXPORT)
	private String capturePhoto;


	/**
	 * 登记照片原始数据
	 */
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.IMPORT)
	private byte[] registerPhotoRaw;
	/**
	 * 抓拍照片原始数据
	 */
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1,scope = CellScope.IMPORT)
	private byte[] capturePhotoRaw;


	/**
	 * 比对时间 日期格式
	 *
	 */
//	@ConvertClass(convertClass = DateConvert.class,simpleArg ="YYYY-MM-dd HH:mm:ss")
//	@DataCell(index =5,type = CellType.Text)
//	private Date compareDate;

	/**
	 * base64 照片
	 */
	@ConvertClass(convertClass = CustomConvert.class)
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1)
	private String base64Photo;

	/**
	 * 加密 照片
	 */
	@ConvertClass(convertClass = CustomConvert.class,simpleArg = "arg")
	@DataCell(index =9,type = CellType.Image,mergeColumnNum = 1)
	private String encryptPhoto;


	public byte[] getRegisterPhotoRaw() {
		return registerPhotoRaw;
	}

	public void setRegisterPhotoRaw(byte[] registerPhotoRaw) {
		this.registerPhotoRaw = registerPhotoRaw;
	}

	public byte[] getCapturePhotoRaw() {
		return capturePhotoRaw;
	}

	public void setCapturePhotoRaw(byte[] capturePhotoRaw) {
		this.capturePhotoRaw = capturePhotoRaw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getIcNum() {
		return icNum;
	}

	public void setIcNum(String icNum) {
		this.icNum = icNum;
	}

	public Date getCompareTime() {
		return compareTime;
	}

	public void setCompareTime(Date compareTime) {
		this.compareTime = compareTime;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getRecognitionType() {
		return recognitionType;
	}

	public void setRecognitionType(int recognitionType) {
		this.recognitionType = recognitionType;
	}

	public float getCompareScore() {
		return compareScore;
	}

	public void setCompareScore(float compareScore) {
		this.compareScore = compareScore;
	}

	public byte getIsPass() {
		return isPass;
	}

	public void setIsPass(byte isPass) {
		this.isPass = isPass;
	}

	public byte getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(byte isUpload) {
		this.isUpload = isUpload;
	}

	public float getTamper() {
		return tamper;
	}

	public void setTamper(float tamper) {
		this.tamper = tamper;
	}

	public short getMask() {
		return mask;
	}

	public void setMask(short mask) {
		this.mask = mask;
	}

	public String getRegisterPhoto() {
		return registerPhoto;
	}

	public void setRegisterPhoto(String registerPhoto) {
		this.registerPhoto = registerPhoto;
	}

	public String getCapturePhoto() {
		return capturePhoto;
	}

	public void setCapturePhoto(String capturePhoto) {
		this.capturePhoto = capturePhoto;
	}

	public String getBase64Photo() {
		return base64Photo;
	}

	public void setBase64Photo(String base64Photo) {
		this.base64Photo = base64Photo;
	}

	public String getEncryptPhoto() {
		return encryptPhoto;
	}

	public void setEncryptPhoto(String encryptPhoto) {
		this.encryptPhoto = encryptPhoto;
	}

	@Override
	public String toString() {
		return "ICInfo{" +
				"name='" + name + '\'' +
				", idNum='" + idNum + '\'' +
				", userType=" + userType +
				", jobNum='" + jobNum + '\'' +
				", icNum='" + icNum + '\'' +
				", compareTime=" + compareTime +
				", deviceName='" + deviceName + '\'' +
				", recognitionType=" + recognitionType +
				", compareScore=" + compareScore +
				", isPass=" + isPass +
				", isUpload=" + isUpload +
				", tamper=" + tamper +
				", mask=" + mask +
				", registerPhoto='" + registerPhoto + '\'' +
				", capturePhoto='" + capturePhoto + '\'' +
				", base64Photo='" + base64Photo + '\'' +
				", encryptPhoto='" + encryptPhoto + '\'' +
				'}';
	}
}