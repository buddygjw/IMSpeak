/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.bluemobi.imspeak.net.handler;


import com.bluemobi.imspeak.core.Constant;
import com.bluemobi.imspeak.net.MainServer;
import com.bluemobi.imspeak.net.data.Message;
import com.bluemobi.imspeak.net.data.Packet;
import com.bluemobi.imspeak.net.data.User;
import com.bluemobi.imspeak.utils.JsonUtil;
import net.sf.json.JSON;
import net.sf.json.util.JSONUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;

/** 
 * This class is to create new sessions, destroy sessions and deliver
 * received XML stanzas to the StanzaHandler.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class CoreIoHandler implements IoHandler {

    private static final Log log = LogFactory.getLog(CoreIoHandler.class);

    public static final String XML_PARSER = "XML_PARSER";

    private static final String CONNECTION = "CONNECTION";

    private static final String STANZA_HANDLER = "STANZA_HANDLER";

    private String serverName;

    private static Map<String,String> sessionIdMap = new HashMap<String,String>();

    private static Map<String,IoSession>  sessionMap = new HashMap<String, IoSession>();


    /**
     * Constructor. Set the server name from server instance. 
     */
    protected CoreIoHandler() {
        serverName = MainServer.getInstance().getServerName();
    }

    /**
     * Invoked from an I/O processor thread when a new connection has been created.
     */
    public void sessionCreated(IoSession session) throws Exception {
        log.info("sessionCreated()...");
    }

    /**
     * Invoked when a connection has been opened.
     */
    public void sessionOpened(IoSession session) throws Exception {
        log.info("sessionOpened()...");
        log.info("remoteAddress=" + session.getRemoteAddress());
        // Create a new XML parser

    }

    /**
     * Invoked when a connection is closed.
     */
    public void sessionClosed(IoSession session) throws Exception {
        log.info("sessionClosed()...");

    }

    /**
     * Invoked with the related IdleStatus when a connection becomes idle.
     */
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        log.info("sessionIdle()...");

    }

    /**
     * Invoked when any exception is thrown.
     */
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        log.info("exceptionCaught()...");
        log.error(cause);
    }

    /**
     * Invoked when a message is received.
     */
    public void messageReceived(IoSession session, Object message)
            throws Exception {

        log.info("messageReceived()...");
        Packet packet = (Packet)message;
        String content =  packet.getTextContent();
        if(packet.getCommandId()== Constant.COMMAND_LOGIN){
            User user = JsonUtil.json2Obj(content,User.class);
            sessionIdMap.put(user.getUsername(),session.getId()+"");
        }
        if(packet.getCommandId() == Constant.COMMAND_MESSAGE){
            Message message1 = JsonUtil.json2Obj(content,Message.class);
            String to = message1.getTo();
            String sessionId = sessionIdMap.get(to);
            IoSession toSession = sessionMap.get(sessionId);
            toSession.write(message1.getContent());
        }

    }

    /**
     * Invoked when a message written by IoSession.write(Object) is sent out.
     */
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("messageSent()...");



    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }

}