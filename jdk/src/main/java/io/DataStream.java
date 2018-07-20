package io;

import java.io.*;

public class DataStream {

    final static String FILENAME = "values.dat";

    public static void main(String[] args) {
        try(FileOutputStream fos = new FileOutputStream(FILENAME);
            DataOutputStream dos = new DataOutputStream(fos)) {

            dos.writeInt(2);
        } catch (IOException e) {

        }

        try (FileInputStream fis = new FileInputStream(FILENAME);
             DataInputStream dis = new DataInputStream(fis)) {

            System.out.println(dis.readInt());
        } catch (IOException e) {

        }
    }
}
