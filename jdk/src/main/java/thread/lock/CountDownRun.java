package thread.lock;

import java.util.concurrent.CountDownLatch;

public class CountDownRun {

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(n);
        for ( int i = 0; i< n; i++) {
            new Thread(new Worker(start, end, i)).start();
        }
        System.out.println("read go!!");
        start.countDown();
        end.await();
        System.out.println("over!!");
    }

}
class Worker implements Runnable {
    private final CountDownLatch start;
    private final CountDownLatch end;
    private int index = 0;
    public Worker(CountDownLatch start,CountDownLatch end, int index) {
        this.start = start;
        this.end = end;
        this.index = index;
    }
    @Override
    public void run() {
        try {
            start.await();
            printNum(index);
            end.countDown();
        } catch (InterruptedException e) {}
    }
    
    void printNum(int index) {
        System.out.println("index is ---->" + index);
    }
    
}
