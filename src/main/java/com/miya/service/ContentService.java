package com.miya.service;


import com.miya.dto.RespObject;
import com.miya.entity.Content;

public interface ContentService {

	RespObject<Content> insertContent(Content content);
}
