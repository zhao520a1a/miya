package com.miya.search.feign;

import com.miya.search.pojo.SearchResult;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SearchServiceHystric implements SearchService {


    @Override
    public SearchResult search(String queryString, int page, int rows) {
        log.error("Hystric fallbackError: 服务* .*,丢了找不到，攻城狮Fighting中！！");
        return null;
    }
}