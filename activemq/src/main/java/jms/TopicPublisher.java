package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicPublisher {

    private ConnectionFactory connectionFactory = null;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public TopicPublisher() throws JMSException {
        this.connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        this.connection = connectionFactory.createConnection();
        connection.start();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("testTopic");
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    }

    public void sendMsg() throws JMSException {
        Message msg;
        msg = session.createTextMessage("topic msg");
        producer.send(msg);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TopicPublisher topicPublisher = null;
        try {
            topicPublisher = new TopicPublisher();
            topicPublisher.sendMsg();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            topicPublisher.close();
        }

    }

    public void close() {
        try {
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            session.close();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
