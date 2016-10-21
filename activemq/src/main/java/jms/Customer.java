package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Customer implements MessageListener {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;
        Destination destination;
        MessageConsumer consumer = null;
        Message message;

        try {
            Context ctx = new InitialContext();
            connectionFactory = (ConnectionFactory) ctx.lookup("connectionFactory");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("TEST.QUEUE");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new Customer());
            // message = (TextMessage) consumer.receive();
        } catch (JMSException | NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message msg) {
        // TODO Auto-generated method stub
        if (msg instanceof TextMessage) {
            System.out.println("Received message is:---------------->" + msg);
        }
    }

}
