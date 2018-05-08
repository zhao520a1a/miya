package com.miya.search.mq;

import com.golden.util.StringUtil;
import com.miya.search.service.SearchItemService;
import com.miya.search.service.impl.SearchItemServiceImpl;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Builder
public class ConsumerThread extends SearchItemServiceImpl implements Runnable {
    public String msg;




    @Override
    public void run() {
        /*添加商品索引 */
        //从消息中取商品id
        long itemId = Long.parseLong(msg);
        try {
            this.importItemToIndexByItemId(itemId);
        } catch (Exception e) {
            log.error("添加指定商品信息到索引库异常：{}", StringUtil.getTrace(e));
        }
//        //等待事务提交
//        Thread.sleep(1000);
    }

}