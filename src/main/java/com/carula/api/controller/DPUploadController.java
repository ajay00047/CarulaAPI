package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GenericResponseBean;
import com.carula.api.constants.Constants;
import com.carula.api.dao.UserDAO;
import com.carula.api.exception.StorageException;
import com.carula.api.service.FileSystemStorageService;

@RestController
public class DPUploadController extends BaseController {

	@Autowired
	private FileSystemStorageService storageService;
	
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = Constants.dpUploadUrl, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@RequestParam("file") final MultipartFile file) throws Exception {
		String fileName = null;
		if ((fileName = storageService.store(file)) != null) {
			userDAO.updateDP(fileName, fileName);
		}else{
			throw new StorageException("Unable to upload file");
		}
		return generateResponse(null);
	}

}
