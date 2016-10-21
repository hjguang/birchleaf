package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PersisPruduce {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Context ctx = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory)ctx.lookup("connectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("persistenceQueue");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        Message msg = session.createTextMessage("test messages");
        producer.send(msg);
    
        producer.close();
        session.close();
        connection.close();
    }

}
