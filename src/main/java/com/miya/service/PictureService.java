package com.miya.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {

	Map uploadPicture(MultipartFile uploadFile);
}
