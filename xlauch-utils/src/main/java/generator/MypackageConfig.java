package generator;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 重写自动生成包信息
 * </p>
 *
 * @author huangxy
 * @since 2017/11/9.
 */
public class MypackageConfig extends PackageConfig {
    private String parent = "com.baomidou";
    private String moduleName = null;
    private String entity = "entity";
    private String service = "service";
    private String serviceImpl = "service.impl";
    private String modServiceImpl = "impl";
    private String mapper = "mapper";
    private String xml = "mapper.xml";
    private String controller = "web";

    public MypackageConfig() {
    }

    @Override
    public String getParent() {
//        return StringUtils.isNotEmpty(this.moduleName)?  this.parent + "." + this.moduleName:this.parent;
        return this.parent;
    }

    @Override
    public PackageConfig setParent(String parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public String getModuleName() {
        return this.moduleName;
    }

    @Override
    public PackageConfig setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    @Override
    public String getEntity() {
        return StringUtils.isNotEmpty(this.moduleName)?  this.entity + "." + this.moduleName:this.entity;
    }

    @Override
    public PackageConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public String getService() {
        return StringUtils.isNotEmpty(this.moduleName)?  this.service + "." + this.moduleName:this.service;
    }

    @Override
    public PackageConfig setService(String service) {
        this.service = service;
        return this;
    }

    @Override
    public String getServiceImpl() {
        return StringUtils.isNotEmpty(this.moduleName)?  this.service + "." + this.moduleName + "." + this.modServiceImpl :this.serviceImpl;
    }

    @Override
    public PackageConfig setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }

    @Override
    public String getMapper() {
        return StringUtils.isNotEmpty(this.moduleName)?  this.mapper + "." + this.moduleName:this.mapper;
    }

    @Override
    public PackageConfig setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    @Override
    public String getXml() {
        return StringUtils.isNotEmpty(this.moduleName)?  this.xml + "." + this.moduleName:this.xml;
    }

    @Override
    public PackageConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }

    @Override
    public String getController() {
        this.controller = StringUtils.isEmpty(this.controller)?"web":this.controller;
        return StringUtils.isNotEmpty(this.moduleName)?  this.controller + "." + this.moduleName:this.controller;
    }

    @Override
    public PackageConfig setController(String controller) {
        this.controller = controller;
        return this;
    }
}
