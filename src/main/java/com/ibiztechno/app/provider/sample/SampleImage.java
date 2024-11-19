package com.ibiztechno.app.provider.sample;

public class SampleImage {
	String TempCode;
	String NameFile;
	byte[] FileImage;
	String FileType;
	String FileExt;
	String ContentType;
	String ContentDisposition;

	

	public String getTempCode() {
		return TempCode;
	}

	public void setTempCode(String tempCode) {
		TempCode = tempCode;
	}

	public String getNameFile() {
		return NameFile;
	}

	public void setNameFile(String nameFile) {
		NameFile = nameFile;
	}

	public byte[] getFileImage() {
		return FileImage;
	}

	public void setFileImage(byte[] fileImage) {
		FileImage = fileImage;
	}

	public String getFileType() {
		return FileType;
	}

	public void setFileType(String fileType) {
		FileType = fileType;
	}

	public String getFileExt() {
		return FileExt;
	}

	public void setFileExt(String fileExt) {
		FileExt = fileExt;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getContentDisposition() {
		return ContentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		ContentDisposition = contentDisposition;
	}
}
