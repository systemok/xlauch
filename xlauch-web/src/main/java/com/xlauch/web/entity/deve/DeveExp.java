package com.xlauch.web.entity.deve;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 类描述    : 通用查询、导出 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExp.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_exp")
public class DeveExp extends Model<DeveExp> implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="exp_id", type= IdType.AUTO)
	private Integer expId;
    /**
     * 报表名称
     */
	private String name;
	/**
	 * 数据源
	 */
	private String datasource;
    /**
     * 编码
     */
	private String code;
    /**
     * 报表SQL
     */
	private String sqlx;
    /**
     * 页面脚本
     */
	private String script;
    /**
     * 页面路径
     */
	@TableField("page_path")
	private String pagePath;
    /**
     * 是否导出TXT 1:是  0:否
     */
	@TableField("txt_able")
	private Integer txtAble;
    /**
     * 是否导出EXCEL 1:是  0:否
     */
	@TableField("excel_able")
	private Integer excelAble;
    /**
     * EXCEL是否打印标题 1:是  0:否
     */
	@TableField("printtitle_able")
	private Integer printtitleAble;
    /**
     * EXCEL导出开始行
     */
	@TableField("begin_row")
	private Integer beginRow;
    /**
     * EXCEL导出开始列
     */
	@TableField("begin_col")
	private Integer beginCol;
    /**
     * EXCEL模板路径
     */
	@TableField("excel_temp")
	private String excelTemp;
    /**
     * 文本分隔符
     */
	@TableField("split_char")
	private String splitChar;
    /**
     * 文件编码
     */
	private String encoding;
    /**
     * 是否导出XML 1:是  0:否
     */
	@TableField("xml_able")
	private Integer xmlAble;
    /**
     * XML根节点名称
     */
	@TableField("root_name")
	private String rootName;
    /**
     * XML节点名称
     */
	@TableField("nodes_name")
	private String nodesName;
    /**
     * 换行符
     */
	@TableField("line_char")
	private String lineChar;
    /**
     * 添加时间
     */
	@TableField("add_time")
	private Date addTime;
    /**
     * 是否生成页面 1:是  0:否
     */
	@TableField("page_able")
	private Integer pageAble;
    /**
     * 是否FITCOLUMN 1:是  0:否 
     */
	@TableField("fit_able")
	private Integer fitAble;


	@Override
	protected Serializable pkVal() {
		return this.expId;
	}

}
