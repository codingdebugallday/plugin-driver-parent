package com.github.codingdebugallday.driver.core.app.service.session;

import java.util.Map;

import com.github.codingdebugallday.driver.core.domain.entity.MetaDataInfo;

/**
 * <p>
 * 元数据session
 * </p>
 *
 * @author JupiterMouse 2020/07/08
 * @since 1.0.0
 */
public interface MetaDataSession {

    /**
     * hive解析表的元数据
     *
     * @param schema    hive库
     * @param tableName hive表名
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> parseMetaStore(String schema, String tableName);

    /**
     * 查询元数据
     *
     * @param schema    schema
     * @param tableName 表名
     * @return MetaDataInfo
     */
    MetaDataInfo queryMetaData(String schema, String tableName);

}