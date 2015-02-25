package com.bluemobi.imspeak.net.extend;


import com.bluemobi.imspeak.net.extend.filter.IFilter;
import com.bluemobi.imspeak.net.lib.net.DataBuffer;
import com.bluemobi.imspeak.net.lib.net.Packet;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;


/**
 * 
 * @Description: Action的描述类
 * @author ziye - ziye[at]mogujie.com
 * @date 2013-7-22 下午4:45:28
 *
 */
public class ActionContext {
	
	private BaseAction action; // Action对象Object
	
	private Method doMethod; // 处理业务的方法
	
	private RequestParams requestParams; // 请求参数控制器
	
	private int requestType; // 请求头的type
	
	private int defaultResponseType; // 默认响应的type
	
	private IFilter[] filters; // 过滤器们

	public BaseAction getAction() {
		return action;
	}

	public void setAction(BaseAction action) {
		this.action = action;
	}

	public Method getDoMethod() {
		return doMethod;
	}

	public void setDoMethod(Method doMethod) {
		this.doMethod = doMethod;
	}

	public RequestParams getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(RequestParams requestParams) {
		this.requestParams = requestParams;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public int getDefaultResponseType() {
		return defaultResponseType;
	}

	public void setDefaultResponseType(int defaultResponseType) {
		this.defaultResponseType = defaultResponseType;
	}

	public IFilter[] getFilters() {
		return filters;
	}

	public void setFilters(IFilter[] filters) {
		this.filters = filters;
	}
	
	public Object invoke(ChannelHandlerContext context, Packet packet) throws Exception {
		// 解析请求的参数
		Object[] params = decode(packet.getContentBuffer(), context, this,packet.getVersion());	
		
	

		return doMethod.invoke(action, params); // 调用
	}
	
	/**
	 *
	 * @Description: 数据包的解析,一个单纯的代理方法
	 * @param dataBuffer
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Object[] decode(DataBuffer dataBuffer, ChannelHandlerContext context, ActionContext actionContext,int version) throws Exception {
		return requestParams.decode(dataBuffer, context, actionContext,version);
	}

	// 发送消息
	public void sendResponse(ChannelHandlerContext context, Packet packet, DataBuffer responseBuffer) {
		Packet message = new Packet();
		message.setVersion(packet.getVersion());
		message.setServiceId(packet.getServiceId());
		message.setCommandId(defaultResponseType);
		message.setReserved(packet.getReserved());
		if(responseBuffer == null) {
			responseBuffer = new DataBuffer(0);
		}
		message.setContentBuffer(responseBuffer);

		context.getChannel().write(message);
	}
}