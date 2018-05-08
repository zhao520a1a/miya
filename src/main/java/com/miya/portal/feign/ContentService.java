package com.miya.portal.feign;

import com.golden.pojo.RespList;
import com.miya.portal.dto.ContentDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//value为服务客户端名称，指定Hystrix的fallback类
@FeignClient(value = "miya-content-service", fallback = ContentServiceHystric.class)
public interface ContentService {

    @GetMapping(value = "/content/getContent")
    RespList<ContentDto> getContentByCid(@RequestParam("cid") long cid);

}
