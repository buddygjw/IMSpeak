package com.bluemobi.imspeak.net.mamager;


import com.bluemobi.imspeak.net.configure.Configure;
import com.bluemobi.imspeak.net.configure.Router;
import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;

/**
 * 
 * @Description: 配置管理类, 单例
 * @author shitou - shitou[at]mogujie.com
 * @date 2013-7-22 上午11:15:23
 *
 */
public class ConfigureManager
{

	public static final Logger logger = LoggerFactory.getLogger(ConfigureManager.class);

	private static ConfigureManager _configureManagerInstance = null;

	public static ConfigureManager getInstance()
	{
		if(_configureManagerInstance == null)
		{
			_configureManagerInstance = new ConfigureManager();
		}
		return _configureManagerInstance;
	}

	private Configure configure;

	private ConfigureManager()
	{
		configure = new Configure();
	}

	public Router getActionRouter() {
		return configure.getActionRouter();
	}

	/**
	 * 装载数据
	 * @throws Exception
	 */
	public void loadAllConfigs() throws Exception
	{
		configure.loadConfigs();
	}

	/**
	 * 重新装载数据
	 * @throws Exception
	 */
	public void reloadAllConfigs() throws Exception {
		loadAllConfigs();
	}

}
