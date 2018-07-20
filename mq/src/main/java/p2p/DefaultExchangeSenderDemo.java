package p2p;

import rbt.Sender;

public class DefaultExchangeSenderDemo {

    public static void sendToDefaultExchange() {
        Sender sender = new Sender();
        sender.initialize();
        sender.send("Test message.");
        sender.destroy();
    }
    public static void main(String[] args) {
        sendToDefaultExchange();
    }
}
