package com.bluemobi.imspeak.net;

import com.bluemobi.imspeak.net.configure.SysConstants;
import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;
import com.bluemobi.imspeak.net.mamager.ConfigureManager;
import com.bluemobi.imspeak.net.mamager.NetworkManager;


/**
 * Created by wangbin on 2015/1/22.
 */
public class MainServer {

    private static final Logger logger = LoggerFactory.getLogger(MainServer.class);


    public void bootup(int port){
        try {
            initConfigure();
            initNet(port);

        }catch (Exception e){
            logger.error("",e);
        }
    }


    public void initConfigure() throws Exception {
        ConfigureManager.getInstance().loadAllConfigs();
    }

    /**
     * 网络初始化
     * @throws Exception
     */
    public void initNet(int port) throws Exception {
        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.init(port);
    }


    public static void imServerInit(){
        int port = SysConstants.SERVER_DEFAULT_PORT;
        MainServer server = new MainServer();
        server.bootup(port);
    }


}
