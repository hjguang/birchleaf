package thread.base;

import java.util.concurrent.CountDownLatch;

public class LatchTest {

	public long process(int num, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(num);

		for (int i = 0; i < num; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {

					}
				}
			};
			t.start();
			System.out.println("Thread is running:"  + t.getName() + i);
		}
		long start = System.currentTimeMillis();
		startGate.countDown();
		endGate.await();
		long end = System.currentTimeMillis();
		return end - start;
	}

	public static void main(String[] args) throws InterruptedException {
		LatchTest test = new LatchTest();
		long time = test.process(5, new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		});
		System.out.println(time);
	}

}
