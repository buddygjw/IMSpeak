package com.bluemobi.imspeak.net.extend.dispatch;

import com.bluemobi.imspeak.net.extend.ActionContext;
import com.bluemobi.imspeak.net.extend.ActionHolder;
import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;
import com.bluemobi.imspeak.net.lib.net.DataBuffer;
import com.bluemobi.imspeak.net.lib.net.IDispatcher;
import com.bluemobi.imspeak.net.lib.net.Packet;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

/**
 * 
 * @Description: 默认的消息分发器，所有从BinaryMessageHandler传来的消息
 *               由这个类根据消息头的type分发到相应的Action
 * @author ziye - ziye[at]mogujie.com
 * @date 2013-7-21 上午11:21:34
 * 
 */
public class DefaultRequestDispatcher implements IDispatcher {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultRequestDispatcher.class);

    private ActionHolder actionHolder;

    public DefaultRequestDispatcher(ActionHolder actionHolder) {
        this.actionHolder = actionHolder;
    }

    public ActionHolder getActionHolder() {
        return actionHolder;
    }

    public void setActionHolder(ActionHolder actionHolder) {
        this.actionHolder = actionHolder;
    }

    /**
     * 
     * @Description: 消息分发函数，所有请求都由这个函数来分发
     * @param @param requestType
     * @param @return
     * @return ActionContext
     * @throws
     */
    @Override
    public void dispatch(ChannelHandlerContext context, MessageEvent e) {

        Packet packet = (Packet) e.getMessage();
            int type = packet.getCommandId();
            logger.info("请求访问的模块commandID:" + type);
            ActionContext actionContext = actionHolder.get(type); // 取得请求对应的Action
            if (actionContext == null) { // 这里是有可能会返回null的
                logger.error("找不到指定的Action, type: " + type);
                return;
            }

            try {
                Object res = actionContext.invoke(context, packet);
                if (res != null) {
                    actionContext.sendResponse(context, packet, (DataBuffer) res); // 发送结果
                }
            } catch (Exception e1) { // 如果出错了，临死前返回一个数据包给客户端...
            logger.error("commandId: " + packet.getCommandId(), e1);
            // actionContext.sendResponse(context, packet, new DataBuffer(0));
        }
    }
}