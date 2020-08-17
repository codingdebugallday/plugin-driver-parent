package com.github.codingdebugallday.driver.core.infra.generator;

import static com.github.codingdebugallday.plugin.core.infra.constants.BaseConstant.Symbol.SPACE;

import java.util.List;

import com.github.codingdebugallday.driver.core.infra.meta.*;

/**
 * <p>
 * 生成建表语句
 *
 * @author tianle.liu
 * </p>
 * @since 1.0
 */
public interface SqlGenerator {

    String NULL = "null";
    String DEFAULT = SPACE + "DEFAULT" + SPACE;
    String NOT_NULL = SPACE + "NOT NULL" + SPACE;
    String AUTO_INCREMENT = SPACE + "AUTO_INCREMENT";
    String YES = "YES";
    String NO = "NO";
    String PRIMARY_KEY = SPACE + "primary key" + SPACE;
    int VARCHAR_LIMIT_LENGTH = 4000;
    int CHAR_LIMIT_LENGTH = 255;

    //===============================================================================
    //  Table
    //===============================================================================

    /**
     * 基于Table进行建表语句
     *
     * @param table 表元数据
     * @return 基于Table进行建表语句
     */
    default String createTable(Table table) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 修改表名Sql
     *
     * @param table        table 表元数据
     * @param newTableName 新的表名
     * @return 修改表名Sql
     */
    default String renameTable(Table table, String newTableName) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 删除表的SQL
     *
     * @param table table 表元数据
     * @return 删除表的SQL
     */
    default String dropTable(Table table) {
        throw new UnsupportedOperationException("Not Implement");
    }

    //===============================================================================
    //  Column
    //===============================================================================

    /**
     * 增加表字段Sql
     *
     * @param column 字段
     * @return 增加表字段Sql
     */
    default String addColumn(Column column) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 重命名字段名SQL
     *
     * @param column       字段
     * @param newFieldName 新字段名称
     * @return 重命名字段名SQL
     */
    default String renameColumn(Column column, String newFieldName) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 修改字段类型的SQL
     *
     * @param column    字段
     * @param newColumn 新字段
     * @return 修改字段类型的SQL
     */
    default String modifyColumnType(Column column, Column newColumn) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 删除字段
     *
     * @param column 字段元数据
     * @return 删除字段的SQL
     */
    default String dropColumn(Column column) {
        throw new UnsupportedOperationException("Not Implement");
    }

    //===============================================================================
    //  IndexKey
    //===============================================================================

    /**
     * 增加索引的Sql（可传入过滤）
     *
     * @param schemaName 表模式
     * @param tableName  表名
     * @param ikList     索引 元数据
     * @param ignoreIkNames 过滤的ignoreIkNames
     * @return 增加索引的Sql
     */
    default String addIndex(String schemaName, String tableName, List<IndexKey> ikList, List<String> ignoreIkNames) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 增加索引的Sql
     *
     * @param schemaName 表模式
     * @param tableName  表名
     * @param ikList     索引 元数据
     * @return 增加索引的Sql
     */
    default String addIndex(String schemaName, String tableName, List<IndexKey> ikList) {
        throw new UnsupportedOperationException("Not Implement");
    }
    /**
     * 删除索引的SQL
     *
     * @param ik 索引 元数据
     * @return 删除索引的SQL
     */
    default String dropIndex(IndexKey ik) {
        throw new UnsupportedOperationException("Not Implement");
    }

    //===============================================================================
    //  PrimaryKey
    //===============================================================================

    /**
     * 增加主键的SQL
     *
     * @param schemaName 表模式
     * @param tableName  表名
     * @param pkList     主键 元数据
     * @return 增加主键的SQL
     */
    default String addPrimaryKey(String schemaName, String tableName, List<PrimaryKey> pkList) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 删除主键的SQL
     *
     * @param pk 主键 元数据
     * @return 删除外键的SQL
     */
    default String dropPrimaryKey(PrimaryKey pk) {
        throw new UnsupportedOperationException("Not Implement");
    }

    //===============================================================================
    //  ForeignKey
    //===============================================================================

    /**
     * 增加外键的SQL
     *
     * @param schemaName 表模式
     * @param tableName  表名
     * @param fkList     外键 元数据
     * @return 增加主键的SQL
     */
    default String addForeignKey(String schemaName, String tableName, List<ForeignKey> fkList) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 删除外键的SQL
     *
     * @param fk 外键 元数据
     * @return 删除外键的SQL
     */
    default String dropForeignKey(ForeignKey fk) {
        throw new UnsupportedOperationException("Not Implement");
    }

    //===============================================================================
    //  convert
    //===============================================================================

    /**
     * 类型SQL生成
     *
     * @param column 字段
     * @return 类型SQL生成
     */
    default String convertType(Column column) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 尽可能使用convertType(Column column)
     *
     * @param column 字段
     * @return 类型SQL生成
     * @see com.github.codingdebugallday.driver.core.infra.generator.AbstractRdbmsSqlGenerator#convertType(Column column)
     * <p>
     * 通过类型名称转换TYPE
     */
    @Deprecated
    default String nameConvertType(Column column) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 类型SQL生成
     *
     * @param columnDef 默认值
     * @return 类型SQL生成
     */
    default String convertColumnDef(String columnDef) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * Nullable 转换
     *
     * @param nullable 为空状态
     * @return 为NULL状态 SQL部分
     */
    default String convertNull(Integer nullable) {
        throw new UnsupportedOperationException("Not Implement");
    }

    /**
     * 是否自增
     *
     * @param column 列
     * @param table  表
     * @return 自增SQL部分
     */
    default String convertAutoincrement(Table table, Column column) {
        throw new UnsupportedOperationException("Not Implement");
    }

}
