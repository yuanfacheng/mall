package com.macro.mall.demo.juc;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Author: yfc
 * @Date: 2023/10/6 16:35
 */
public class CountDownLatchExample {
	public static void main(String[] args) throws InterruptedException {
		// 创建一个CountDownLatch对象，设置初始计数器的值为3
		CountDownLatch latch = new CountDownLatch(3);

		// 创建三个线程并启动它们
		Thread worker1 = new Worker(latch, "Worker 1");
		Thread worker2 = new Worker(latch, "Worker 2");
		Thread worker3 = new Worker(latch, "Worker 3");

		worker1.start();
		worker2.start();
		worker3.start();

		// 主线程等待，直到计数器的值变为0
		latch.await();

		// 当计数器的值为0时，表示所有线程都完成了任务
		System.out.println("All workers have completed their tasks.");
	}
}

class Worker extends Thread {
	private CountDownLatch latch;

	public Worker(CountDownLatch latch, String name) {
		super(name);
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println(getName() + " is working");
		// 模拟工作耗时
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(getName() + " has completed the task");
		// 每个线程完成任务后，调用countDown()方法，计数器减1
		latch.countDown();
	}
}

class Main {
	public static void main(String[] args) {
		// 创建一个字符串列表
		List<String> words = Arrays.asList("apple", "banana", "orange", "grape", "melon", "peach");

		// 使用Stream API进行处理：转换为大写，筛选包含字母"E"的字符串，按长度排序
		List<String> result = words.stream()
				.map(String::toUpperCase) // 转换为大写
				.filter(word -> word.contains("E")) // 筛选包含字母"E"的字符串
				.sorted((str1, str2) -> str1.length() - str2.length()) // 按长度排序
				.collect(Collectors.toList()); // 将结果收集为列表

		// 打印处理后的结果
		System.out.println(result);
	}
}


class Person {
	private String name;

	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}


class Main1 {
	public static void main(String[] args) {
		// 创建包含Person对象的列表
		List<Person> people = Arrays.asList(
				new Person("Alice", 25),
				new Person("Bob", 30),
				new Person("Charlie", 18),
				new Person("David", 22),
				new Person("Eva", 28),
				new Person("Frank", 35),
				new Person("Grace", 18)
		);

		// 使用Stream API进行处理
		Map<Integer, Long> ageGroupCount = people.stream()
				.filter(person -> person.getAge() > 18) // 筛选年龄大于18岁的人
				.collect(Collectors.groupingBy(Person::getAge, Collectors.counting())); // 按年龄进行分组并计数

		// 按人数降序排列并打印结果
		ageGroupCount.entrySet().stream()
				.sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue())) // 按人数降序排列
				.forEach(entry -> System.out.println("Age: " + entry.getKey() + ", Count: " + entry.getValue()));
	}
}

class ObjectToMapConverter {

	public static Map<String, Object> convertObjectToMap(Object obj) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();

		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object fieldValue = field.get(obj);
			map.put(fieldName, fieldValue);
		}

		return map;
	}

	public static void main(String[] args) {
		// Example usage:
		// Create an object
		YourClass yourObject = new YourClass();
		// Convert object to map
		try {
			Map<String, Object> objectMap = convertObjectToMap(yourObject);
			// Print the map
			System.out.println(objectMap);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// Define your class here
	static class YourClass {
		private String name;

		private int age;

		// Getters and setters
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}


@Configuration
@EnableAsync
class AppConfig {

	@Bean("taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5); // 核心线程数
		executor.setMaxPoolSize(10); // 最大线程数
		executor.setQueueCapacity(25); // 等待队列容量
		executor.setThreadNamePrefix("SFTPTask-"); // 线程名前缀
		executor.initialize();
		return executor;
	}
}


@Configuration
@EnableAsync
class AsyncConfig implements AsyncConfigurer {

	@Override
	@Bean("customThreadPool")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5); // 核心线程数
		executor.setMaxPoolSize(10); // 最大线程数
		executor.setQueueCapacity(25); // 等待队列容量
		executor.setThreadNamePrefix("CustomThreadPool-"); // 线程名前缀
		executor.initialize();
		return executor;
	}
}


@Service
class MyAsyncService {

	@Async("customThreadPool") // 使用自定义线程池
	public CompletableFuture<String> performAsyncTask() {
		// 异步任务的具体逻辑
		// 这里可以放入你想要异步执行的代码
		String result = "Async Task Result";
		return CompletableFuture.completedFuture(result);
	}
}












