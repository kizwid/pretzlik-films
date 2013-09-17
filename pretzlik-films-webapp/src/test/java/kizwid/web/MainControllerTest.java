package kizwid.web;

import kizwid.web.server.WebServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User: kizwid
 * Date: 2012-02-06
 */
public class MainControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(MainControllerTest.class);
    public static final int PORT = 8090;
    public static final String HOST = "localhost";
    public static final String CONTEXTPATH = "/pretzlik-films";
    public static final String URL_BASE = "http://" + HOST + ":" + PORT + CONTEXTPATH +"/control?";
    private static WebServer webServer;

    @Before
    public void setUp() throws InterruptedException, IOException {

        //just start for 1st test (or reuse running instance if using jetty:run)
        if(checkPort(PORT)){

            //build the database
            //sqlLoader.load("releases","views");

            Executor executor = Executors.newSingleThreadExecutor();
            CountDownLatch latch = new CountDownLatch(1);
            webServer = new WebServer(PORT,CONTEXTPATH,latch);
            executor.execute(webServer);
            latch.await();
            System.out.println("****** started webserver **********");
        }

    }

    @AfterClass
    public static void tearDown() throws Exception {
        if(webServer != null)webServer.stop();//only stop after all tests have completed
        logger.info("system.property pretzlik-films.env = {}", System.getProperty("pretzlik-films.env"));
    }

    @Test
    public void canLoadDashboard() throws Exception {

        StringWriter sw = new StringWriter();
        get(URL_BASE + "Action=Home&User=unitTest", new PrintWriter(sw));
        String page = sw.toString().trim();
        //System.out.println(page);
        logger.info(page.substring(0, Math.min(500, page.length())));
        Assert.assertTrue(page.contains("<title>Pretzlik Films</title>"));

    }

    @Test
    public void runForever() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }


    public static void get(String sUrl, PrintWriter pw) throws Exception {
        URL url = new URL(sUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        for (; ; ) {
            String sLine = br.readLine();
            if (sLine == null) break;
            pw.println(sLine);
        }
        br.close();
        pw.flush();
    }

    //sometimes we run this using jetty:run
    private boolean checkPort(int port) {
        boolean success;
        try {
            Server server = new Server(port);
            server.start();
            success = true;
            server.stop();
        }   catch (Exception e){
            success=false;
        }
        return success;
    }


}
