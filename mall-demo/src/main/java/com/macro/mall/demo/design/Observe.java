package com.macro.mall.demo.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 观察者模式
 * @Author: yfc
 * @Date: 2023/10/6 16:28
 */
public class Observe {
}


// 主题接口
interface Subject {
	void registerObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}

// 具体主题类
class ConcreteSubject implements Subject {
	private List<Observer> observers = new ArrayList<>();

	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		notifyObservers();
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(state);
		}
	}
}


// 观察者接口
interface Observer {
	void update(int state);
}

// 具体观察者类
class ConcreteObserver implements Observer {
	private int observerState;

	@Override
	public void update(int state) {
		observerState = state;
		System.out.println("Observer state updated to: " + observerState);
	}

}


class ObserverPatternExample {
	public static void main(String[] args) {
		// 创建主题对象
		ConcreteSubject subject = new ConcreteSubject();

		// 创建观察者对象
		Observer observer1 = new ConcreteObserver();
		Observer observer2 = new ConcreteObserver();

		// 注册观察者
		subject.registerObserver(observer1);
		subject.registerObserver(observer2);

		// 主题状态变化，观察者将会被通知并更新
		subject.setState(10);

		// 移除一个观察者
		subject.removeObserver(observer1);

		// 再次改变主题状态
		subject.setState(20);
	}
}



