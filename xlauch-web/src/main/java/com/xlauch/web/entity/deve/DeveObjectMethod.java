package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 二次开发--模块管理--模块方法表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveObjectMethod.java <br/>
 * </p>
 * @author 伊凡
 * @since 2018-01-23
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_object_method")
public class DeveObjectMethod extends Model<DeveObjectMethod> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="method_id", type= IdType.AUTO)
	private Integer methodId;
    /**
     * 对象ID id为-1时，表示该方法为系统预设方法
     */
	@TableField("obj_id")
	private Integer objId;
    /**
     * 方法编码
     */
	private String code;
    /**
     * 模块名称(中文）
     */
	private String name;
    /**
     * 按钮图标
     */
	private String icon;
	@TableField("icon_path")
	private String iconPath;
    /**
     * 图标位置 1:左边  2:右边
     */
	@TableField("icon_position")
	private Integer iconPosition;
    /**
     * 保存方法（新增 ADD、修改 EDIT、删除 DEL、查询 QUERY）
     */
	@TableField("method_type")
	private String methodType;
    /**
     * 展示类型 列表，弹窗，异步
     */
	@TableField("show_type")
	private String showType;
    /**
     * 是否自适应 1：是 0：否
     */
	@TableField("fit_cloumn")
	private Integer fitCloumn;
    /**
     * 显示宽度
     */
	private Integer width;
    /**
     * 显示高度
     */
	private Integer height;
    /**
     * 排序
     */
	private Integer seq;
    /**
     * 提示内容
     */
	private String msg;
    /**
     * 默认方法 1：是 0：否
     */
	@TableField("default_method")
	private Integer defaultMethod;
    /**
     * 视图SQL
     */
	@TableField("view_sql")
	private String viewSql;
    /**
     * 是否单选
     */
	@TableField("single_able")
	private Integer singleAble;
    /**
     * 是否初始加载
     */
	@TableField("first_load")
	private Integer firstLoad;
    /**
     * 默认排序字段(desc)
     */
	@TableField("default_order")
	private String defaultOrder;
    /**
     * 排序方式 desc：降序 asc：升序
     */
	@TableField("order_type")
	private String orderType;
    /**
     * 依赖JS文件
     */
	@TableField("diy_js")
	private String diyJs;
    /**
     * 初始化字典
     */
	@TableField("init_dict")
	private String initDict;
    /**
     * 页面生成目录
     */
	@TableField("page_path")
	private String pagePath;
    /**
     * 是否可用 1：可用 0：不可用
     */
	private Integer status;
    /**
     * 描述
     */
	private String note;

	/**
	 * 字段列表
	 */
	@TableField(exist=false)
	private List<DeveObjectColumn> columnList ;

	/**
	 * 参数列表
	 */
	@TableField(exist=false)
	private List<DeveObjectParam> paramList ;

	@Override
	protected Serializable pkVal() {
		return this.methodId;
	}

}
