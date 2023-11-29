//package com.macro.mall.demo.reflect;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.List;
//import java.util.function.Consumer;
//
//public class ReflectionExample {
//
//	public static Object invokeMethod(Class<?> clazz, String methodName, Object... args)
//			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//		// 获取方法的参数类型
//		Class<?>[] parameterTypes = new Class[args.length];
//		for (int i = 0; i < args.length; i++) {
//			parameterTypes[i] = args[i].getClass();
//		}
//
//		// 获取方法
//		Method method = clazz.getDeclaredMethod(methodName, parameterTypes[0]);
//		method.setAccessible(true); // 如果方法是私有的，需要设置为可访问
//
//		// 实例化类（如果方法是静态的，则不需要实例化）
////		Object instance = null;
////		if (!Modifier.isStatic(method.getModifiers())) {
////			instance = clazz.newInstance();
////		}
//		String str = "";
//
//		// 调用方法并返回结果
////		return method.invoke(instance, args);
//
//		return "";
//	}
//
//	public static void main(String[] args) {
//		try {
//			// 示例：调用String类的substring方法
//			Class<?> stringClass = String.class;
//			String result = (String) invokeMethod(String.class, "substring", "Hello, World!", 7);
//			System.out.println(result); // Output: World!
//
//			// 示例：调用Math类的静态方法sqrt
////            Class<?> mathClass = Math.class;
////            double sqrtResult = (double) invokeMethod(mathClass, "sqrt", 16.0);
////            System.out.println(sqrtResult); // Output: 4.0
//		}
//		catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public void sdsdf(Class<?> clazz) {
//		Method[] methods = clazz.getDeclaredMethods(); // 获取所有声明的方法
//		for (Method method : methods) {
//			String name = method.getName(); // 获取方法名
//			Class<?> returnType = method.getReturnType(); // 获取返回类型
//			Class<?>[] parameterTypes = method.getParameterTypes(); // 获取参数类型数组
//			int modifiers = method.getModifiers(); // 获取方法的访问修饰符
//		}
//
//	}
//
//
//	public void processNumbers(List<Integer> numbers, Consumer<Integer> consumer) {
//		for (Integer number : numbers) {
//			consumer.accept(number);
//		}
//	}
//}
