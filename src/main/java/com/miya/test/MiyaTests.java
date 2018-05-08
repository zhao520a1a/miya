package com.miya.test;

import com.miya.entity.Item;
import com.miya.mq.KafkaSender;
import com.miya.service.impl.ItemServiceImpl;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class MiyaTests {


	@Autowired
	private ItemServiceImpl itemService;


	@Autowired
	private KafkaSender kafkaSender;

	@RequestMapping("/testKafka")
	public void testKafka(@RequestParam String msg) {
		System.out.println("Msg:" + msg);
		kafkaSender.sendMessage(msg);
		System.out.println("发送成功");
	}



	@RequestMapping(value = "/item",method={RequestMethod.POST,RequestMethod.GET})
	public Item testItem(){
		Item item = itemService.getItemById(562379L);
		return item;
	}



	@RequestMapping(value = "/page",method={RequestMethod.POST,RequestMethod.GET})
	public Page testPage(){
		Pagination pagination = new Pagination(15, 1);
		Page<Item> page = itemService.find(pagination);
		return page;
	}









}
