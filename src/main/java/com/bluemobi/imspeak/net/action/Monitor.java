package com.bluemobi.imspeak.net.action;


import com.bluemobi.imspeak.net.extend.BaseAction;
import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;
import com.bluemobi.imspeak.net.lib.net.DataBuffer;

public class Monitor extends BaseAction {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    /**
     * 
     * @Description: heartbeat
     * @param clientAddress
     * @return
     */
    public DataBuffer heartbeat(String clientAddress, int version) {
        DataBuffer dataBuffer = new DataBuffer(0);
        return dataBuffer; // 不用返回
    }

}