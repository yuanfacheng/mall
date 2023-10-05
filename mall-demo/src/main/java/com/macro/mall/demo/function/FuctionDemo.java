package com.macro.mall.demo.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Description: jdk8函数式编程
 * @Author: yfc
 * @Date: 2023/10/5 10:51
 */
public class FuctionDemo {

}

/**
 * 概述
 * Supplier、Function、Predicate、Consumer、BiFunction、BiPredicate、BiConsumer是Java函数式接口的一部分，它们用于定义不同类型的函数，从而在函数式编程中提供了更灵活的方式来处理数据。
 *
 * Supplier：表示一个供应商，它不接受任何参数，但返回一个结果。它的抽象方法为T get()，用于获取一个结果。
 *
 * Function：表示一个函数，它接受一个参数并返回一个结果。它的抽象方法为R apply(T t)，用于将输入参数转换为输出结果。
 *
 * Predicate：表示一个断言，它接受一个参数并返回一个布尔值。它的抽象方法为boolean test(T t)，用于对输入参数进行条件判断。
 *
 * Consumer：表示一个消费者，它接受一个参数并没有返回值。它的抽象方法为void accept(T t)，用于对输入参数进行一些操作。
 *
 * BiFunction：表示一个接受两个参数并返回一个结果的函数。它的抽象方法为R apply(T t, U u)，用于将两个输入参数转换为输出结果。
 *
 * BiPredicate：表示一个接受两个参数并返回一个布尔值的断言。它的抽象方法为boolean test(T t, U u)，用于对两个输入参数进行条件判断。
 *
 * BiConsumer：表示一个接受两个参数并没有返回值的操作。它的抽象方法为void accept(T t, U u)，用于对两个输入参数进行一些操作。
 *
 * 这些函数式接口可以通过Lambda表达式来实现，从而简化代码的编写。在函数式编程中，它们可以作为方法的参数或返回值，用于描述不同的行为和操作，提高代码的可读性和可维护性。
 **/


//	 /** 详细
//	 * Supplier<T>
//	 *
//	 * 抽象方法：T get()
//	 * 用途：表示一个供应商，不接受任何参数，返回一个结果。
//	 * 使用场景：当需要获取一个对象，而该对象的创建逻辑比较复杂或者需要重复使用时，可以使用Supplier。
//	 ** /
//

class ProductManager {
	public static void main(String[] args) {
		Product product = createProduct(() -> {
			// 从数据库获取商品信息并创建Product对象
			return getFromDatabase();
		});

		System.out.println(product);
	}

	public static Product createProduct(Supplier<Product> supplier) {
		return supplier.get();
	}

	public static Product getFromDatabase() {
		// 从数据库获取商品信息
		// ...
		return new Product("iPhone", 999);
	}
}

class Product {
	private String name;

	private double price;

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" +
				"name='" + name + '\'' +
				", price=" + price +
				'}';
	}
}

//
//
//	  Function<T, R>
//
//	  抽象方法：R apply(T t)
//	  用途：表示一个接受一个输入参数并返回一个结果的函数。
//	  使用场景：当需要对输入参数进行处理，并返回一个结果时，可以使用Function。
class SalaryCalculator {
	public static void main(String[] args) {
		Employee employee = new Employee("John", 40, 25.0);
		double salary = calculateSalary(employee, emp -> emp.getHoursWorked() * emp.getHourlyRate());
		System.out.println("Salary: " + salary);
	}

	public static double calculateSalary(Employee employee, Function<Employee, Double> salaryFunction) {
		return salaryFunction.apply(employee);
	}
}

class Employee {
	private String name;

	private int hoursWorked;

	private double hourlyRate;

	public Employee(String name, int hoursWorked, double hourlyRate) {
		this.name = name;
		this.hoursWorked = hoursWorked;
		this.hourlyRate = hourlyRate;
	}

	public String getName() {
		return name;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}
}

//
//	  Predicate<T>
//
//	  抽象方法：boolean test(T t)
//	  用途：表示一个断言，接受一个输入参数并返回一个布尔值。
//	  使用场景：当需要对输入参数进行条件判断时，可以使用Predicate。
class FilterFruits {
	public static void main(String[] args) {
		List<Fruit> fruits = new ArrayList<>();
		fruits.add(new Fruit("Apple", "Red", 100));
		fruits.add(new Fruit("Banana", "Yellow", 150));
		fruits.add(new Fruit("Orange", "Orange", 120));

		List<Fruit> redFruits = filterFruits(fruits, fruit -> fruit.getColor().equals("Red"));
		System.out.println("Red fruits: " + redFruits);
	}

	public static List<Fruit> filterFruits(List<Fruit> fruits, Predicate<Fruit> predicate) {
		List<Fruit> filteredFruits = new ArrayList<>();
		for (Fruit fruit : fruits) {
			if (predicate.test(fruit)) {
				filteredFruits.add(fruit);
			}
		}
		return filteredFruits;
	}
}

class Fruit {
	private String name;

	private String color;

	private int weight;

	public Fruit(String name, String color, int weight) {
		this.name = name;
		this.color = color;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "Fruit{" +
				"name='" + name + '\'' +
				", color='" + color + '\'' +
				", weight=" + weight +
				'}';
	}
}

//
//	  Consumer<T>
//
//	  抽象方法：void accept(T t)
//	  用途：表示一个接受一个输入参数并没有返回值的操作。
//	  使用场景：当需要对输入参数进行一些操作，而不需要返回结果时，可以使用Consumer。
class ProcessNumbers {
	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);

		processNumbers(numbers, number -> System.out.println("Processing number: " + number));
	}

	public static void processNumbers(List<Integer> numbers, Consumer<Integer> consumer) {
		for (Integer number : numbers) {
			consumer.accept(number);
		}
	}
}
//	  BiFunction<T, U, R>、BiPredicate<T, U>、BiConsumer<T, U>
//
//	  和Function、Predicate、Consumer一样，只是一个入参变成了两个
//
//	  Mybatis框架里就有很多地方用到了Bi的，如：ResultHandler、ResultSetHandler、ParameterHandler、StatementHandler


