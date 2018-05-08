package com.miya.search.mq;

import com.golden.util.StringUtil;
import com.miya.search.service.SearchItemService;
import lombok.extern.log4j.Log4j2;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Log4j2
@EnableBinding(Sink.class)
public class KafkaSinkConsumer {


    @Autowired
    public SearchItemService searchItemService;


    @StreamListener(Sink.INPUT)
    public void messageSink(Object payload) {
        log.info("KafkaConsumer接受消息,msg:{} " , payload);
        String msg = payload.toString();

        try {
            if (!StringUtils.isEmpty(msg)) {
                String[] datas = msg.split(":");
                String flag = datas[0];
                String data = datas[1];
                if (flag.equals("add") || flag.equals("update")) {
                    searchItemService.importItemToIndexByItemId(Long.parseLong(data));
                    log.info("商品Id:{}, 导入|修改索引成功", msg);
                    //			ConsumerThreadPool.putThread(msg); // 添加一个消费线程
                } else if (flag.equals("delete")) {
                    searchItemService.deleteItemsIndex(Long.parseLong(data));
                    log.info("商品Id:{}, 删除索引成功", msg);
                }
            }
        } catch (Exception e) {
            log.error("KafkaConsumer接受消息处理异常： {}", StringUtil.getTrace(e));
        }
    }


//	@StreamListener(Sink.INPUT)
//	public void receiver(Message<Object> message) {
//		Object obj = message.getPayload();
//		System.out.println("接受对象:" + obj);
//	}

}
