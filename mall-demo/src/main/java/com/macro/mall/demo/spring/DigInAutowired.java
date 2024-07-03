package com.macro.mall.demo.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

/**
 * @Description:
 * @Author: yfc
 * @Date: 2024/3/21 10:54
 */
//autowiredannotationbeanpostprocessor
//bean后处理器
public class DigInAutowired {
	public static void main(String[] args) throws Throwable {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		//这里用new的话，容器默认已经是创建好的成品bean了，不会在走bean的创建过程，
		// 依赖注入，属性填充，初始化等步骤都不走了
		beanFactory.registerSingleton("bean2",new Bean2());
		beanFactory.registerSingleton("bean3",new Bean3());
		//@Value
		beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());


		AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
		//beanFactory去找一些依赖的bean
		processor.setBeanFactory(beanFactory);

		Bean1 bean1 = new Bean1();
//		System.out.println(bean1);
//		//执行依赖注入
//		processor.postProcessProperties(null,bean1,"bean1");
//		System.out.println(bean1);

		Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata",String.class,Class.class, PropertyValues.class);
		findAutowiringMetadata.setAccessible(true);
		//获取bean1上加了@Autowired的成员变量,方法参数
		InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);
		metadata.inject(bean1,"bean1",null);
		System.out.println(bean1);
	}
}
