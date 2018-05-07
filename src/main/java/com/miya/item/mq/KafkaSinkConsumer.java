//package com.miya.item.mq;
//
//import com.golden.util.StringUtil;
//import com.google.common.collect.Maps;
//import com.miya.item.feign.ItemService;
//import com.miya.item.dto.ItemDescDto;
//import com.miya.item.dto.ItemDto;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.Map;
//
//
///*
// *
// * 在指定的路径生成商品静态文件
// * */
//@Slf4j
//@EnableBinding(Sink.class)
//public class KafkaSinkConsumer {
//
//    @Autowired
//    private ItemService itemService;
//    @Autowired
//    private FreeMarkerConfigurer freeMarkerConfigurer;
//    @Value("${HTML_OUT_PATH}")
//    private String HTML_OUT_PATH;
//
//    @StreamListener(Sink.INPUT)
//    public void messageSink(Object payload) {
//        log.info("KafkaConsumer接受消息,msg:{} ", payload);
//        String msg = payload.toString();
//        try {
//            if (!StringUtils.isEmpty(msg)) {
//                String[] datas = msg.split(":");
//                String flag = datas[0];
//                String data = datas[1];
//                if (flag.equals("add") || flag.equals("update")) {
//                    createStaticTemplet(Long.parseLong(data));
//                    log.info("商品Id:{}, 导入|修改索引成功", msg);
//                    //			ConsumerThreadPool.putThread(msg); // 添加一个消费线程
//                } else if (flag.equals("delete")) {
//                    log.info("商品Id:{}, 删除索引成功", msg);
//                }
//            }
//        } catch (Exception e) {
//            log.error("KafkaConsumer接受消息处理异常： {}", StringUtil.getTrace(e));
//        }
//    }
//
//    private void createStaticTemplet(Long itemId) throws InterruptedException, IOException, TemplateException {
//        //等待事务提交
//        Thread.sleep(1000);
//        //根据商品id查询商品信息及商品描述
//        ItemDto item = itemService.getItemById(itemId).getData();
//        ItemDescDto itemDesc = itemService.getItemDesc(itemId).getData();
//        //使用freemarker生成静态页面
//        Configuration configuration = freeMarkerConfigurer.getConfiguration();
//        //1.创建模板
//        //2.加载模板对象
//        Template template = configuration.getTemplate("itemDemo.ftl");
//        //3.准备模板需要的数据
//        Map data = Maps.newHashMap();
//        data.put("item", item);
//        data.put("itemDesc", itemDesc);
//        //4.指定输出的目录及文件名
//        String htmlOutPath = HTML_OUT_PATH + itemId + ".html";
//        Writer out = new FileWriter(new File(htmlOutPath));
//        //5.生成静态页面
//        template.process(data, out);
//        //关闭流
//        out.close();
//        log.info("生成商品详情静态页面成功，路径:{} ", htmlOutPath);
//    }
//
//
////	@StreamListener(Sink.INPUT)
////	public void receiver(Message<Object> message) {
////		Object obj = message.getPayload();
////		System.out.println("接受对象:" + obj);
////	}
//
//
//}
