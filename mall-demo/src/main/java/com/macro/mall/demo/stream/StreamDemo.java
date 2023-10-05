package com.macro.mall.demo.stream;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description: jdk8Stream流
 * @Author: yfc
 * @Date: 2023/10/5 11:39
 */
public class StreamDemo {
}

/**
 * 目录
 * 1.前言
 *
 * 1.1 为什么要用Stream
 *
 * 1.2 什么是聚合操作
 *
 * 2.正文
 *
 * 2.1 Stream操作分类
 *
 * 2.2 Stream API使用
 *
 * 2.2.1 Stream 构成与创建
 *
 * 2.2.2 无状态（Stateless）操作
 *
 * 2.2.3 有状态（Stateful）操作
 *
 * 2.2.4 短路（Short-circuiting）操作
 *
 * 2.2.5 非短路（Unshort-circuiting）操作
 *
 * 3.总结
 *
 * 1.前言
 * Java 8的另一大亮点Stream，它与 java.io 包里的 InputStream 和 OutputStream 是完全不同的概念。
 *
 * Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。
 *
 * Stream API 借助于同样新出现的 Lambda 表达式，极大的提高编程效率和程序可读性。同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用 fork/join 并行方式来拆分任务和加速处理过程。
 *
 * 1.1 为什么要用Stream
 * 我个人总结有如下几个特点：
 *
 * 有高效率的并行操作
 * 有多中功能性的聚合操作
 * 函数式编程，使代码更加简洁，提高编程效率
 * 1.2 什么是聚合操作
 * 举个例子，例如我们现在有一个模块的列表需要做如下处理：
 *
 * 客户每月平均消费金额
 * 最昂贵的在售商品
 * 本周完成的有效订单（排除了无效的）
 * 取十个数据样本作为首页推荐
 * 以上这些操作，你可以理解为就是对一个列表集合的聚合操作啦，类似于SQL里面的（count（）、sum(）、avg（）....）！
 *
 * 有一些操作，有人可能会说，可以在SQL语句中完成过滤分类！首先不说SQL不能实现的功能，即使SQL能够实现，但是数据库毕竟是用来读写数据的，主要功能是用于数据落地存储的。并不是用来做大量的逻辑处理的，所以不能为了图方便，而忽略了性能方面的损耗！所以，相比之下，有一些列表操作我们必须在程序中做逻辑处理！那如果我们用之前的java处理方式，得像如下操作一样：
 *
 * for(int i=0;i<10;i++){
 *     if(....){
 *         //内部做一系列的逻辑判断处理
 *         //也
 *         //许
 *         //有
 *         //这
 *         //么
 *         //多
 *         //行
 *         //还
 *         //不
 *         //止
 *     }else{
 *         //吧啦吧啦吧啦.......
 *     }
 *
 * }
 *
 * 那如果用Stream来处理的话，可能就只有如下简单几行：
 *
 * list.stream().filter().limit(10).foreach();
 * 所以，代码不仅简洁了，阅读起来也会很是方便！
 *
 * 2.正文
 * 2.1 Stream操作分类
 * Stream的操作可以分为两大类：中间操作、终结操作
 *
 * 中间操作可分为：
 *
 * 无状态（Stateless）操作：指元素的处理不受之前元素的影响
 * 有状态（Stateful）操作：指该操作只有拿到所有元素之后才能继续下去
 * 终结操作可分为：
 *
 * 短路（Short-circuiting）操作：指遇到某些符合条件的元素就可以得到最终结果
 * 非短路（Unshort-circuiting）操作：指必须处理完所有元素才能得到最终结果
 * Stream结合具体操作，大致可分为如下图所示：
 *
 *
 *
 * 2.2 Stream API使用
 * 接下来，我们将按各种类型的操作，对一些常用的功能API进行一一讲解：
 *
 * 2.2.1 Stream 构成与创建
 * 2.2.1.1 流的构成
 *
 * 当我们使用一个流的时候，通常包括三个基本步骤：
 *
 * 获取一个数据源（source）→ 数据转换 → 执行操作获取想要的结果，每次转换原有 Stream 对象不改变，返回一个新的 Stream 对象（可以有多次转换），这就允许对其操作可以像链条一样排列，变成一个管道。
 *
 * 如下图所示：
 *
 *
 *
 * 2.2.1.2 流的创建
 *
 * 通过 java.util.Collection.stream() 方法用集合创建流
 * List<String> list = Arrays.asList("hello","world","stream");
 * //创建顺序流
 * Stream<String> stream = list.stream();
 * //创建并行流
 * Stream<String> parallelStream = list.parallelStream();
 * 使用java.util.Arrays.stream(T[] array)方法用数组创建流
 * String[] array = {"h", "e", "l", "l", "o"};
 * Stream<String> arrayStream = Arrays.stream(array);
 * Stream的静态方法：of()、iterate()、generate()
 * Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 6);
 *
 * Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 2).limit(3);
 * stream2.forEach(System.out::println);
 *
 * Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
 * stream3.forEach(System.out::println)
 *
 *
 * //输出结果如下：
 *
 * 0
 * 2
 * 4
 * 0.9620319103852426
 * 0.8303672905658537
 * 0.09203215202737569
 *
 * stream和parallelStream的简单区分
 * stream是顺序流，由主线程按顺序对流执行操作，而parallelStream是并行流，内部以多线程并行执行的方式对流进行操作，需要注意使用并行流的前提是流中的数据处理没有顺序要求（会乱序，即使用了forEachOrdered）。例如筛选集合中的奇数，两者的处理不同之处：
 *
 *
 *
 * 当然，除了直接创建并行流，还可以通过parallel()把顺序流转换成并行流：
 *
 * Optional<Integer> findFirst = list.stream().parallel().filter(x->x>4).findFirst();
 * 2.2.2 无状态（Stateless）操作
 *  filter：筛选，是按照一定的规则校验流中的元素，将符合条件的元素提取到新的流中的操作。
 * Stream<T> filter(Predicate<? super T> predicate);
 * 流程解析图如下：
 *
 *
 *
 * 举个栗子：
 *
 * public static void main(String[] args) {
 *     List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2);
 *     Stream<Integer> stream = list.stream();
 *     stream.filter(x -> x > 5).forEach(System.out::println);
 * }
 *
 *
 *
 * //结果如下：
 *
 * 6
 * 7
 * 8
 * 映射(map、flatMap、peek)
 * ①map：一个元素类型为 T 的流转换成元素类型为 R 的流，这个方法传入一个Function的函数式接口，接收一个泛型T，返回泛型R，map函数的定义，返回的流，表示的泛型是R对象；
 *
 * 简言之：将集合中的元素A转换成想要得到的B
 *
 * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
 * 流程解析图如下：
 *
 *
 *
 * 举个栗子：
 *
 * //使用的People对象
 * public class People {
 *     private String name;
 *     private int age;
 *     ...省略get,set方法
 * }
 *
 * //将String转化为People对象
 * Stream.of("小王:18","小杨:20").map(new Function<String, People>() {
 *      @Override
 *      public People apply(String s) {
 *          String[] str = s.split(":");
 *          People people = new People(str[0],Integer.valueOf(str[1]));
 *          return people;
 *      }
 *  }).forEach(people-> System.out.println("people = " + people));
 * }
 *
 * 或如下（众多姿势，任君选择）：
 *
 * List<String> output = wordList.stream().
 * map(String::toUpperCase).
 * collect(Collectors.toList());
 * ②flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
 *
 * 简言之：与Map功能类似，区别在于将结合A的流转换成B流
 *
 * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
 * 流程解析图如下：
 *
 *
 *
 * 举个栗子：
 *
 * public static void main(String[] args) {
 *     List<String> list1 = Arrays.asList("m,k,l,a", "1,3,5,7");
 *     List<String> listNew = list1.stream().flatMap(s -> {
 *         // 将每个元素转换成一个stream
 *         String[] split = s.split(",");
 *         Stream<String> s2 = Arrays.stream(split);
 *         return s2;
 *     }).collect(Collectors.toList());
 *
 *     System.out.println("处理前的集合：" + list1);
 *     System.out.println("处理后的集合：" + listNew);
 * }
 *
 * //结果如下：
 * //这个结果的引号是不存在的，为了方便阅读，我手动添加的
 * 处理前的集合：["m,k,l,a", "1,3,5,7"]
 * 处理后的集合：["m", "k", "l", "a", "1", "3", "5", "7"]
 *
 * ③peek：peek 操作接收的是一个 Consumer<T> 函数。顾名思义 peek 操作会按照 Consumer<T> 函数提供的逻辑去消费流中的每一个元素，同时有可能改变元素内部的一些属性。
 *
 * Stream<T> peek(Consumer<? super T> action);
 * 这里我们要提一下这个 Consumer<T> ，以理解什么是消费。
 *
 * Consumer<T> 是一个函数接口。一个抽象方法 void accept(T t) 意为接受一个 T 类型的参数并将其消费掉。其实消费给我的感觉就是 “用掉” ，自然返回的就是 void 。通常“用掉” T 的方式为两种：
 *
 * T 本身的 void 方法 比较典型的就是 setter 。
 *
 * 把 T 交给其它接口（类）的 void 方法进行处理 比如我们经常用的打印一个对象 System.out.println(T)
 *
 * 操作流程解析图如下：
 *
 *
 *
 * 下面我们来看个栗子：
 *
 * Stream<String> stream = Stream.of("hello", "felord.cn");
 * stream.peek(System.out::println);
 * 执行之后，控制台并没有输出任何字符串！纳尼？？
 *
 * 这是因为流的生命周期有三个阶段：
 *
 * 起始生成阶段。
 *
 * 中间操作会逐一获取元素并进行处理。可有可无。所有中间操作都是惰性的，因此，流在管道中流动之前，任何操作都不会产生任何影响。
 *
 * 终端操作。通常分为 最终的消费 （foreach 之类的）和 归纳 （collect）两类。还有重要的一点就是终端操作启动了流在管道中的流动。
 *
 * 所以，上面的代码是因为缺少了终端操作，因此，我们改成如下即可：
 *
 * Stream<String> stream = Stream.of("hello", "felord.cn");
 * stream.peek(System.out::println).collect(Collectors.toList());
 *
 * //控制台打印内容如下：
 * hello
 * felord.cn
 * 重点：peek VS map
 *
 * 他们最大的区别是：
 *
 * peek 操作 一般用于不想改变流中元素本身的类型或者只想元素的内部状态时；
 *
 * 而 map 则用于改变流中元素本身类型，即从元素中派生出另一种类型的操作。
 *
 * ④mapToInt、mapToLong、mapToDouble、flatMapToDouble、flatMapToInt、flatMapToLong
 *
 * 以上这些操作是map和flatMap的特例版，也就是针对特定的数据类型进行映射处理。其对应的方法接口如下：
 *
 * IntStream mapToInt(ToIntFunction<? super T> mapper);
 *
 * LongStream mapToLong(ToLongFunction<? super T> mapper);
 *
 * DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
 *
 * IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
 *
 * LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
 *
 * DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
 * 此处就不全部单独说明了，取一个操作举例说明一下其用法：
 *
 * Stream<String> stream = Stream.of("hello", "felord.cn");
 * stream.mapToInt(s->s.length()).forEach(System.out::println);
 *
 *
 * //输出结果
 * 5
 * 9
 * 并且这些指定类型的流，还有另外一些常用的方法，也是很好用的，可以参考：IntStream、LongStream、DoubleStream
 *
 * 无序化（unordered）
 * unordered()操作不会执行任何操作来显式地对流进行排序。它的作用是消除了流必须保持有序的约束，从而允许后续操作使用不必考虑排序的优化。
 *
 * 举个栗子：
 *
 * public static void main(String[] args) {
 * 	 Stream.of(5, 1, 2, 6, 3, 7, 4).unordered().forEach(System.out::println);
 *      Stream.of(5, 1, 2, 6, 3, 7,4).unordered().parallel().forEach(System.out::println);
 * }
 *
 *
 * //两次输出结果对比（方便比较，写在一起）
 * 第一遍：          第二遍：
 * //第一行代码输出   //第一行代码输出
 * 5                 5
 * 1                 1
 * 2                 2
 * 6                 6
 * 3                 3
 * 7                 7
 * 4                 4
 *
 * //第二行代码输出   //第二行代码输出
 * 3                 3
 * 6                 6
 * 4                 7
 * 7                 5
 * 2                 4
 * 1                 1
 * 5                 2
 *
 *
 * 以上结果，可以看到，虽然用了unordered()，但是第一个循环里的数据顺序并没有被打乱；是不是很好奇？
 *
 * 您可以在Java 8文档中有一下一段内容：
 *
 * 对于顺序流，顺序的存在与否不会影响性能，只影响确定性。如果流是顺序的，则在相同的源上重复执行相同的流管道将产生相同的结果;
 *
 * 如果是非顺序流，重复执行可能会产生不同的结果。 对于并行流，放宽排序约束有时可以实现更高效的执行。
 *
 * 在流有序时, 但用户不特别关心该顺序的情况下，使用 unordered 明确地对流进行去除有序约束可以改善某些有状态或终端操作的并行性能。
 *
 * 2.2.3 有状态（Stateful）操作
 * distinct：返回由该流的不同元素组成的流（根据 Object.equals(Object)）；distinct（）使用hashCode（）和equals（）方法来获取不同的元素。因此，我们的类必须实现hashCode（）和equals（）方法。
 * Stream<T> distinct();
 * 简言之：就是去重；下面看下流程解析图：
 *
 *
 *
 * 举个栗子：
 *
 * Stream<String> stream = Stream.of("1", "3","4","10","4","6","23","3");
 * stream.distinct().forEach(System.out::println);
 *
 *
 * //输出
 * 1
 * 3
 * 4
 * 10
 * 6
 * 23
 * 可以发现，重复的数字会被剔除掉！那么如果需要对自定义的对象进行过滤，则需要重写对象的equals方法即可 ！
 *
 * 另外有一个细节可以看到，去重之后还是按照原流中的排序顺序输出的，所以是有序的！
 *
 * sorted：返回由该流的元素组成的流，并根据自然顺序排序
 * 该接口有两种形式：无参和有参数，如：
 *
 * Stream<T> sorted();
 *
 * Stream<T> sorted(Comparator<? super T> comparator);
 * 那区别其实就在于：传入比较器的参数，可以自定义这个比较器，即自定义比较规则。
 *
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * stream.sorted().forEach(System.out::println);
 *
 *
 * //输出
 * 1
 * 3
 * 4
 * 8
 * 9
 * 10
 * 16
 * limit：获取流中n个元素返回的流
 * 这个很好理解，和mysql的中的limit函数一样的效果，返回指定个数的元素流。
 *
 * Stream<T> limit(long maxSize);
 * 流程解析图如下：
 *
 *
 *
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * stream.limit(3).forEach(System.out::println);
 *
 *
 * //输出
 * 3
 * 1
 * 10
 * skip：在丢弃流的第一个n元素之后，返回由该流的其余元素组成的流。
 * 简言之：跳过第n个元素，返回其后面的元素流；
 *
 * Stream<T> skip(long n);
 * 流程解析图：
 *
 *
 *
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * stream.skip(3).forEach(System.out::println);
 *
 *
 * //输出
 * 16
 * 8
 * 4
 * 9
 * 2.2.4 短路（Short-circuiting）操作
 * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true;
 * boolean anyMatch(Predicate<? super T> predicate);
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * System.out.println("result="+stream.anyMatch(s->s==2));
 *
 * //输出
 * result=false
 *
 * allMatch：Stream 中全部元素符合传入的 predicate，返回 true;
 * boolean allMatch(Predicate<? super T> predicate);
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * System.out.println("result="+stream.allMatch(s->s>=1));
 *
 *
 * //输出
 * result=true
 * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true.
 * boolean noneMatch(Predicate<? super T> predicate);
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * System.out.println("result="+stream.noneMatch(s -> s>=17 ));
 *
 *
 * //输出
 * result=true
 * findFirst：用于返回满足条件的第一个元素（但是该元素是封装在Optional类中）
 * 关于Optional可以点这里：【Java 8系列】Java开发者的判空利器 -- Optional
 *
 * Optional<T> findFirst();
 * 举个栗子：
 *
 * Stream<Integer> stream = Stream.of(3,1,10,16,8,4,9);
 * System.out.println("result="+stream.findFirst().get());
 *
 * //输出
 * result=3
 *
 *
 * //当然，我们还可以结合filter处理
 * System.out.println("result="+stream.filter(s-> s > 3).findFirst().get());
 *
 * //输出
 * result=10
 * findAny：返回流中的任意元素（但是该元素也是封装在Optional类中）
 * Optional<T> findAny();
 * 举个栗子：
 *
 * List<String> strAry = Arrays.asList( "Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");
 *
 * String result = strAry.parallelStream().filter(s->s.startsWith("J")).findAny().get();
 * System.out.println("result = " + result);
 *
 * //输出
 * result = Jill
 * 通过多次执行，我们会发现，其实findAny会每次按顺序返回第一个元素。那这个时候，可能会认为findAny与findFirst方法是一样的效果。其实不然，findAny()操作，返回的元素是不确定的，对于同一个列表多次调用findAny()有可能会返回不同的值。使用findAny()是为了更高效的性能。如果是数据较少，串行地情况下，一般会返回第一个结果，如果是并行的情况，那就不能确保是第一个。
 *
 * 我们接着看下面这个例子：
 *
 * List<String> strAry = Arrays.asList( "Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");
 *
 * String result = strAry.parallelStream().filter(s->s.startsWith("J")).findAny().get();
 * System.out.println("result = " + result);
 *
 * //输出
 * result = Jill
 * 或
 * result = Julia
 * 如此可见，在并行流里，findAny可就不是只返回第一个元素啦！
 *
 * 2.2.5 非短路（Unshort-circuiting）操作
 * forEach：该方法接收一个Lambda表达式，然后在Stream的每一个元素上执行该表达式
 * 可以理解为我们平时使用的for循环，但是较于for循环，又略有不同！咱们待会再讲。
 *
 * void forEach(Consumer<? super T> action);
 * 举个栗子：
 *
 * List<String> strAry = Arrays.asList( "Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");
 *
 * strAry.stream().forEach(s-> {
 * 			if("Jack".equalsIgnoreCase(s)) System.out.println(s);
 *                });
 *
 * //输出
 * Jack
 * 那如果我们把 "Jack"用在循环外部用一个变量接收，如下操作：
 *
 * String name = "Jack";
 * strAry.stream().forEach(s-> {
 * 	if(name.equalsIgnoreCase(s)) name = "Jackson";
 * });
 * 那么此时编辑器则会爆红，
 *
 *
 *
 * 因为lambda中，使用的外部变量必须是最终的，不可变的，所以如果我们想要对其进行修改，那是不可能的！如果必须这么使用，可以将外部变量，移至表达式之中使用才行！
 *
 * forEachOrdered：该方法接收一个Lambda表达式，然后按顺序在Stream的每一个元素上执行该表达式
 * void forEachOrdered(Consumer<? super T> action);
 * 该功能其实和forEach是很相似的，也是循环操作！那唯一的区别，就在于forEachOrdered是可以保证循环时元素是按原来的顺序逐个循环的！
 *
 * 但是，也不尽其然！因为有的时候，forEachOrdered也是不能百分百保证有序！例如下面这个例子：
 *
 * Stream.of("AAA,","BBB,","CCC,","DDD,").parallel().forEach(System.out::print);
 * System.out.println("\n______________________________________________");
 * Stream.of("AAA,","BBB,","CCC,","DDD").parallel().forEachOrdered(System.out::print);
 * System.out.println("\n______________________________________________");
 * Stream.of("DDD,","AAA,","BBB,","CCC").parallel().forEachOrdered(System.out::print);
 *
 * //输出为：
 *
 * CCC,DDD,BBB,AAA,
 * ______________________________________________
 * AAA,BBB,CCC,DDD
 * ______________________________________________
 * DDD,AAA,BBB,CCC
 * 可以看到，在并行流时，由于是多线程处理，其实还是无法保证有序操作的！
 *
 * toArray：返回包含此流元素的数组；当有参数时，则使用提供的generator函数分配返回的数组，以及分区执行或调整大小可能需要的任何其他数组
 * Object [] toArray();
 *
 * <A> A[] toArray(IntFunction<A[]> generator);
 * 举个栗子：
 *
 * List<String> strList = Arrays.asList( "Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");
 *
 * Object [] strAryNoArg = strList.stream().toArray();
 * String [] strAry = strList.stream().toArray(String[]::new);
 * reduce：方法接收一个函数作为累加器，数组中的每个值（从左到右）开始缩减，最终计算为一个值
 * 通过字面意思，可能比较难理解是个什么意思？下面我们先看一个图，熟悉一下这个接口的操作流程是怎样的：
 *
 *
 *
 * 该接口含有3种调用方式：
 *
 * Optional<T> reduce(BinaryOperator<T> accumulator);
 *
 * T reduce(T identity, BinaryOperator<T> accumulator);
 *
 * <U> U reduce(U identity,
 *                  BiFunction<U, ? super T, U> accumulator,
 *                  BinaryOperator<U> combiner);
 *
 *
 * //以及参数的定义结构
 * @FunctionalInterface
 * public interface BinaryOperator<T> extends BiFunction<T,T,T> {
 * 	//两个静态方法，先进行忽略
 * }
 *
 * @FunctionalInterface
 * public interface BiFunction<T, U, R> {
 * 	R apply(T t, U u);
 * 	//一个默认方法，先进行忽略
 * }
 *
 * 下面举几个栗子，看看具体效果：
 *
 * （一）.先以1个参数的接口为例
 *
 * 为了方便理解，先看下内部的执行效果代码：
 *
 * boolean foundAny = false;
 * T result = null;
 * for (T element : this stream) {
 *     if (!foundAny) {
 *         foundAny = true;
 *         result = element;
 *     }
 *     else
 *         result = accumulator.apply(result, element);
 * }
 * return foundAny ? Optional.of(result) : Optional.empty();
 * 再看下具体栗子：
 *
 * List<Integer> num = Arrays.asList(1, 2, 4, 5, 6, 7);
 *
 * *原接口一比一原汁原味写法*
 * Integer integer = num.stream().reduce(new BinaryOperator<Integer>() {
 *     @Override
 *     public Integer apply(Integer a, Integer b) {
 *     	System.out.println("x:"+a);
 *         return a + b;
 *     }
 * }).get();
 * System.out.println("resutl:"+integer);
 *
 *
 * *等效写法一*
 * Integer result = num.stream().reduce((x, y) -> {
 *     System.out.println("x:"+x);
 *     return x + y;
 * }).get();
 * System.out.println("resutl:"+result);
 *
 *
 * *等效的普通写法*
 * boolean flag = false;
 * int temp = 0;
 * for (Integer integer : num) {
 * 	if(!flag){
 * 		temp = integer;
 * 		flag = true;    * 	}else {
 * 		System.out.println("x:"+temp);
 * 		temp += integer;    * 	}
 * }
 *
 * System.out.println("resutl:"+temp);
 *
 * 执行结果都是：
 *
 * x:1
 * x:3
 * x:7
 * x:12
 * x:18
 * resutl:25
 * （二）再以2个参数的接口为例
 *
 * 先看下内部的执行效果代码：
 *
 * T result = identity;
 * for (T element : this stream){
 *     result = accumulator.apply(result, element)
 * }
 * return result;
 * 在看具体栗子：
 *
 * List<Integer> num = Arrays.asList(1, 2, 4, 5, 6, 7);
 *
 * *一比一原汁原味写法*
 * Integer integer = num.stream().reduce(1,new BinaryOperator<Integer>() {
 *     @Override
 *     public Integer apply(Integer a, Integer b) {
 *     	System.out.println("a="+a);
 *         return a + b;
 *     }
 * });
 * System.out.println("resutl:"+integer);
 *
 *
 * *普通for循环写法*
 * int temp = 1;
 * for (Integer integer : num) {
 * 	System.out.println("a="+temp);
 * 	temp += integer;
 * }
 *
 * System.out.println("resutl:"+temp);
 *
 * 输出结果都是：
 *
 * a=1
 * a=2
 * a=4
 * a=8
 * a=13
 * a=19
 * resutl:26
 * （三）最后3个参数的接口为例
 *
 * 这个接口的内部执行效果，其实和2个参数的几乎一致。那么第三个参数是啥呢？这是一个combiner组合器；
 *
 * 组合器需要和累加器的返回类型需要进行兼容，combiner组合器的方法主要用在并行操作中
 *
 * 在看具体栗子：
 *
 * List<Integer> num = Arrays.asList(1, 2, 3, 4, 5, 6);
 * List<Integer> other = new ArrayList<>();
 * other.addAll(Arrays.asList(7,8,9,10));
 *
 * num.stream().reduce(other,
 * 	(x, y) -> { //第二个参数
 * 		System.out.println(JSON.toJSONString(x));
 *         x.add(y);
 *         return x;
 *     },
 * 	(x, y) -> { //第三个参数
 *         System.out.println("并行才会出现："+JSON.toJSONString(x));
 *         return x;
 * });
 *
 *
 *
 *
 * //输出结果：
 * [7,8,9,10,1]
 * [7,8,9,10,1,2]
 * [7,8,9,10,1,2,3]
 * [7,8,9,10,1,2,3,4]
 * [7,8,9,10,1,2,3,4,5]
 * [7,8,9,10,1,2,3,4,5,6]
 *
 * 我们再讲串行流改成并行流，看下会出现什么结果：
 *
 * List<Integer> num = Arrays.asList( 4, 5, 6);
 * List<Integer> other = new ArrayList<>();
 * other.addAll(Arrays.asList(1,2,3));
 *
 * num.parallelStream().reduce(other,
 * 	(x, y) -> { //第二个参数
 *         x.add(y);
 *         System.out.println(JSON.toJSONString(x));
 *         return x;
 *     },
 * 	(x, y) -> { //第三个参数
 * 		x.addAll(y);
 * 		System.out.println("结合："+JSON.toJSONString(x));
 * 		return x;
 * });
 *
 *
 * //输出结果
 * //第一遍
 * [1,2,3,4,5,6]
 * [1,2,3,4,5,6]
 * [1,2,3,4,5,6]
 * 结合：[1,2,3,4,5,6,1,2,3,4,5,6]
 * 结合：[1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6]
 *
 * //第二遍
 * [1,2,3,4,6]
 * [1,2,3,4,6]
 * [1,2,3,4,6]
 * 结合：[1,2,3,4,6,1,2,3,4,6]
 * 结合：[1,2,3,4,6,1,2,3,4,6,1,2,3,4,6,1,2,3,4,6]
 *
 * //第三遍
 * [1,2,3,5,4,6]
 * [1,2,3,5,4,6]
 * [1,2,3,5,4,6]
 * 结合：[1,2,3,5,4,6,1,2,3,5,4,6]
 * 结合：[1,2,3,5,4,6,1,2,3,5,4,6,1,2,3,5,4,6,1,2,3,5,4,6]
 *
 * 我们会发现每个结果都是乱序的，并且多执行几次，都会出现不同的结果。并且第三个参数组合器内的代码也得到了执行！！
 *
 * 这就是因为并行时，使用多线程时顺序性没有保障所产生的结果。通过实践，可以看到：组合器的作用，其实是对参数2中的各个线程，产生的结果进行了再一遍的归约操作！
 *
 * 并且仔细看第二遍的执行结果：每一组都少了一1个值！！！
 *
 * 所以，对于并行流parallelStream操作，必须慎用！！
 *
 * collect：称为收集器，是一个终端操作,它接收的参数是将流中的元素累积到汇总结果的各种方式
 * <R, A> R collect(Collector<? super T, A, R> collector);
 *
 * <R> R collect(Supplier<R> supplier,
 *                   BiConsumer<R, ? super T> accumulator,
 *                   BiConsumer<R, R> combiner);
 * 第一种方式会比较经常使用到，也比较方便使用，现在先看一看里面常用的一些方法：
 *
 * 工厂方法
 *
 * 返回类型
 *
 * 用于
 *
 * toList
 *
 * List<T>
 *
 * 把流中所有元素收集到List中
 *
 * 示例:List<Menu> menus=Menu.getMenus.stream().collect(Collectors.toList());
 *
 * toSet
 *
 * Set<T>
 *
 * 把流中所有元素收集到Set中,删除重复项
 *
 * 示例:Set<Menu> menus=Menu.getMenus.stream().collect(Collectors.toSet());
 *
 * toCollection
 *
 * Collection<T>
 *
 * 把流中所有元素收集到给定的供应源创建的集合中
 *
 * 示例:ArrayList<Menu> menus=Menu.getMenus.stream().collect(Collectors.toCollection(ArrayList::new));
 *
 * Counting
 *
 * Long
 *
 * 计算流中元素个数
 *
 * 示例:Long count=Menu.getMenus.stream().collect(counting);
 *
 * SummingInt
 *
 * Integer
 *
 * 对流中元素的一个整数属性求和
 *
 * 示例:Integer count=Menu.getMenus.stream().collect(summingInt(Menu::getCalories));
 *
 * averagingInt
 *
 * Double
 *
 * 计算流中元素integer属性的平均值
 *
 * 示例:Double averaging=Menu.getMenus.stream().collect(averagingInt(Menu::getCalories));
 *
 * Joining
 *
 * String
 *
 * 连接流中每个元素的toString方法生成的字符串
 *
 * 示例:String name=Menu.getMenus.stream().map(Menu::getName).collect(joining(“, ”));
 *
 * maxBy
 *
 * Optional<T>
 *
 * 一个包裹了流中按照给定比较器选出的最大元素的optional
 * 如果为空返回的是Optional.empty()
 *
 * 示例:Optional<Menu> fattest=Menu.getMenus.stream().collect(maxBy(Menu::getCalories));
 *
 * minBy
 *
 * Optional<T>
 *
 * 一个包裹了流中按照给定比较器选出的最小元素的optional
 * 如果为空返回的是Optional.empty()
 *
 * 示例: Optional<Menu> lessest=Menu.getMenus.stream().collect(minBy(Menu::getCalories));
 *
 * Reducing
 *
 * 归约操作产生的类型
 *
 * 从一个作为累加器的初始值开始,利用binaryOperator与流中的元素逐个结合,从而将流归约为单个值
 *
 * 示例:int count=Menu.getMenus.stream().collect(reducing(0,Menu::getCalories,Integer::sum));
 *
 * collectingAndThen
 *
 * 转换函数返回的类型
 *
 * 包裹另一个转换器,对其结果应用转换函数
 *
 * 示例:Int count=Menu.getMenus.stream().collect(collectingAndThen(toList(),List::size));
 *
 * groupingBy
 *
 * Map<K,List<T>>
 *
 * 根据流中元素的某个值对流中的元素进行分组,并将属性值做为结果map的键
 *
 * 示例:Map<Type,List<Menu>> menuType=Menu.getMenus.stream().collect(groupingby(Menu::getType));
 *
 * partitioningBy
 *
 * Map<Boolean,List<T>>
 *
 * 根据流中每个元素应用谓语的结果来对项目进行分区
 *
 * 示例:Map<Boolean,List<Menu>> menuType=Menu.getMenus.stream().collect(partitioningBy(Menu::isType)
 *
 * 第二种方式看起来跟reduce的三个入参的方法有点类似，也可以用来实现filter、map等操作！
 *
 * 流程解析图如下：
 *
 *
 *
 * 举个栗子：
 *
 * List<Integer> numList = Arrays.asList(1,2,3);
 *
 * numList.stream()
 *         .collect(()->{ //第一个参数
 *         	//构造器
 *             System.out.println("构造器，返回一个你想用到的任意起始对象！此处返回一个空List为例");
 *             System.out.println();
 *             return new ArrayList();
 *         }, (a, b) -> {  //第二个参数
 *             synchronized (Java8DemoServiceImpl.class) {  //加锁是为了并行流下方便查看打印结果
 *                 System.out.println("累加器");
 *                 a.forEach(item -> System.out.println("a:" + item));
 *                 System.out.println("b:" + b);
 *
 *                 a.add(b);
 *                 //换行
 *                 System.out.println();
 *             }
 *         }, (a, b) -> {  //第三个参数
 *             synchronized (Java8DemoServiceImpl.class) {  //加锁是为了并行流下方便查看打印结果
 *                 System.out.println("合并器");
 *                 System.out.println("a:" + JSON.toJSONString(a) + " , " + "b:" + JSON.toJSONString(b));
 *
 *                 a.addAll(b);
 *                 System.out.println();  //为了换行方便查看打印
 *             }
 *         })
 *         .forEach(item -> System.out.println("最终结果项:" + item));
 *
 * 运行结果：
 *
 * 构造器，返回一个你想用到的任意起始对象！此处返回一个空List为例
 *
 * 累加器
 * b:1
 *
 * 累加器
 * a:1
 * b:2
 *
 * 累加器
 * a:1
 * a:2
 * b:3
 *
 * 最终结果项:1
 * 最终结果项:2
 * 最终结果项:3
 *
 * 如果把上述流换成并行流，会得到如下一种结果：
 *
 * 构造器，返回一个你想用到的任意起始对象！此处返回一个空List为例
 * 构造器，返回一个你想用到的任意起始对象！此处返回一个空List为例
 *
 * 构造器，返回一个你想用到的任意起始对象！此处返回一个空List为例
 *
 * 累加器
 *
 * b:2
 *
 * 累加器
 * b:1
 *
 * 累加器
 * b:3
 *
 * 合并器
 * a:[2] , b:[3]
 *
 * 合并器
 * a:[1] , b:[2,3]
 *
 * 最终结果项:1
 * 最终结果项:2
 * 最终结果项:3
 *
 * 可以看到，根据流内的元素个数n，起了n个线程，同时分别执行了构造器、累加器、合并器内代码！与reduce的行为方式基本一致！
 *
 * max：根据提供的Comparator返回此流的最大元素
 * Optional<T> max(Comparator<? super T> comparator);
 * 举个栗子：
 *
 * List<Integer> num = Arrays.asList( 4, 5, 6);
 * num.stream().max(Integer::compareTo).ifPresent(System.out::println);
 *
 * //输出
 * 6
 * min：根据提供的Comparator返回此流的最小元素
 * Optional<T> min(Comparator<? super T> comparator);
 * 举个栗子：
 *
 * List<Integer> num = Arrays.asList( 4, 5, 6);
 * num.stream().min(Integer::compareTo).ifPresent(System.out::println);
 *
 * //输出
 * 4
 * count：返回此流中的元素计数
 * long count();
 * 举个栗子：
 *
 * List<Integer> num = Arrays.asList( 4, 5, 6);
 * System.out.println(num.stream().count());
 *
 * //输出
 * 3
 * 3.总结
 * 此处给正在学习的朋友两个小提示：
 *
 * 1、对于流的各种操作所属分类，还不够熟悉的，可以直接进入方法的源码接口内，如下，是可以查看到类型说明的：
 *
 *
 *
 * 2、对于并行流stream().parallel()、parallelStream()的使用，须慎重使用！使用前须考虑其不确定因素和无序性，考虑多线程所带来的复杂性！！
 *
 * 2020年已近年末，再过几天就要步入新年啦！工作之余，耗时几天，终于写完了这篇博文！分享不易，希望感兴趣的朋友，可以留言讨论，点赞收藏！
 * ————————————————
 * 版权声明：本文为CSDN博主「善良勤劳勇敢而又聪明的老杨」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/yy339452689/article/details/110956119
 */








