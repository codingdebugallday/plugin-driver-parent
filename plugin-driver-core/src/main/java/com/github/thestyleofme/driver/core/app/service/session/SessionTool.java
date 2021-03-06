package com.github.thestyleofme.driver.core.app.service.session;

import static com.github.thestyleofme.plugin.core.infra.constants.BaseConstant.Symbol.SEMICOLON;

import com.github.thestyleofme.driver.core.app.service.session.funcations.extractor.*;
import com.github.thestyleofme.driver.core.app.service.session.funcations.setter.SchemaSetter;
import org.apache.logging.log4j.util.Strings;

/**
 * <p>
 * 设置器、提取器接口工具
 * </p>
 *
 * @author JupiterMouse 2020/07/08
 * @since 1.0.0
 */
public interface SessionTool {

    // 设置器

    /**
     * schema设置
     *
     * @return SchemaSetter
     */
    default SchemaSetter schemaSetter() {
        throw new UnsupportedOperationException("Not Implement");
    }

    // 提取器

    /**
     * 数据库schema提取
     *
     * @return SchemaExtractor
     */
    default SchemaExtractor schemaExtractor() {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 表提取
     *
     * @return TableExtractor
     */
    default TableExtractor tableExtractor() {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 表主键提取
     *
     * @return tableIndexExtractor
     */
    default TablePkExtractor tablePkExtractor() {
        throw new UnsupportedOperationException("Not Implement");

    }

    /**
     * 表索引提取
     *
     * @return tableIndexExtractor
     */
    default TableIndexExtractor tableIndexExtractor() {
        throw new UnsupportedOperationException("Not Implement");

    }

    /**
     * 表结构提取
     *
     * @return TableStructureExtractor
     */
    default TableStructureExtractor tableStructureExtractor() {
        throw new UnsupportedOperationException("Not Implement");
    }

    // other

    /**
     * 分页提取
     *
     * @return PageSqlExtractor
     */
    default PageSqlExtractor pageSqlExtractor() {
        return (pageFormat, sql, pageable) -> {
            long page = pageable.getPageNumber();
            long size = pageable.getPageSize();
            long offset = page * size;
            String trimSql = sql.trim();
            if (trimSql.endsWith(SEMICOLON)) {
                sql = trimSql.substring(0, trimSql.length() - 1);
            }
            if (pageable.getSort().isSorted()) {
                sql = sql + " order by " + Strings.join(pageable.getSort().iterator(), ',').replace(":", " ");
            }
            return String.format(pageFormat, sql, offset, size);
        };
    }

}
