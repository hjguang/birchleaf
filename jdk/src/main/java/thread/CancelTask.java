/**
 * 
 */
package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 取消任务
 * 
 * @author houjianguang
 *
 */
public class CancelTask implements Callable<String> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		CancelTask task = new CancelTask();
		System.out.printf("Main: Executing the Task\n");
		Future<String> result = executor.submit(task);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: Canceling the Task\n");
		result.cancel(true); //取消任务
		
		System.out.printf("Main: Canceled: %s\n",result.isCancelled());
		System.out.printf("Main: Done: %s\n",result.isDone());
		
		executor.shutdown();
		System.out.printf("Main: The executor has finished\n");
	}

	@Override
	public String call() throws Exception {
		while (true) {
			System.out.printf("Task: Test\n");
			Thread.sleep(100);
		}
	}

}
