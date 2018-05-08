package com.miya.search.mq;

import lombok.Builder;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理kafka消息的线程池
 *
 */
public class ConsumerThreadPool {


    private  static int threadNum = 5  ;

    private static ExecutorService es = Executors.newFixedThreadPool(threadNum);

    /**
     * 向线程池中添加线程
     *
     * @param msg kafka消息
     */
    public static void putThread(String msg) {
        es.execute(ConsumerThread.builder().msg(msg).build());
    }

}






