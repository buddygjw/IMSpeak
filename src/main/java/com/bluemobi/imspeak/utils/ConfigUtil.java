package com.bluemobi.imspeak.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 获取apiconfig.properties 文件的信息
 * @author Administrator
 *
 */
public class ConfigUtil
{
	private static final String CONFIG_FILE = "conf/system.properties";
	
	public static PropertiesConfiguration getConfig() {
		PropertiesConfiguration config = null;
		try {
			config = new PropertiesConfiguration(CONFIG_FILE);
			config.setAutoSave(true);
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e) {
			throw new RuntimeException( e.getLocalizedMessage());
		}
		return config;
	}

    public static PropertiesConfiguration getConfig(String filePath) {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(filePath);
            config.setAutoSave(true);
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            throw new RuntimeException( e.getLocalizedMessage());
        }
        return config;
    }
	
	/**
	 * 获取key对应的字符串
	 * @param key 对应的key
	 * @return
	 */
	public static String getString(String key) {
		
		return getConfig().getString(key);
	}
	
	public static void updateProp(String key, String value) {
		getConfig().setProperty(key, value);
	}
	
}
