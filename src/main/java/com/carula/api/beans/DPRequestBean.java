package com.carula.api.beans;

import org.springframework.web.multipart.MultipartFile;

public class DPRequestBean extends BaseRequestBean{

	private MultipartFile fileName;

	public MultipartFile getFileName() {
		return fileName;
	}

	public void setFileName(MultipartFile fileName) {
		this.fileName = fileName;
	}



}
