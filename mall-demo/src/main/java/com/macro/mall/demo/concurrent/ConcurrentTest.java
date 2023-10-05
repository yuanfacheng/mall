package com.macro.mall.demo.concurrent;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.Test;

/**
 * @Description: 模拟N个线程并发
 * @Author: yfc
 * @Date: 2023/10/5 13:35
 */
public class ConcurrentTest {
	@Test
	public void test() {
		ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
			// 测试的逻辑内容
			long delay = RandomUtil.randomLong(100, 1000);
			ThreadUtil.sleep(delay);
			Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
		});

		// 获取总的执行时间，单位毫秒
		Console.log(tester.getInterval());
	}
}
