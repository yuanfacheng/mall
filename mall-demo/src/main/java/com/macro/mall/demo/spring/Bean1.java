package com.macro.mall.demo.spring;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: aa
 * @Author: yfc
 * @Date: 2024/3/21 10:44
 */
public class Bean1 {

	private static final Logger log = LoggerFactory.getLogger(Bean1.class);

	private Bean2 bean2;


	@Autowired
	public void setBean2(Bean2 bean2){
		log.debug("@Autowired 生效：{}",bean2);
		this.bean2 = bean2;
	}

	@Autowired
	private Bean3 bean3;

	@Resource
	public void setBean2(Bean3 bean3){
		log.debug("@Resource 生效：{}",bean3);
		this.bean3 = bean3;

	}



	public static void main(String[] args) {



	}

	public interface beanFactoryPostProcessor{
		void inject();
	}
}
