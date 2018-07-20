package io;

import java.io.*;

public class SerDeSer {

    public static void main(String[] args) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("customer.dat"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("customer.dat"))
        ) {
            Customer c = new Customer(1,"aa");
            oos.writeObject(c);

            c = (Customer)ois.readObject();
            System.out.println(c.getId());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
