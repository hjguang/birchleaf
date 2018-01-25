package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

public class RetriveTime {
    public static final String HOSTNAME = "time.nist.gov";

    public static void main(String[] args) throws IOException {

      /*  try (Socket socket = new Socket("time.nist.gov",13)) {

            socket.setSoTimeout(150000);

            InputStream in = socket.getInputStream();
            StringBuffer time = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            for (int c = reader.read(); c!= -1; c = reader.read()) {
                time.append((char) c);
            }
            System.out.println(time);
        } catch (IOException e) {
            System.err.println(e);
        }*/

        Date d = RetriveTime.getDateFromNetwork();
        System.out.println("It is---" + d);
    }

    public static Date getDateFromNetwork() throws IOException {
        long differenceBetweenEpochs = 2208988800L;
        Socket socket = null;
        try {
            socket = new Socket(HOSTNAME, 37);
//            socket.setSoTimeout(150000);
            InputStream raw = socket.getInputStream();
            long secondSince1900 = 0;
            for (int i = 0; i < 4; i++) {
                long l = secondSince1900;
                int r = raw.read();
                System.out.println("secondSince1900 = " + secondSince1900);
                System.out.println("r================" + r);
                long s = secondSince1900 << 8;
                System.out.println("s================" + s);
                secondSince1900 = s | r;
                System.out.println("secondSince1900== " + secondSince1900);
                System.out.println("************************************");
            }


            long secondSinze1970 = secondSince1900 - differenceBetweenEpochs;
            long msSecond = secondSinze1970 * 1000;
            Date time = new Date(msSecond);
            return time;
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
            }
        }
    }
}
