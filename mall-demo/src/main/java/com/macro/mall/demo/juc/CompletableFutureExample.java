package com.macro.mall.demo.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * CompletableFuture 是 Java 8 引入的一个类，用于异步编程和处理多个异步操作的结果。
 * 它提供了一种简洁的方式来处理异步计算的完成事件。CompletableFuture 类实现了 Future 接口，
 * 但它也实现了 CompletionStage 接口，这个接口定义了一组在异步操作完成时触发其他操作的方法，使得你可以构建一些复杂的异步流水线。
 *
 * 以下是一个简单的 CompletableFuture 示例，演示了如何创建、组合和处理异步操作的结果：
 * 在这个示例中，CompletableFuture.supplyAsync() 方法用于执行一个异步操作，返回一个
 * CompletableFuture 对象。thenComposeAsync() 方法将上一个操作的结果作为参数，并返回一个新的
 * CompletableFuture 对象，表示一个组合操作。最后，使用 whenComplete() 方法在所有操作完成时打印最终的结果。
 *
 * 请注意，CompletableFuture 还有很多其他有用的方法，例如 thenApplyAsync() 用于转换异步操作的结果，
 * thenAcceptAsync() 用于消费异步操作的结果等。这些方法允许你以非常灵活的方式组合和处理异步操作。
 * @Author: yfc
 * @Date: 2023/10/6 15:59
 */
public class CompletableFutureExample {
	public static void main(String[] args) {
		// 创建一个CompletableFuture，表示一个异步操作，它会在未来的某个时刻完成
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			// 模拟一个耗时的操作
			try {
				Thread.sleep(2000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Hello, ";
		});

		// 当上面的异步操作完成时，将其结果与另一个异步操作的结果进行组合
		CompletableFuture<String> combinedFuture = future.thenComposeAsync(result -> {
			// 第二个异步操作，将前一个操作的结果与另外一个字符串组合起来
			return CompletableFuture.supplyAsync(() -> result + "World!");
		});

		// 当所有异步操作完成时，打印最终的结果
		combinedFuture.whenComplete((result, throwable) -> {
			if (throwable == null) {
				System.out.println(result); // 输出：Hello, World!
			}
			else {
				System.err.println("Error occurred: " + throwable.getMessage());
			}
		});

		// 阻塞等待所有异步操作完成
		try {
			combinedFuture.get();
		}
		catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

