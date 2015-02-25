package com.bluemobi.imspeak.net.configure;

import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;
import com.bluemobi.imspeak.utils.ConfigUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置，单利
 * @author stone
 *
 */
public class Configure
{
	public static final Logger logger = LoggerFactory.getLogger(Configure.class);

	public Router actionRouter;

    public PropertiesConfiguration systemConfig;

	public Configure()
	{
		actionRouter = Router.getInstance();
        systemConfig = ConfigUtil.getConfig("conf/system.properties");
	}



	public Router getActionRouter() {
		return actionRouter;
	}


	/**
	 * 载入所有配置
	 * @throws Exception
	 */
	public void loadConfigs() throws Exception {
		loadActionConfig(); // action相关的路由配置
	}

	public void loadActionConfig() throws Exception {

		actionRouter.load(systemConfig.getString("com.mogujie.ares.config.file.route"));
	}


}
