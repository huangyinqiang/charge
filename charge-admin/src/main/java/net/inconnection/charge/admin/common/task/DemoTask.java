package net.inconnection.charge.admin.common.task;

import com.jfinal.plugin.cron4j.ITask;

/**
 * 用于配置类型为taskClass的定时任务
 */
public class DemoTask implements ITask {
	
	@Override
	public void run() {
		System.out.println("demo run!");
	}

	@Override
	public void stop() {
		System.out.println("demo stop!");
	}

}
