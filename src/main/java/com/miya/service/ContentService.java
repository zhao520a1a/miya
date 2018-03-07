package com.miya.service;


import com.miya.dto.ResponseModal;
import com.miya.entity.Content;

import java.util.List;

public interface ContentService {

	ResponseModal addContent(Content content);

	List<Content> getContentByCid(long cid);

}
