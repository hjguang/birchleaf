package dubbo.server;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServiceMain2 {

    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) throws IOException {
        context = new ClassPathXmlApplicationContext(
                new String[] { "applicationProvider2.xml" });
        context.start();

        System.out.println("Press any key to exit.");
        System.in.read();
    }

}
