package kizwid.web.server;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLDecoder;
import java.util.concurrent.CountDownLatch;

/**
 * User: kizwid
 * Date: 2012-02-22
 */
public class WebServer implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final CountDownLatch latch;
    private final Server server;
    private final int httpPort;
    private final String contextPath;
    final String WEBAPPDIR = "src/main/webapp";

    public static final int DEFAULT_PORT = 8091;
    public static final String DEFAULT_CONTEXTPATH = "/pretzlik-films";

    public WebServer(int httpPort, String contextPath, CountDownLatch latch) {
        this.latch = latch;
        this.httpPort = httpPort;
        this.contextPath = contextPath;
        server = new Server();
    }

    public void run() {

        try {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(httpPort);
            server.addConnector(connector);

            //TODO: use reliable path to webAppPath
            //final URL warUrl = Thread.currentThread().getContextClassLoader().getResource(WEBAPPDIR);
            File testClasses = new File(URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getFile()));
            File base = testClasses.getParentFile().getParentFile();
            File webAppDir = new File(base + File.separator + WEBAPPDIR);
            final String webAppPath = webAppDir.getAbsolutePath();

            WebAppContext webAppContext;
            ResourceHandler resourceHandler;
            if( webAppDir.exists()){
                
                logger.info("using regular webAppDir from file system [{}]",webAppPath);
                webAppContext = new WebAppContext();
                webAppContext.setDescriptor(webAppPath + "/WEB-INF/web.xml");
                webAppContext.setResourceBase(webAppPath);
                webAppContext.setContextPath(contextPath);
                webAppContext.setParentLoaderPriority(true);

                resourceHandler = new ResourceHandler();
                resourceHandler.setWelcomeFiles(new String[]{"index.html"});
                resourceHandler.setResourceBase(webAppPath);
                
            }else{
                throw new IllegalArgumentException("WebAppDir not found " + webAppDir);
            }

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{resourceHandler, webAppContext, new DefaultHandler()});
            server.setHandler(handlers);

            server.start();
            latch.countDown();//notify caller
            server.join();
            server.stop();
            logger.info("server stopped");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Server Interrupted. Stopping", e);
        } catch (Exception e) {
            logger.error("Server Stopping", e);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) throws InterruptedException {

        String env = System.getProperty("pretzlik-films.env", "dev");
        System.setProperty("pretzlik-films.env", env);
        logger.info("starting webServer for pretzlik-films.env [{}]",env);

        CountDownLatch latch = new CountDownLatch(1);
        WebServer webServer = new WebServer(DEFAULT_PORT, DEFAULT_CONTEXTPATH, latch);
        webServer.run();

    }

}
