package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName reason
 */
@TableName(value ="reason")
@Data
public class Reason implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 玩家id
     */
    private Integer uid;

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 原因/理由
     */
    private String description;

    /**
     * 该申请的原因
     */
    private Integer type;

    /**
     * 签到数据创建时间
     */
    private Date createtime;

    /**
     * 签到数据更新时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }


}