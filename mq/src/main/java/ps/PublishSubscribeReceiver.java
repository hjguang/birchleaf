package ps;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rbt.Sender;

import java.io.IOException;

public class PublishSubscribeReceiver {

    private static final String EXCHANGE_NAME = "pubsub_exchange";
    private final static Logger LOGGER = LoggerFactory.getLogger(Sender.class);
    private Channel channel = null;
    private Connection connection = null;

    public void initialize() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public String receive(String queue) {
        if (channel == null) {
            initialize();
        }
        String message = null;
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(queue, false, false, false, null);
            channel.queueBind(queue, EXCHANGE_NAME, "");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queue,true,consumer);
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            message = new String(delivery.getBody());
            LOGGER.info("pub_sub:Message received: " + message);
            return message;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ShutdownSignalException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ConsumerCancelledException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return message;
    }
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }
}
