package generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 *
 * @author huangxy
 * @date 2017-10-26
 */
public class MysqlGenerator {


    /**
     * 工程基础目录
     */
//    public final static String PROJECT_PATH = "E:\\deve\\workspace\\xlauch\\code\\xlauch\\xlauch-web\\src\\main\\";
//    public final static String modName = "deve";
//    public final static String AUTHOR = "伊凡";
//    public final static String TABLE_NAME = "t_deve_exp_param";



    /**
     * 工程基础目录
     */

    public final static String PROJECT_PATH = "E:\\xlauch\\xlauch\\xlauch-web\\src\\main\\" ;
    public final static String modName = "sys";
    public final static String AUTHOR = "huangxy";
    public final static String TABLE_NAME = "t_sys_dict";







    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("t_sys_user", FieldFill.INSERT_UPDATE));


        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();


        /**
         *  自定义生成列表页面
         */
        focList.add(new FileOutConfig("/templates/list.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String jspName = tableInfo.getEntityName();
                jspName = jspName.substring(0, 1).toLowerCase() + jspName.substring(1);
                // 自定义输入文件名称
                return PROJECT_PATH + "webapp//pages//" + modName + "/" + jspName + "List.html";
            }
        });

        /**
         *  自定义生成新增页面
         */
        focList.add(new FileOutConfig("/templates/add.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String jspName = tableInfo.getEntityName();
                jspName = jspName.substring(0, 1).toLowerCase() + jspName.substring(1);
                // 自定义输入文件名称
                return PROJECT_PATH + "webapp//pages//" + modName + "/" + jspName + "Add.html";
            }
        });

        /**
         *  自定义生成修改页面
         */
        focList.add(new FileOutConfig("/templates/edit.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String jspName = tableInfo.getEntityName();
                jspName = jspName.substring(0, 1).toLowerCase() + jspName.substring(1);
                // 自定义输入文件名称
                return PROJECT_PATH + "webapp//pages//" + modName + "/" + jspName + "Edit.html";
            }
        });






        /**
         * 生成mapper
         */
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String jspName = tableInfo.getEntityName();
                jspName = jspName.substring(0, 1).toLowerCase() + jspName.substring(1);
                // 自定义输入文件名称
                return PROJECT_PATH + "resources//mapper//" + modName + "/" + tableInfo.getEntityName() + ".xml";
            }
        });


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        //输出目录
                        .setOutputDir(PROJECT_PATH + "java")
                        // 是否覆盖文件
                        .setFileOverride(true)
                        // 开启 activeRecord 模式
                        .setActiveRecord(true)
                        // XML 二级缓存
                        .setEnableCache(false)
                        // XML ResultMap
                        .setBaseResultMap(true)
                        // XML columList
                        .setBaseColumnList(true)
                        .setAuthor(AUTHOR)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                // .setMapperName("%sDao")
                // .setXmlName("%sDao")
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        // 数据库类型
                        .setDbType(DbType.MYSQL)
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                //    return DbColumnType.BOOLEAN;
                                // }
                                return super.processTypeConvert(fieldType);
                            }
                        })
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUsername("xlauch")
                        .setPassword("Xlauch@123")
                        .setUrl("jdbc:mysql://39.106.64.79:3306/xlauch?characterEncoding=utf8")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        // .setDbColumnUnderline(true)//全局下划线命名
                        .setTablePrefix(new String[]{"t_", "mp_"})// 此处可以修改为您的表前缀
                        // 表名生成策略
                        .setNaming(NamingStrategy.underline_to_camel)
                        // 需要生成的表
                        .setInclude(new String[]{TABLE_NAME})
                        //.setExclude(new String[]{"t_deve_exp"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
                        //.setSuperEntityColumns(new String[]{"test_id"})
                        .setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                        .setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                        .setSuperMapperClass("com.xlauch.core.supers.mapper.SuperMapper")
                        // 自定义 service 父类
                        .setSuperServiceClass("com.xlauch.core.supers.service.SuperService")
                        // 自定义 service 实现类父类
                        .setSuperServiceImplClass("com.xlauch.core.supers.service.SuperServiceImpl")
                        // 自定义 controller 父类
                        .setSuperControllerClass("com.xlauch.core.supers.web.BaseController")
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new MypackageConfig()
                        // 设置模块名称
                        .setModuleName(modName)
                        // 自定义包路径
                        .setParent("com.xlauch.web")
                        // 这里是控制器包名，默认 web
                        .setController("controller")
                        .setEntity("entity")
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>(1);
                        map.put("lowName", getLowName());
                        this.setMap(map);
                    }
                }.setFileOutConfigList(focList)
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                // .setController("/templates/controller.java.vm")
                // .setEntity("...");
                // .setMapper("...");
                // .setXml("...");
                // .setService("...");
                // .setServiceImpl("...");
        );

        //执行生成
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

    private static String getLowName() {
        String lowName = TABLE_NAME.replaceFirst("t_", "");
        while (lowName.indexOf("_") != -1) {
            int pos = lowName.indexOf("_");
            lowName = lowName.substring(0, pos) + (lowName.charAt(pos + 1) + "").toUpperCase() + lowName.substring(pos + 2);
        }

        return lowName;
    }

    public static void main2(String[] args) {
        System.out.println("" + getLowName());
    }
}