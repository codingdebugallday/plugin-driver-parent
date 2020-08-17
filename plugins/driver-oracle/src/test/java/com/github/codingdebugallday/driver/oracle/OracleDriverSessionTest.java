package com.github.codingdebugallday.driver.oracle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.codingdebugallday.driver.core.app.service.session.DriverSession;
import com.github.codingdebugallday.driver.core.infra.meta.*;
import com.github.codingdebugallday.driver.oracle.session.OracleDriverSessionFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * Session接口测试
 * </p>
 *
 * @author JupiterMouse 2020/08/03
 * @since 1.0
 */
public class OracleDriverSessionTest {

    private DriverSession driverSession;

    @Before
    public void buildOracleSession() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:oracle:thin:@172.23.16.75:21521:ORCLCDB");
        dataSource.setUsername("system");
        dataSource.setPassword("123456");
        dataSource.setSchema("C##TEST");
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        OracleDriverSessionFactory oracleDriverSessionFactory = new OracleDriverSessionFactory();
        oracleDriverSessionFactory.setDataSource(dataSource);
        this.driverSession = oracleDriverSessionFactory.getDriverSession();
    }

    //===============================================================================
    //  SchemaSession
    //===============================================================================


    /**
     * 获取所有数据库
     */
    @Test
    public void testSchemaList() {
        List<String> schemaList = driverSession.schemaList();
        System.out.println(schemaList);
        assertFalse(CollectionUtils.isEmpty(schemaList));
    }

    @Test
    public void testSchemaCreate() {
        boolean schemaCreateFlag = driverSession.schemaCreate("test123");
        assertTrue(schemaCreateFlag);
    }

    /**
     * 统计条数,可以填表名,也可以填sql
     */
    @Test
    public void testQueryCount() {
        String sql = "select * from test05;";
        Long count = driverSession.queryCount(null, sql);
        System.out.println(count);
        assertNotNull(count);
    }

    /**
     * 获取当前schema
     */
    @Test
    public void testCurrentSchema() {
        String currentSchema = driverSession.currentSchema();
        System.out.println(currentSchema);
        assertTrue(currentSchema == null || currentSchema.isEmpty());
    }

    /**
     * oracle不支持Catalog,参考:https://blog.csdn.net/yz_blanks/article/details/102805809
     */
    @Test
    public void testCurrentCatalog() {
        String currentCatalog = driverSession.currentCatalog();
        System.out.println(currentCatalog);
    }


    /**
     * 批量执行
     */
    @Test
    public void testExecuteAll() {
        // 增
        String sql1 = "select sysdate from dual;";
        String sql2 = "select 1 from dual;";
        String sql3 = "select 2 from dual;";
        // 插
        // 查
        // 删除
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sql1).append("\n");
        stringBuilder.append(sql2).append("\n");
        stringBuilder.append(sql3).append("\n");

        List<List<Map<String, Object>>> executeAll = driverSession.executeAll("C##TEST", stringBuilder.toString(), true);
        assertFalse(executeAll.isEmpty());
    }


    //===============================================================================
    //  TableSession
    //===============================================================================

    /**
     * 查看库下所有的表
     */
    @Test
    public void testTableList() {
        List<String> tableList = driverSession.tableList(driverSession.currentSchema());
        tableList.forEach(System.out::println);
        assertFalse(CollectionUtils.isEmpty(tableList));
    }

    /**
     * oracle无法直接获取主键
     */
    @Test
    public void testTablePk() {
        List<PrimaryKey> primaryKeys = driverSession.tablePk("C##TEST", "test05");
        primaryKeys.forEach(System.out::println);
    }

    /**
     * 获取外键
     */
    @Test
    public void testTableFk() {
        List<ForeignKey> foreignKeys = driverSession.tableFk("C##TEST", "test05");
        foreignKeys.forEach(System.out::println);
    }

    /**
     * 获取索引
     */
    @Test
    public void testTableIndex() {
        List<IndexKey> indexKeyList = driverSession.tableIndex("SYSTEM", "HELP");
        indexKeyList.forEach(System.out::println);
    }

    /**
     * 表是否已经存在,区分表名大小写
     */
    @Test
    public void testTableExists() {
        boolean tableExists = driverSession.tableExists("C##TEST", "TEST05");
        System.out.println(tableExists);
    }

    /**
     * 获取库下面所有视图
     */
    @Test
    public void testViews() {
        List<String> views = driverSession.viewList("SYS");
        views.forEach(System.out::println);
    }

    /**
     * 查询表
     */
    @Test
    public void testTableQuery() {
        List<Map<String, Object>> tableQuery = driverSession.tableQuery("C##TEST", "MYSQL_ALL_COL");
        System.out.println(tableQuery);
        assertFalse(tableQuery.isEmpty());
    }

    /**
     * 查询语句
     */
    @Test
    public void testSelect() {
        List<List<Map<String, Object>>> result = driverSession.executeAll("C##TEST", "select * from MYSQL_ALL_COL", true);
        System.out.println(result);
        assertFalse(result.isEmpty());
    }

    /**
     * 新增
     */
    @Test
    public void tableInsert() {
        List<Tuple<String, String>> values = new ArrayList<>();
        Tuple<String, String> v1 = new Tuple<>();
        v1.setFirst("name");
        v1.setSecond("tony");
        Tuple<String, String> v2 = new Tuple<>();
        v2.setFirst("age");
        v2.setSecond("18");
        values.add(v1);
        values.add(v2);
        boolean b = driverSession.tableInsert("C##TEST", "TEST_CXK", values);
        System.out.println(b);
    }

    //===============================================================================
    //  TableSession
    //===============================================================================

    /**
     * 获取JDBC元数据,不带额外信息
     */
    @Test
    public void testTableMetaData() {
        Table table = driverSession.tableMetaData("C##TEST", "MYSQL_ALL_COL");
        System.out.println(table);
        assertTrue(Objects.nonNull(table));
    }

    /**
     * 查看表详细元数据,包含索引,列
     */
    @Test
    public void tableMetaExtra() {
        Table table = driverSession.tableMetaExtra("C##TEST", "MYSQL_ALL_COL");
        System.out.println(table);
        assertTrue(Objects.nonNull(table));
    }

    /**
     * 验证是否正确
     */
    @Test
    public void isValid() {
        boolean valid = driverSession.isValid();
        assertTrue(valid);
    }

    @Test
    public void columnMetaData() {

    }

    /**
     * 查看schema元数据信息
     */
    @Test
    public void testsSchemaMetaExtra() {
        Schema schema = driverSession.schemaMetaExtra(driverSession.currentSchema());
        System.out.println(schema);
        assertTrue(Objects.nonNull(schema));
    }

    /**
     * 获取数据源信息,里面有数据库版本
     */
    @Test
    public void testCatalogMetaExtra() {
        Catalog catalog = driverSession.catalogMetaExtra();
        // Catalog(tableCat=null, databaseProductName=Oracle, databaseProductVersion=Oracle Database 12c Enterprise Edition Release 12.1.0.2.0 - 64bit Production
        System.out.println(catalog);
        assertTrue(Objects.nonNull(catalog));
    }

    @Test
    public void testShowCreateSql() {
        String showTableSql = driverSession.showCreateSql("C##TEST", "MYSQL_ALL_COL", ShowType.TABLE);
        System.out.println(showTableSql);
        assertFalse(showTableSql.isEmpty());

        /*String showViewSql = driverSession.showCreateSql("hdsp_core", "xcor_datasource_assign_v", ShowType.VIEW);
        System.out.println(showViewSql);
        assertFalse(showViewSql.isEmpty());*/

    }
}
