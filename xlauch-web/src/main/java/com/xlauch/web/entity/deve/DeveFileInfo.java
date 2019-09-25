package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 文件信息表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveFileInfo.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-12-05
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_file_info")
public class DeveFileInfo extends Model<DeveFileInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="file_id", type= IdType.AUTO)
	private Integer fileId;
    /**
     * 文件上传名称
     */
	@TableField("upload_name")
	private String uploadName;
    /**
     * 文件实际保存名称
     */
	@TableField("save_name")
	private String saveName;
    /**
     * 文件大小
     */
	private String size;
    /**
     * 文件类型
     */
	private String type;
    /**
     * 文件md5
     */
	private String md5;
    /**
     * 是否上传完成(0:未,1:完成)
     */
	private Integer finish;
    /**
     * 排序
     */
	private Integer seq;
    /**
     * 备注
     */
	private String note;
    /**
     * 文件路径类型（系统参数）
     */
	@TableField("path_type")
	private String pathType;
    /**
     * 文件路径
     */
	private String path;
    /**
     * 文件完整路径
     */
	@TableField("path_all")
	private String pathAll;
    /**
     * 上传批次号
     */
	private String batcode;
    /**
     * 创建人员
     */
	private String createuser;
    /**
     * 创建时间
     */
	private Date createtime;


	@Override
	protected Serializable pkVal() {
		return this.fileId;
	}

}
