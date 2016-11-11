package dubbo.server;

import dubbo.MyService;

public class MyServiceImpl implements MyService {
    @Override
    public String say(String name) {
        return "Hello " + name + "!";
    }

}
