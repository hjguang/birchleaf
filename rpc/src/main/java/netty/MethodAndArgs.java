package netty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MethodAndArgs implements Serializable {
    private String methodName;// 璋冪敤鐨勬柟娉曞悕绉�
    private Class<?>[] types;// 鍙傛暟绫诲瀷
    private Object[] objects;// 鍙傛暟鍒楄〃

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public void setTypes(Class<?>[] types) {
        this.types = types;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public MethodAndArgs() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MethodAndArgs(String methodName, Class<?>[] types, Object[] objects) {

        this.methodName = methodName;
        this.types = types;
        this.objects = objects;
    }
}
