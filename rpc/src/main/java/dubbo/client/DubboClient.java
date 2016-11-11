package dubbo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dubbo.MyService;

public class DubboClient {

    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext(new String[] { "applicationConsumer.xml" });
        context.start();
        MyService myService = context.getBean(MyService.class);
        
        System.out.println(myService.say("Jay"));
    }

}
