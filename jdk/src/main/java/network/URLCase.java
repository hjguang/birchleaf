package network;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

    public class URLCase {

        public static void main(String[] args) {
            try {
                URL url = new URL("http://www.baidu.com");
                URLConnection uc = url.openConnection();

                try (InputStream is = uc.getInputStream()) {
                    InputStream buffer = new BufferedInputStream(is);
                    Reader reader = new InputStreamReader(buffer);
                    int c;
                    while ((c = reader.read()) != -1) {
                        System.out.print((char)c);
                    }
                }
            } catch (MalformedURLException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }


}
