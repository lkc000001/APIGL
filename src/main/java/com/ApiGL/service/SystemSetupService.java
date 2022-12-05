package com.ApiGL.service;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.web.multipart.MultipartFile;

public interface SystemSetupService {
	
	String apiGLRoleUpload(MultipartFile file) throws EncryptedDocumentException, IOException;
	
	String apiglFunctionUpload(MultipartFile file) throws EncryptedDocumentException, IOException;

	String UserUpload(MultipartFile file) throws EncryptedDocumentException, IOException;
}
