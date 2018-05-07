package com.miya.item.feign;


import com.golden.pojo.RespList;
import com.miya.item.dto.ContentDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

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