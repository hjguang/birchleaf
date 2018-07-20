package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStream {

    public static void main(String[] args) {

        try(FileInputStream fis = new FileInputStream("readme.txt");
            FileOutputStream fos = new FileOutputStream("new_values.txt")
        ) {
            int b;
            while ( (b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {

        }
    }
}
