package zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class HelloZooKeeper implements Watcher {

    static CountDownLatch count = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String hostPort = "localhost:2181/zk";
        ZooKeeper zk = new ZooKeeper(hostPort, 20000, new HelloZooKeeper());
        count.await();
        System.out.println(zk.getState());
//        String root = zk.create("/test", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println(root);
//        List<String> list = zk.getChildren("/", new HelloZooKeeper());
//        System.out.println(list);
        zk.setData("/test", "123".getBytes(), -1);
        System.out.println(new String(zk.getData("/test", true, null)));
    }

    public void process(WatchedEvent arg0) {
        System.out.println(arg0);
        if (arg0.getState() == KeeperState.SyncConnected) {
            count.countDown();
        }
    }

}
