package cn.magme.web.entity;

import java.io.Serializable;

public class UploadInfo implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 7867332088730775569L;

    //已经上传的大小.
	private double uploaded;
	
	//上传的总大小.
	private double totalLong;
	
	//上传的百分比.
	private int percent;
	
	/**
	 * 
	 */
	private String fileName;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getUploaded() {
		return uploaded;
	}

	public void setUploaded(double uploaded) {
		this.uploaded = uploaded;
	}

	public double getTotalLong() {
		return totalLong;
	}

	public void setTotalLong(double totalLong) {
		this.totalLong = totalLong;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	

}
