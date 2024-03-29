package rose.mary.trace.core.esnecil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lemon.balm.core.cache.InfinispanCacheManager;

import rose.mary.trace.core.cache.CacheProxy;

public class S9LManager implements Runnable {

    Logger logger = LoggerFactory.getLogger(getClass());

    Thread thread;

    boolean isShutdown = true;

    String name = "S9LManager";

    long delay = 24 * 60 * 60 * 1000;

    int d = 0;
    byte[] h;
    int l = 0;
    long s = 0L;
    byte[] u;

    public S9LManager() throws Exception {

        InfinispanCacheManager cm = null;
        try {
            cm = new InfinispanCacheManager();
            CacheProxy<String, Object> oCache = cm.initializeCache(
                    System.getProperty("rose.mary.home") + File.separator + "lib" + File.separator + "ext", "o");
            if (oCache == null)
                System.out.println("oCache is null");

            d = (int) oCache.get("s9l.d");
            h = (byte[]) oCache.get("s9l.h");
            l = (int) oCache.get("s9l.l");
            s = (long) oCache.get("s9l.s");
            u = (byte[]) oCache.get("s9l.u");

            System.out.println("license day:" + d);

        } finally {
            if (cm != null)
                cm.close();
        }
    }

    public void start() {

        if (thread != null)
            stop();
        thread = new Thread(this, name);
        isShutdown = false;
        thread.start();

    }

    public void stop() {
        isShutdown = true;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        boolean ok = false;
        try {
            ok = validateDate() && validateHost();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ok;
    }

    private boolean validateDate() {
        if (l == -1)
            return true;
        if ((System.currentTimeMillis() - s) / 1000 / 24 / 60 / 60 <= d) {
            return true;
        } else {
            return false;
        }
    }

    private String getHostName() throws Exception {

        String cmd = System.getProperty("t9.hostname.cmd");
        if (cmd == null || cmd.length() == 0) {
            return InetAddress.getLocalHost().getHostName();
        } else {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream is = p.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (true) {
                int d = is.read();
                if (d == -1)
                    break;
                baos.write(d);
            }
            return new String(baos.toByteArray());
        }
    }

    private final boolean validateHost() {
        return true;
        /* 2022.09.16 do not check host */
        /*
         * String name = "";
         * try {
         * name = getHostName();
         * 
         * for (int i = 0; i < h.length; i++) {
         * if ((char) h[i] != name.charAt(i))
         * return false;
         * }
         * return true;
         * // return name.equals(new String(h));
         * } catch (Exception e) {
         * logger.error("can't hostname", e);
         * return false;
         * }
         */
    }

    @Override
    public void run() {

        while (Thread.currentThread() == thread && !isShutdown) {

            check();

            try {
                Thread.sleep(delay);
                continue;
            } catch (java.lang.InterruptedException ie) {
                isShutdown = true;
                break;
            }

        }
    }

    int alertDurationDay = 30;
    String contactMail = "mocomsysall@gmail.com, awe654452@gmail.com, whoana@gmail.com, 010-9113-4270";

    public void check() {

        if (!validateDate()) {
            logger.error("Your license was expired!" + System.lineSeparator() + showLicense());
            //logger.error("S9L:A9 has something problem(esnecil)!");
            if (!isInDurationDay()) {
                logger.error("T9 system was shut down. Contact to email \"" + contactMail + "\" for the more information.");
                System.exit(-1);
            } else {
                logger.error("T9 system will be shutdown in " + alertDurationDay + " days. Contact to email \"" + contactMail + "\" for the more information.");
            }
        } else if (!validateHost()) {

            // try{
            // String userHost = getHostName();
            // String asignHost = new String(h);
            // logger.error("asign host:" + asignHost + ", length:" + asignHost.length());
            // logger.error("[");
            // for(int i = 0 ; i < asignHost.length(); i ++){
            // logger.error(""+asignHost.charAt(i));
            // }
            // logger.error("]");
            // logger.error("userHost host:" + userHost + ", length:" + userHost.length());
            // logger.error("[");
            // for(int i = 0 ; i < userHost.length(); i ++){
            // logger.error(""+userHost.charAt(i));
            // }
            // logger.error("]");

            // }catch(Exception e){
            // e.printStackTrace();
            // }

            logger.error("This license' host is not right!" + System.lineSeparator() + showLicense());
            //logger.error("S9L:A9 has something problem(esnecil)!");
            System.exit(-1);
        }

        // logger.info("This license is right!" + System.lineSeparator() +
        // showLicense());
    }

    private boolean isInDurationDay() {
        return (System.currentTimeMillis() - s) / 1000 / 24 / 60 / 60 <= (d + alertDurationDay);
    }

    int theRestDay() {
        return (int) (d - (System.currentTimeMillis() - s) / (24 * 60 * 60 * 1000));
    }

    String showLicense() {
        StringBuffer sb = new StringBuffer();

        sb.append(System.lineSeparator());
        sb.append("--------------------------------------------------------------------")
                .append(System.lineSeparator());
        sb.append("ⓒ 2022. (" + contactMail + ") all rights reserved.").append(System.lineSeparator());
        sb.append("--------------------------------------------------------------------")
                .append(System.lineSeparator());
        if (l != -1) {
            Calendar to = Calendar.getInstance();
            to.setTimeInMillis(s);
            to.set(Calendar.DAY_OF_YEAR, to.get(Calendar.DAY_OF_YEAR) + d);
            sb.append("This software is licensed through ").append(to.getTime().toString())
                    .append(System.lineSeparator());
            if (theRestDay() >= 0) {
                sb.append("The rest of day: ").append(theRestDay()).append(System.lineSeparator());
            } else {
                sb.append("The license is expired!").append(System.lineSeparator());
            }
        } else {
            sb.append("This software is licensed with unlimited rights.").append(System.lineSeparator());
        }
        sb.append("Authorized Host:").append(new String(h)).append(System.lineSeparator());
        sb.append("Authorized Users or Sites:").append(new String(u)).append(System.lineSeparator());
        sb.append("--------------------------------------------------------------------")
                .append(System.lineSeparator());

        return sb.toString();
    }

}
