/**
 * 
 */
package thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ��������
 * 
 * @author houjianguang
 *
 */
public class PeriodicalTask implements Runnable {

	private String name;

	public PeriodicalTask(String name) {
		this.name = name;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		System.out.printf("Main: Starting at: %s\n", new Date());
		PeriodicalTask task = new PeriodicalTask("Task");
		//��һ��1s��ִ�У�����ÿ2sִ��
		ScheduledFuture<?> result = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
		for (int i = 0; i < 10; i++) {
			//��ȡ������һ��ִ�е�ʱ��
			System.out.printf("Main: Delay: %d\n", result.getDelay(TimeUnit.MILLISECONDS));
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: Finished at: %s\n", new Date());
	}

	@Override
	public void run() {
		System.out.printf("%s: Starting at : %s\n", name, new Date());
	}

}