//一、创建Stream
//		从集合创建Stream
//		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//		Stream<Integer> stream = numbers.stream();
//		1
//		2
//		从数组创建Stream
//		int[] array = {1, 2, 3, 4, 5};
//		IntStream stream = Arrays.stream(array);
//		1
//		2
//		使用Stream.of()创建Stream
//		Stream stream = Stream.of(“a”, “b”, “c”);
//
//		二、中间操作
//		filter()：过滤满足条件的元素。
//		案例1
//		List<Integer> evenNumbers = numbers.stream()
//		.filter(n -> n % 2 == 0)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		案例2
////原方法
//public static void main(String[] args) {
//
//		List<String> lines = Arrays.asList("spring", "hibernate", "neo4j");
//		List<String> result = getFilterOutput(lines, "neo4j");
//		for (String temp : result) {
//		System.out.println(temp);
//		}
//
//		}
//
//private static List<String> getFilterOutput(List<String> lines, String filter) {
//		List<String> result = new ArrayList<>();
//		for (String line : lines) {
//		if (!filter.equals(line)) {
//		result.add(line);
//		}
//		}
//		return result;
//		}
//
//
////stream优化
//public static void main(String[] args) {
//		List<String> lines = Arrays.asList("spring", "hibernate", "neo4j");
//		List<String> result = lines.stream()                // 转化为一个流
//		.filter(line -> !"neo4j".equals(line))     // 排除 'String'
//		.collect(Collectors.toList());              // 吧输出流收集回List中
//		result.forEach(System.out::println);                //输出 : spring, hibernate
//		}
//
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		案例3
//		package com.god.genius.baisc.jdk.jdk8.streamFilter.student;
//
//
//		import java.time.LocalDate;
//		import java.util.List;
//
//public class StudentInfo implements Comparable<StudentInfo> {
//
//	//名称
//	private String name;
//
//	//性别 true男 false女
//	private Boolean gender;
//
//	//年龄
//	private Integer age;
//
//	//身高
//	private Double height;
//
//	//出生日期
//	private LocalDate birthday;
//
//	public StudentInfo(String name, Boolean gender, Integer age, Double height, LocalDate birthday){
//		this.name = name;
//		this.gender = gender;
//		this.age = age;
//		this.height = height;
//		this.birthday = birthday;
//	}
//
//	@Override
//	public String toString(){
//		String info = String.format("%s\t\t%s\t\t%s\t\t\t%s\t\t%s",this.name,this.gender.toString(),this.age.toString(),this.height.toString(),birthday.toString());
//		return info;
//	}
//
//	public static void printStudents(List<StudentInfo> studentInfos){
//		System.out.println("[姓名]\t\t[性别]\t\t[年龄]\t\t[身高]\t\t[生日]");
//		System.out.println("----------------------------------------------------------");
//		studentInfos.forEach(s->System.out.println(s.toString()));
//		System.out.println(" ");
//	}
//
//	@Override
//	public int compareTo(StudentInfo ob) {
//		return this.age.compareTo(ob.getAge());
//		//return 1;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Boolean getGender() {
//		return gender;
//	}
//
//	public void setGender(Boolean gender) {
//		this.gender = gender;
//	}
//
//	public Integer getAge() {
//		return age;
//	}
//
//	public void setAge(Integer age) {
//		this.age = age;
//	}
//
//	public Double getHeight() {
//		return height;
//	}
//
//	public void setHeight(Double height) {
//		this.height = height;
//	}
//
//	public LocalDate getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(LocalDate birthday) {
//		this.birthday = birthday;
//	}
//}
//
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		35
//		36
//		37
//		38
//		39
//		40
//		41
//		42
//		43
//		44
//		45
//		46
//		47
//		48
//		49
//		50
//		51
//		52
//		53
//		54
//		55
//		56
//		57
//		58
//		59
//		60
//		61
//		62
//		63
//		64
//		65
//		66
//		67
//		68
//		69
//		70
//		71
//		72
//		73
//		74
//		75
//		76
//		77
//		78
//		79
//		80
//		81
//		82
//		83
//		84
//		85
//		86
//		87
//		88
//		89
//		90
//		测试代码
//
//		package com.god.genius.baisc.jdk.jdk8.streamFilter.student;
//
//		import java.time.LocalDate;
//		import java.util.ArrayList;
//		import java.util.List;
//		import java.util.stream.Collectors;
//
///**
// * @author lisir
// */
//public class Demo {
//	public static void main(String[] args) {
//		List<StudentInfo> list = new ArrayList<>();
//		list.add(new StudentInfo("李白", true, 18, 1.76, LocalDate.of(2001, 3, 23)));
//		list.add(new StudentInfo("大乔", false, 18, 1.68, LocalDate.of(2001, 6, 3)));
//		list.add(new StudentInfo("凯皇", true, 19, 1.82, LocalDate.of(2000, 3, 11)));
//		list.add(new StudentInfo("小乔", false, 17, 1.61, LocalDate.of(2002, 10, 18)));
//
//		//查询所有英雄
//		StudentInfo.printStudents(list);
//		//查找18岁的英雄
//		List<StudentInfo> collect = list.stream().filter(s -> s.getAge() == 19).collect(Collectors.toList());
//		StudentInfo.printStudents(collect);
//
//		综合查询：查找身高在1.8米及以上且年龄小于19岁的英雄
//		List<StudentInfo> boys = list.stream().filter(s -> s.getGender() && s.getHeight() >= 1.66 && s.getAge() < 19).collect(Collectors.toList());
//		//输出查找结果
//		StudentInfo.printStudents(boys);
//
//		list.forEach(s -> System.out.println(s.toString()));
//		list.stream().forEach(s->System.out.println(s.toString()));
//
//	}
//}
//
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		案例4: Streams 中 filter(), findAny() 和 orElse()的用法
//public static void main(String[] args) {
//
//		List<User> userList = Arrays.asList(
//		new User("mkyong", 30),
//		new User("jack", 20),
//		new User("lawrence", 40)
//		);
//
//		User result = getStudentByName(userList, "jack");
//		System.out.println(result);
//
//		}
//
//private static User getStudentByName(List<User> userList, String name) {
//		User result = null;
//		for (User temp : userList) {
//		if (name.equals(temp.getName())) {
//		result = temp;
//		}
//		}
//		return result;
//		}
//
//public static void main(String[] args) {
//
//		List<User> userList = Arrays.asList(
//		new User("张三", 30),
//		new User("李四", 20),
//		new User("Enoch", 40)
//		);
//		User result1 = userList.stream()                        // 转化为流
//		.filter(x -> "Enoch".equals(x.getName()))       // 只过滤出"Enoch"
//		.findAny()                                      // 如果找到了就返回
//		.orElse(null);                                  // 如果找不到就返回null
//
//		System.out.println(result1);
//
//		User result2 = userList.stream()
//		.filter(x -> "Enoch".equals(x.getName()))
//		.findAny()
//		.orElse(null);
//
//		System.out.println(result2);
//
//		}
//
//public static void main(String[] args) {
//
//		List<User> userList = Arrays.asList(
//		new User("张三", 30),
//		new User("李四", 20),
//		new User("Enoch", 40)
//		);
//		User result1 = userList.stream()
//		.filter((p) -> "李四".equals(p.getName()) && 20 == p.getAge())
//		.findAny()
//		.orElse(null);
//		System.out.println("result 1 :" + result1);
//
//		//或者这样写
//		User result2 = userList.stream()
//		.filter(p -> "Enoch".equals(p.getName()) && 20 == p.getAge()).findAny()
//		.orElse(null);
//		System.out.println("result 2 :" + result2);
//		}
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		35
//		36
//		37
//		38
//		39
//		40
//		41
//		42
//		43
//		44
//		45
//		46
//		47
//		48
//		49
//		50
//		51
//		52
//		53
//		54
//		55
//		56
//		57
//		58
//		59
//		60
//		61
//		62
//		63
//		64
//		65
//		map()：对每个元素执行某种操作并返回一个新的Stream。
//		List<String> upperCaseNames = names.stream()
//		.map(String::toUpperCase)
//		.collect(Collectors.toList());
//
//public static void main(String[] args) {
//		List<User> userList = Arrays.asList(
//		new User("张三", 30),
//		new User("李四", 20),
//		new User("Enoch", 40)
//		);
//		String name = userList.stream()
//		.filter(x -> "Enoch".equals(x.getName()))
//		.map(User::getName)                        //把流转化为String,这里其实就是
//		//把一个新的事物转为另外一种事物了，类型得到了转换
//		.findAny()
//		.orElse("");
//		System.out.println("name : " + name);
//		List<String> collect = userList.stream()
//		.map(User::getName)
//		.collect(Collectors.toList());
//		collect.forEach(System.out::println);
//		}
//
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		flatMap()：将多个Stream组合成一个Stream。
//		List<Integer> flattenedList = listOfLists.stream()
//		.flatMap(List::stream)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		distinct()：去除重复元素。
//		List<Integer> distinctNumbers = numbers.stream()
//		.distinct()
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		sorted()：对元素进行排序。
//		List<Integer> sortedNumbers = numbers.stream()
//		.sorted()
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		limit()：截取Stream的前n个元素。
//		List<Integer> limitedNumbers = numbers.stream()
//		.limit(3)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		skip()：跳过Stream的前n个元素。
//		List<Integer> skippedNumbers = numbers.stream()
//		.skip(3)
//		.collect(Collectors.toList
//		1
//		2
//		3
//		三、终端操作
//		forEach()：遍历Stream中的每个元素。
//		numbers.stream()
//		.forEach(System.out::println);
//		1
//		2
//		filterLists.stream().forEach(s -> System.out.println(s));
//
//		Stream.generate(random).limit(10).forEach(System.out::println);//可传入方法
//
//		roster.stream().parallel().filter(p1.negate()).forEach(p -> t.test§);//也可以实现接口
//
//		strList.forEach(System.out::println)
//
//		// 创建出一个数组
//		List<String> strList = Arrays.asList("YangHang", "AnXiaoHei", "LiuPengFei");
//
//		strList.forEach(System.out::println);
//		1
//		2
//		3
//		4
//		首先， 我们看一下是java.lang.Iterable下的一个默认方法forEach调用的，一看到这个function包下面的被@FunctionalInterface注解声明的Consumer接口， 瞬间就了然了， 这不又是函数式编程搞的鬼么？
//		现在的问题应该很明朗了， System.out::println这段代码其实就是Consumer接口的一个实现方式啊。
//
//		具体是怎么实现，如下代码。
//
//@Test
//public void testDemo2() {
//		List<String> strList = Arrays.asList("YangHang", "AnXiaoHei", "LiuPengFei");
//
//		strList.forEach(x -> {
//		System.out.println(x);
//		});
//		}
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		然后， 我们惊喜的发现和上面的代码运行结果是一制的， 我们基本上可以断定， 上面那种写法是下面这种的一种缩写形式。 就是把你遍历出来的每一个对象都用来去调用System.out（也就是PrintStream类的一个实例）的println方法。
//		最后， 大家是不是有一个想法， 想自己写一个Consumer接口的实现类， 让foreach调用一下。
//
//public class PrintUtil {
//
//	/**
//	 * 对要遍历的元素添加add操作
//	 */
//	public void addString(String x) {
//		System.out.println(x + "add");
//	}
//}
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		然后， 我们这么来玩
//
//@Test
//public void testDemo3() {
//		List<String> strList = Arrays.asList("YangHang", "AnXiaoHei", "LiuPengFei");
//
//		strList.forEach(new PrintUtil()::addString);
//		}
//		1
//		2
//		3
//		4
//		5
//		6
//		运行一下， 果然可以的。
//		但是发现， 如果是静态方法的时候必须得用类名双冒号静态方法， 这估计是语法的一种， 注意就好。
//
//		count()：统计Stream中元素的数量。
//		long count = numbers.stream()
//		.count();
//		1
//		2
//		collect()：将Stream中的元素收集到一个集合中。
//		List<Integer> collectedNumbers = numbers.stream()
//		.collect(Collectors.toList());
//		1
//		2
//		min()：查找Stream中的最小元素。
//		Optional<Integer> minNumber = numbers.stream()
//		.min(Integer::compareTo);
//		if (minNumber.isPresent()) {
//		System.out.println("Min number: " + minNumber.get());
//		}
//		1
//		2
//		3
//		4
//		5
//		max()：查找Stream中的最大元素。
//		Optional<Integer> maxNumber = numbers.stream()
//		.max(Integer::compareTo);
//		if (maxNumber.isPresent()) {
//		System.out.println("Max number: " + maxNumber.get());
//		}
//		1
//		2
//		3
//		4
//		5
//		reduce()：对Stream中的元素进行计算。
//		Optional<Integer> sum = numbers.stream()
//		.reduce(Integer::sum);
//		if (sum.isPresent()) {
//		System.out.println("Sum: " + sum.get());
//		}
//		1
//		2
//		3
//		4
//		5
//		anyMatch()：判断Stream中是否存在满足条件的元素。
//		boolean hasEvenNumber = numbers.stream()
//		.anyMatch(n -> n % 2 == 0);
//		1
//		2
//		allMatch()：判断Stream中的所有元素是否都满足条件。
//		boolean allPositive = numbers.stream()
//		.allMatch(n -> n > 0);
//		1
//		2
//		noneMatch()：判断Stream中的所有元素是否都不满足条件。
//		boolean noneNegative = numbers.stream()
//		.noneMatch(n -> n < 0);
//		1
//		2
//		四、样例演示
//		1、处理Person对象列表，及一些常见的数据操作
//		假设我们有一个Person类，它具有姓名、年龄和性别属性。我们要处理一个包含多个Person对象的列表，并进行一些常见的数据操作。以下是使用Java 8的Stream常用用法处理这个复杂数据的示例代码：
//
//		import java.util.Arrays;
//		import java.util.List;
//		import java.util.stream.Collectors;
//
//public class Main {
//	public static void main(String[] args) {
//		List<Person> persons = Arrays.asList(
//				new Person("John", 25, Gender.MALE),
//				new Person("Jane", 30, Gender.FEMALE),
//				new Person("Tom", 20, Gender.MALE),
//				new Person("Susan", 28, Gender.FEMALE),
//				new Person("Mike", 35, Gender.MALE)
//		);
//
//		// 使用Stream的常用用法
//
//		// 1. 按年龄升序排列，并获取姓名列表
//		List<String> sortedNames = persons.stream()
//				.sorted((p1, p2) -> p1.getAge() - p2.getAge())
//				.map(Person::getName)
//				.collect(Collectors.toList());
//		System.out.println(sortedNames);
//
//		// 2. 获取所有年龄大于25岁的人的姓名列表
//		List<String> namesAbove25 = persons.stream()
//				.filter(p -> p.getAge() > 25)
//				.map(Person::getName)
//				.collect(Collectors.toList());
//		System.out.println(namesAbove25);
//
//		// 3. 统计男性和女性的人数
//		long maleCount = persons.stream()
//				.filter(p -> p.getGender() == Gender.MALE)
//				.count();
//		long femaleCount = persons.stream()
//				.filter(p -> p.getGender() == Gender.FEMALE)
//				.count();
//		System.out.println("Male count: " + maleCount);
//		System.out.println("Female count: " + femaleCount);
//
//		// 4. 按性别分组
//		Map<Gender, List<Person>> personsByGender = persons.stream()
//				.collect(Collectors.groupingBy(Person::getGender));
//		System.out.println(personsByGender);
//
//		// 5. 计算年龄的平均值
//		double averageAge = persons.stream()
//				.mapToInt(Person::getAge)
//				.average()
//				.orElse(0);
//		System.out.println("Average age: " + averageAge);
//	}
//}
//
//class Person {
//	private String name;
//	private int age;
//	private Gender gender;
//
//	public Person(String name, int age, Gender gender) {
//		this.name = name;
//		this.age = age;
//		this.gender = gender;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public Gender getGender() {
//		return gender;
//	}
//
//	@Override
//	public String toString() {
//		return "Person{" +
//				"name='" + name + '\'' +
//				", age=" + age +
//				", gender=" + gender +
//				'}';
//	}
//}
//
//enum Gender {
//	MALE, FEMALE
//}
//
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		35
//		36
//		37
//		38
//		39
//		40
//		41
//		42
//		43
//		44
//		45
//		46
//		47
//		48
//		49
//		50
//		51
//		52
//		53
//		54
//		55
//		56
//		57
//		58
//		59
//		60
//		61
//		62
//		63
//		64
//		65
//		66
//		67
//		68
//		69
//		70
//		71
//		72
//		73
//		74
//		75
//		76
//		77
//		78
//		79
//		80
//		81
//		82
//		83
//		84
//		85
//		86
//		87
//		88
//		89
//		90
//		这段代码使用了Stream的排序、映射、过滤、计数、分组和统计等常用操作，展示了如何在处理复杂数据时利用Stream提供的功能。根据实际需要，可以组合使用这些操作来完成更复杂的数据处理任务。
//
//		2、学生老师和课程，及一些常见的数据操作
//		假设我们有一个包含学生、老师和课程的复杂数据结构。学生和老师都有姓名和年龄属性，课程有名称和标签属性。我们要处理这个复杂数据结构，并进行一些常见的数据操作。以下是使用Java 8的Stream常用用法处理这个复杂数据的示例代码：
//
//		import java.util.Arrays;
//		import java.util.List;
//		import java.util.stream.Collectors;
//
//public class Main {
//	public static void main(String[] args) {
//		// 创建学生
//		Student student1 = new Student("John", 20);
//		Student student2 = new Student("Jane", 22);
//		Student student3 = new Student("Tom", 23);
//		List<Student> students = Arrays.asList(student1, student2, student3);
//
//		// 创建老师
//		Teacher teacher1 = new Teacher("Amy", 35);
//		Teacher teacher2 = new Teacher("Bob", 40);
//		List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
//
//		// 创建课程
//		Course course1 = new Course("Math", "science");
//		Course course2 = new Course("English", "language");
//		Course course3 = new Course("Physics", "science");
//		List<Course> courses = Arrays.asList(course1, course2, course3);
//
//		// 学生选课
//		student1.selectCourse(course1);
//		student1.selectCourse(course2);
//		student2.selectCourse(course2);
//		student2.selectCourse(course3);
//		student3.selectCourse(course1);
//		student3.selectCourse(course3);
//
//		// 按照年龄升序排列学生
//		List<Student> sortedStudents = students.stream()
//				.sorted((s1, s2) -> s1.getAge() - s2.getAge())
//				.collect(Collectors.toList());
//		System.out.println(sortedStudents);
//
//		// 获取学生姓名列表
//		List<String> studentNames = students.stream()
//				.map(Student::getName)
//				.collect(Collectors.toList());
//		System.out.println(studentNames);
//
//		// 获取学生所选的课程名列表
//		List<String> studentCourseNames = students.stream()
//				.flatMap(student -> student.getCourses().stream())
//				.map(Course::getName)
//				.collect(Collectors.toList());
//		System.out.println(studentCourseNames);
//
//		// 获取选择了"science"标签的课程
//		List<Course> scienceCourses = courses.stream()
//				.filter(course -> course.getLabel().equals("science"))
//				.collect(Collectors.toList());
//		System.out.println(scienceCourses);
//
//		// 根据老师的年龄分组学生
//		Map<Teacher, List<Student>> studentsByTeacher = students.stream()
//				.collect(Collectors.groupingBy(Student::getTeacher));
//		System.out.println(studentsByTeacher);
//	}
//}
//
//class Student {
//	private String name;
//	private int age;
//	private List<Course> courses;
//	private Teacher teacher;
//
//	public Student(String name, int age) {
//		this.name = name;
//		this.age = age;
//		this.courses = new ArrayList<>();
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public List<Course> getCourses() {
//		return courses;
//	}
//
//	public Teacher getTeacher() {
//		return teacher;
//	}
//
//	public void selectCourse(Course course) {
//		courses.add(course);
//		course.addStudent(this);
//	}
//
//	@Override
//	public String toString() {
//		return "Student{" +
//				"name='" + name + '\'' +
//				", age=" + age +
//				'}';
//	}
//}
//
//class Teacher {
//	private String name;
//	private int age;
//
//	public Teacher(String name, int age) {
//		this.name = name;
//		this.age = age;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	@Override
//	public String toString() {
//		return "Teacher{" +
//				"name='" + name + '\'' +
//				", age=" + age +
//				'}';
//	}
//}
//
//class Course {
//	private String name;
//	private String label;
//	private List<Student> students;
//
//	public Course(String name, String label) {
//		this.name = name;
//		this.label = label;
//		this.students = new ArrayList<>();
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public String getLabel() {
//		return label;
//	}
//
//	public List<Student> getStudents() {
//		return students;
//	}
//
//	public void addStudent(Student student) {
//		students.add(student);
//		student.teacher = student.getTeacher();
//	}
//
//	@Override
//	public String toString() {
//		return "Course{" +
//				"name='" + name + '\'' +
//				", label='" + label + '\'' +
//				'}';
//	}
//}
//
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		35
//		36
//		37
//		38
//		39
//		40
//		41
//		42
//		43
//		44
//		45
//		46
//		47
//		48
//		49
//		50
//		51
//		52
//		53
//		54
//		55
//		56
//		57
//		58
//		59
//		60
//		61
//		62
//		63
//		64
//		65
//		66
//		67
//		68
//		69
//		70
//		71
//		72
//		73
//		74
//		75
//		76
//		77
//		78
//		79
//		80
//		81
//		82
//		83
//		84
//		85
//		86
//		87
//		88
//		89
//		90
//		91
//		92
//		93
//		94
//		95
//		96
//		97
//		98
//		99
//		100
//		101
//		102
//		103
//		104
//		105
//		106
//		107
//		108
//		109
//		110
//		111
//		112
//		113
//		114
//		115
//		116
//		117
//		118
//		119
//		120
//		121
//		122
//		123
//		124
//		125
//		126
//		127
//		128
//		129
//		130
//		131
//		132
//		133
//		134
//		135
//		136
//		137
//		138
//		139
//		140
//		141
//		142
//		143
//		144
//		145
//		146
//		147
//		148
//		149
//		150
//		151
//		152
//		153
//		154
//		155
//		156
//		157
//		158
//		159
//		160
//		161
//		162
//		163
//		164
//		165
//		166
//		167
//		这段代码演示了如何使用Stream对包含学生、老师和课程的复杂数据进行处理。它展示了在使用Stream时常见的一些操作，如排序、映射、过滤、分组等。根据实际需求，你可以在此基础上进一步扩展和优化数据操作。
//
//		3、公司部门用户和商品订单，及一些常见的数据操作
//		import java.util.ArrayList;
//		import java.util.Arrays;
//		import java.util.List;
//		import java.util.Map;
//		import java.util.stream.Collectors;
//
//class User {
//	private String name;
//	private String company;
//	private String department;
//
//	public User(String name, String company, String department) {
//		this.name = name;
//		this.company = company;
//		this.department = department;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public String getCompany() {
//		return company;
//	}
//
//	public String getDepartment() {
//		return department;
//	}
//}
//
//class Order {
//	private String product;
//	private int quantity;
//
//	public Order(String product, int quantity) {
//		this.product = product;
//		this.quantity = quantity;
//	}
//
//	public String getProduct() {
//		return product;
//	}
//
//	public int getQuantity() {
//		return quantity;
//	}
//}
//
//public class StreamExample {
//	public static void main(String[] args) {
//		List<User> users = Arrays.asList(
//				new User("John", "Company A", "Department A"),
//				new User("Tom", "Company B", "Department B"),
//				new User("Alice", "Company A", "Department B")
//		);
//
//		List<Order> orders = Arrays.asList(
//				new Order("Product A", 5),
//				new Order("Product B", 3),
//				new Order("Product A", 2)
//		);
//
//		// 1. 根据公司分组用户
//		Map<String, List<User>> usersByCompany = users.stream()
//				.collect(Collectors.groupingBy(User::getCompany));
//		System.out.println("Users grouped by company: " + usersByCompany);
//
//		// 2. 过滤用户，只保留来自公司 A 的用户
//		List<User> usersFromCompanyA = users.stream()
//				.filter(u -> u.getCompany().equals("Company A"))
//				.collect(Collectors.toList());
//		System.out.println("Users from Company A: " + usersFromCompanyA);
//
//		// 3. 获取部门为 Department B 的用户数量
//		long departmentBUserCount = users.stream()
//				.filter(u -> u.getDepartment().equals("Department B"))
//				.count();
//		System.out.println("Department B user count: " + departmentBUserCount);
//
//		// 4. 获取每个用户的订单总数
//		Map<User, Integer> orderCountByUser = users.stream()
//				.collect(Collectors.toMap(
//						u -> u,
//						u -> orders.stream()
//								.filter(o -> o.getProduct().equals("Product A"))
//								.mapToInt(Order::getQuantity)
//								.sum()
//				));
//		System.out.println("Order count by user: " + orderCountByUser);
//
//		// 5. 获取所有订单的总数量
//		int totalOrderCount = orders.stream()
//				.mapToInt(Order::getQuantity)
//				.sum();
//		System.out.println("Total order count: " + totalOrderCount);
//	}
//}
//
//1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		10
//		11
//		12
//		13
//		14
//		15
//		16
//		17
//		18
//		19
//		20
//		21
//		22
//		23
//		24
//		25
//		26
//		27
//		28
//		29
//		30
//		31
//		32
//		33
//		34
//		35
//		36
//		37
//		38
//		39
//		40
//		41
//		42
//		43
//		44
//		45
//		46
//		47
//		48
//		49
//		50
//		51
//		52
//		53
//		54
//		55
//		56
//		57
//		58
//		59
//		60
//		61
//		62
//		63
//		64
//		65
//		66
//		67
//		68
//		69
//		70
//		71
//		72
//		73
//		74
//		75
//		76
//		77
//		78
//		79
//		80
//		81
//		82
//		83
//		84
//		85
//		86
//		87
//		88
//		89
//		90
//		91
//		92
//		93
//		94
//		95
//		96
//		97
//		这个示例代码展示了使用 JDK 8 Stream API 进行一些常见的数据处理操作，包括分组、过滤、计数和求和等操作。你可以根据实际的复杂数据结构和需求来进行相应的修改和扩展。
//
//		4、公司部门用户和商品订单，n个示例
//		过滤过程：
//		假设有一个用户列表List users，过滤出年龄大于18岁的用户：
//
//		List<User> filteredUsers = users.stream()
//		.filter(user -> user.getAge() > 18)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		映射过程：
//		假设有一个用户列表List users，提取所有用户的用户名：
//
//		List<String> usernames = users.stream()
//		.map(user -> user.getUsername())
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		排序过程：
//		假设有一个用户列表List users，按照年龄从小到大排序：
//
//		List<User> sortedUsers = users.stream()
//		.sorted(Comparator.comparingInt(User::getAge))
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		分组过程：
//		假设有一个订单列表List orders，根据公司进行分组：
//
//		Map<Company, List<Order>> ordersByCompany = orders.stream()
//		.collect(Collectors.groupingBy(Order::getCompany));
//		1
//		2
//		聚合操作：
//		假设有一个订单列表List orders，计算所有订单的总金额：
//
//		double totalAmount = orders.stream()
//		.mapToDouble(Order::getAmount)
//		.sum();
//		1
//		2
//		3
//		扁平化处理：
//		假设有一个公司列表List companies，获取所有公司的部门列表：
//
//		List<Department> departments = companies.stream()
//		.flatMap(company -> company.getDepartments().stream())
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		匹配元素：
//		假设有一个用户列表List users，检查是否存在年龄大于等于30岁的用户：
//
//		boolean anyMatch = users.stream()
//		.anyMatch(user -> user.getAge() >= 30);
//		1
//		2
//		查找元素：
//		假设有一个用户列表List users，查找年龄最大的用户：
//
//		Optional<User> maxAgeUser = users.stream()
//		.max(Comparator.comparingInt(User::getAge));
//		1
//		2
//		限制结果集：
//		假设有一个订单列表List orders，获取前5个订单：
//
//		List<Order> limitedOrders = orders.stream()
//		.limit(5)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		跳过元素：
//		假设有一个商品列表List products，跳过前3个商品，获取剩下的商品：
//
//		List<Product> skippedProducts = products.stream()
//		.skip(3)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		去重处理：
//		假设有一个整数列表List numbers，去除重复的数字：
//
//		List<Integer> distinctNumbers = numbers.stream()
//		.distinct()
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		并行处理：
//		假设有一个订单列表List orders，使用并行流计算订单的总金额：
//
//		double totalAmount = orders.parallelStream()
//		.mapToDouble(Order::getAmount)
//		.sum();
//		1
//		2
//		3
//		使用reduce聚合操作：
//		假设有一个订单列表List orders，计算所有订单的总金额，使用reduce操作：
//
//		double totalAmount = orders.stream()
//		.map(Order::getAmount)
//		.reduce(0.0, Double::sum);
//		1
//		2
//		3
//		使用findFirst查找第一个元素：
//		假设有一个订单列表List orders，查找第一个购买商品为“手机”的订单：
//
//		Optional<Order> firstMobileOrder = orders.stream()
//		.filter(order -> order.getProduct().equals("手机"))
//		.findFirst();
//		1
//		2
//		3
//		对集合元素进行批量操作：
//		假设有一个用户列表List users，将所有用户的年龄加5并更新：
//
//		List<User> updatedUsers = users.stream()
//		.peek(user -> user.setAge(user.getAge() + 5))
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		多级分组：
//		假设有一个商品列表List products，按照公司和部门进行多级分组：
//
//		Map<Company, Map<Department, List<Product>>> productsByCompanyAndDepartment = products.stream()
//		.collect(Collectors.groupingBy(Product::getCompany,
//		Collectors.groupingBy(Product::getDepartment)));
//		1
//		2
//		3
//		使用flatMap进行嵌套处理：
//		假设有一个公司列表List companies，获取所有公司的所有部门的所有员工姓名：
//
//		List<String> employeeNames = companies.stream()
//		.flatMap(company -> company.getDepartments().stream())
//		.flatMap(department -> department.getEmployees().stream())
//		.map(Employee::getName)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		4
//		5
//		查找满足条件的任意元素：
//		假设有一个商品列表List products，查找任意一件库存大于0的商品：
//
//		Optional<Product> anyProduct = products.stream()
//		.filter(product -> product.getStock() > 0)
//		.findAny();
//		1
//		2
//		3
//		统计元素个数：
//		假设有一个用户列表List users，统计用户的数量：
//
//		long userCount = users.stream()
//		.count();
//		1
//		2
//		使用forEach进行迭代操作：
//		假设有一个订单列表List orders，打印所有订单的信息：
//
//		orders.stream()
//		.forEach(System.out::println);
//		1
//		2
//		并行处理处理大数据量：
//		假设有一个非常大的用户列表List users，使用并行流进行处理：
//
//		users.parallelStream()
//		.filter(user -> user.getAge() > 18)
//		.forEach(System.out::println);
//		1
//		2
//		3
//		使用collect进行自定义的聚合操作：
//		假设有一个商品列表List products，将所有商品的名称用逗号连接起来：
//
//		String names = products.stream()
//		.map(Product::getName)
//		.collect(Collectors.joining(", "));
//		1
//		2
//		3
//		使用Optional处理可能为空的值：
//		假设有一个List<Optional> userList，筛选出非空的用户列表：
//
//		List<User> nonEmptyUserList = userList.stream()
//		.filter(Optional::isPresent)
//		.map(Optional::get)
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		4
//		连接字符串：
//		假设有一个商品列表List products，将所有商品的名称以逗号分隔连接成一个字符串：
//
//		String joinedNames = products.stream()
//		.map(Product::getName)
//		.collect(Collectors.joining(", "));
//		1
//		2
//		3
//		对元素进行分页处理：
//		假设有一个订单列表List orders，将订单按照每页10个进行分页：
//
//		int pageSize = 10;
//		int totalPages = (int) Math.ceil((double) orders.size() / pageSize);
//
//		List<List<Order>> paginatedOrders = IntStream.range(0, totalPages)
//		.mapToObj(page -> orders.stream()
//		.skip(page * pageSize)
//		.limit(pageSize)
//		.collect(Collectors.toList()))
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		8
//		9
//		使用IntStream进行数值操作：
//		假设有一个整数列表List numbers，计算列表中所有偶数的平方和：
//
//		int sumOfEvenSquares = numbers.stream()
//		.filter(number -> number % 2 == 0)
//		.mapToInt(number -> number * number)
//		.sum();
//		1
//		2
//		3
//		4
//		使用max和min获取最大值和最小值：
//		假设有一个整数列表List numbers，找到列表中的最大值和最小值：
//
//		Optional<Integer> maxNumber = numbers.stream()
//		.max(Integer::compareTo);
//
//		Optional<Integer> minNumber = numbers.stream()
//		.min(Integer::compareTo);
//		1
//		2
//		3
//		4
//		5
//		使用toMap将集合转换为Map：
//		假设有一个用户列表List users，将用户按照ID作为键转换为Map：
//
//		Map<Integer, User> userMap = users.stream()
//		.collect(Collectors.toMap(User::getId, Function.identity()));
//		1
//		2
//		使用anyMatch和allMatch进行条件判断：
//		假设有一个用户列表List users，检查是否所有用户的年龄都大于18岁：
//
//		boolean allAdults = users.stream()
//		.allMatch(user -> user.getAge() > 18);
//
//		boolean anyAdult = users.stream()
//		.anyMatch(user -> user.getAge() > 18);
//		1
//		2
//		3
//		4
//		5
//		使用distinct和sorted进行去重和排序：
//		假设有一个整数列表List numbers，去除重复值并对值进行排序：
//
//		List<Integer> distinctSortedNumbers = numbers.stream()
//		.distinct()
//		.sorted()
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		4
//		使用flatMap将多个列表合并为一个列表：
//		假设有一个公司列表List companies，将每个公司的部门列表合并成一个部门列表：
//
//		List<Department> allDepartments = companies.stream()
//		.flatMap(company -> company.getDepartments().stream())
//		.collect(Collectors.toList());
//		1
//		2
//		3
//		使用reduce进行自定义聚合操作：
//		假设有一个订单列表List orders，计算所有订单的总金额：
//
//		double totalAmount = orders.stream()
//		.map(Order::getAmount)
//		.reduce(0.0, Double::sum);
//		1
//		2
//		3
//		使用partitioningBy进行分区操作：
//		假设有一个商品列表List products，按照库存是否大于0进行分区：
//
//		Map<Boolean, List<Product>> partitionedProducts = products.stream()
//		.collect(Collectors.partitioningBy(product -> product.getStock() > 0));
//		1
//		2
//		使用zip操作将两个流合并为一个流：
//		假设有一个用户列表List users和一个商品列表List products，将两个列表合并为一个交替的流：
//
//		Stream interleavedStream = StreamUtils.zip(users.stream(), products.stream());
//
//		使用iterate生成一个无限流：
//		假设需要生成一个自增的整数序列：
//
//		Stream sequentialNumberStream = Stream.iterate(0, i -> i + 1);
//
//		使用toList将流转换为列表：
//		假设有一个用户流Stream userStream，将其转换为用户列表：
//
//		List userList = userStream.collect(Collectors.toList());
//
//		使用toSet将流转换为集合：
//		假设有一个商品流Stream productStream，将其转换为商品集合：
//
//		Set productSet = productStream.collect(Collectors.toSet());
//
//		使用joining将流转换为字符串：
//		假设有一个部门流Stream departmentStream，将其转换为部门名称的逗号分隔字符串：
//
//		String departmentNames = departmentStream.map(Department::getName)
//		.collect(Collectors.joining(", "));
//		1
//		2
//		使用summarizingInt计算流中的统计数据：
//		假设有一个订单流Stream orderStream，计算订单金额的统计数据：
//
//		DoubleSummaryStatistics orderStatistics = orderStream
//		.collect(Collectors.summarizingDouble(Order::getAmount));
//		System.out.println("Total Orders: " + orderStatistics.getCount());
//		System.out.println("Total Amount: " + orderStatistics.getSum());
//		System.out.println("Average Amount: " + orderStatistics.getAverage());
//		System.out.println("Max Amount: " + orderStatistics.getMax());
//		System.out.println("Min Amount: " + orderStatistics.getMin());
//		1
//		2
//		3
//		4
//		5
//		6
//		7
//		使用toMap将流转换为自定义的Map：
//		假设有一个用户流Stream userStream，将其转换为以ID为键、用户对象为值的Map：
//
//		Map<Integer, User> userMap = userStream.collect(Collectors.toMap(User::getId, Function.identity()));
//
//		使用groupingByConcurrent进行并发分组操作：
//		假设有一个订单列表List orders，按照公司进行并发分组：
//
//		ConcurrentMap<Company, List<Order>> ordersByCompany = orders.stream()
//		.collect(Collectors.groupingByConcurrent(Order::getCompany));
//		1
//		2
//		使用flatMapToInt计算流中元素的数值总和：
//		假设有一个整数列表List numbers，计算列表中所有元素的数值总和：
//
//		int sum = numbers.stream()
//		.flatMapToInt(IntStream::of)
//		.sum();
//		1
//		2
//		3
//		使用peek进行调试操作：
//		假设有一个商品列表List products，在对每个商品进行其他操作之前，在控制台打印商品信息：
//
//		List<Product> modifiedProducts = products.stream()
//		.peek(product -> System.out.println("Processing product: " + product.getName()))
//		.map(product -> /* 进行其他操作 */)
//		.collect(Collectors.toList());
