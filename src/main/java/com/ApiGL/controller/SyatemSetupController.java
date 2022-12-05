package com.ApiGL.controller;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ApiGL.entity.response.ErrorMsg;
import com.ApiGL.service.SystemSetupService;

@Controller
@RequestMapping(value = "/systemSetup")
public class SyatemSetupController {
	
	@Autowired
	SystemSetupService systemSetupService;
	
    @GetMapping("/")
    public String index() {
    	return "systemSetup";
    }
    
    /**
	 * 角色資料上傳,excel檔案
	 * @param file
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
    @PostMapping("/roleupload")
    public ResponseEntity<?> roleUpload(@RequestParam("uploadFile") MultipartFile file) throws EncryptedDocumentException, IOException {
    	String respMsg = systemSetupService.apiGLRoleUpload(file);
		return new ResponseEntity<ErrorMsg>(new ErrorMsg(200, respMsg), HttpStatus.OK);
    }
    
    /**
	 * 功能資料上傳,excel檔案
	 * @param file
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
    @PostMapping("/functionupload")
    public ResponseEntity<?> functionUpload(@RequestParam("uploadFile") MultipartFile file) throws EncryptedDocumentException, IOException {
    	System.out.println(file);
    	String respMsg = systemSetupService.apiglFunctionUpload(file);
		return new ResponseEntity<ErrorMsg>(new ErrorMsg(200, respMsg), HttpStatus.OK);
    }
    
    /**
	 * 使用者資料上傳,excel檔案
	 * @param file
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
    @PostMapping("/userupload")
    public ResponseEntity<?> userUpload(@RequestParam("uploadFile") MultipartFile file) throws EncryptedDocumentException, IOException {
    	String respMsg = systemSetupService.UserUpload(file);
		return new ResponseEntity<ErrorMsg>(new ErrorMsg(200, respMsg), HttpStatus.OK);
    }
}
