package com.github.codingdebugallday.driver.mysql.session;

import com.github.codingdebugallday.driver.core.app.service.session.rdbms.AbstractRdbmsDriverSession;
import com.github.codingdebugallday.driver.core.infra.exceptions.DriverException;
import com.github.codingdebugallday.driver.core.infra.meta.Column;
import com.github.codingdebugallday.driver.core.infra.meta.Table;
import com.github.codingdebugallday.driver.mysql.session.meta.MysqlColumnExtra;
import com.github.codingdebugallday.driver.mysql.session.meta.MysqlTableExtra;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * description
 * </p>
 *
 * @author JupiterMouse 2020/07/10
 * @since 1.0.0
 */
public class MysqlDriverSession extends AbstractRdbmsDriverSession {

    private static final String TABLE_METADATA_SQL = "select " +
            "table_rows as tableRows," +
            "data_length as dataLength," +
            "table_comment as tableComment" +
            " from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = '%s' and TABLE_NAME = '%s'";

    public MysqlDriverSession(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Table tableMetaDetail(String schema, String tableName) {
        List<Map<String, Object>> metaDataMapList = this.executeOneQuery(schema, String.format(TABLE_METADATA_SQL, schema, tableName));
        MysqlTableExtra tableExtra = new MysqlTableExtra();
        Table table = new Table();
        // basic info
        try {
            table.init(this.dataSource.getConnection(), schema, schema, tableName);
        } catch (SQLException e) {
            throw new DriverException("connection error", e);
        }
        // 表额外信息
        if (CollectionUtils.isEmpty(metaDataMapList)) {
            // nothing to do
        } else {
            try {
                BeanUtils.populate(tableExtra, metaDataMapList.get(0));
                table.setExtra(BeanUtils.describe(tableExtra));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 字段额外信息
        List<Column> columnList = table.getColumnList();
        columnList.forEach(column -> {
            MysqlColumnExtra columnExtra = new MysqlColumnExtra();
            boolean flag = false;
            if (Objects.nonNull(table.getPkMap().get(column.getColumnName()))) {
                columnExtra.setPkFlag(1);
                flag = true;
            }
            if (Objects.nonNull(table.getFkMap().get(column.getColumnName()))) {
                columnExtra.setFkFlag(1);
                flag = true;
            }
            if (Objects.nonNull(table.getIkMap().get(column.getColumnName()))) {
                columnExtra.setIndexFlag(1);
                flag = true;
            }
            if (flag) {
                try {
                    column.setExtra(BeanUtils.describe(columnExtra));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return table;
    }

}
