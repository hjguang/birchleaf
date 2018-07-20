package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterDemo {

    public static void main(String[] args) {

        try (FileWriter fw = new FileWriter("d:\\tmp.txt");
             BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.write("a");
            String b = null;
            bw.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter("d:\\tmp1.txt");
             BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.append("a");
            bw.append(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
