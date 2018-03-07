package com.miya.portal.feign;

import com.miya.portal.dto.ContentDto;
import com.miya.portal.dto.RespList;
import com.miya.portal.dto.ResponseModal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵金鑫
 */
@Log4j2
@Component
public class ContentServiceHystric implements ContentService {


    @Override
    public RespList<ContentDto> getContentByCid (long cid) {
        log.error("Hystric fallbackError: 内容服务* .*,丢了找不到，攻城狮Fighting中！！");
        return new RespList<ContentDto>().failData(null);
    }
}