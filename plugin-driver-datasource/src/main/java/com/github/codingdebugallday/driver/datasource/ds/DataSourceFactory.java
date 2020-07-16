package com.github.codingdebugallday.driver.datasource.ds;

import com.github.codingdebugallday.driver.common.domain.entity.PluginDatasource;
import com.github.codingdebugallday.driver.common.infra.exceptions.DriverException;
import com.github.codingdebugallday.driver.common.infra.utils.JsonUtil;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>
 * 连接池数据源创建接口
 * </p>
 *
 * @author JupiterMouse 2020/07/14
 * @since 1.0
 */
public interface DataSourceFactory {

    /**
     * 连接URL
     */
    String JDBC_URL = "jdbcUrl";
    /**
     * 用户名
     */
    String USERNAME = "username";
    /**
     * 密码
     */
    String PASSWORD = "password";

    /**
     * 创建数据源
     *
     * @param pluginDatasource 配置信息
     * @return DataSource
     */
    DataSource create(PluginDatasource pluginDatasource);

    /**
     * 从PluginDatasource中解析配置信息
     *
     * @param pluginDatasource PluginDatasource
     * @return Map<String, Object>
     */
    @SuppressWarnings({"all"})
    default Map<String, String> parsingSetting(PluginDatasource pluginDatasource) {
        String settingsInfo = pluginDatasource.getSettingsInfo();
        if (StringUtils.isEmpty(settingsInfo)) {
            throw new DriverException("the datasource settingsInfo cannot be empty");
        }
        Map<String, String> configMap = JsonUtil.toObj(settingsInfo, Map.class);
        this.verifyConfig(configMap);
        return configMap;
    }

    /**
     * 校验参数是否包含必要信息
     *
     * @param prop 配置信息
     */
    default void verifyConfig(Map<String, String> prop) {
        if (StringUtils.isEmpty(prop.get(JDBC_URL))) {
            throw new DriverException("jdbcUrl need not null");
        }
        if (StringUtils.isEmpty(prop.get(PASSWORD))) {
            throw new DriverException("username need not null");
        }
        if (StringUtils.isEmpty(prop.get(PASSWORD))) {
            throw new DriverException("password need not null");
        }
    }
}