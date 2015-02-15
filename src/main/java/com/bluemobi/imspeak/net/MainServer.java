package com.bluemobi.imspeak.net;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wangbin on 2015/2/11.
 */
public class MainServer {
    private static final Log log = LogFactory.getLog(MainServer.class);

    private ApplicationContext context;

    private static MainServer instance;

    private String serverName;

    public static MainServer getInstance() {
        // return instance;
        if (instance == null) {
            synchronized (MainServer.class) {
                instance = new MainServer();
            }
        }
        return instance;
    }

    public MainServer(){
        if (instance != null) {
            throw new IllegalStateException("A server is already running");
        }
        instance = this;
        start();
    }


    /**
     * Starts the server using Spring configuration.
     */
    public void start() {
        try {
            //  locateServer();
            serverName = "127.0.0.1";
          //  context = new ClassPathXmlApplicationContext("applicationContext.xml");
            log.info("Spring Configuration loaded.");

            log.info("XmppServer started: " + serverName);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }




    public String getServerName() {
        return serverName;
    }

}
