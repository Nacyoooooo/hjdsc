package com.chenzhihao.serviceutil.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName signs
 */
@TableName(value ="signs")
public class Signs implements Serializable {
    /**
     * 主键,宠物的编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 签到的年份
     */
    private Integer signyear;

    /**
     * 签到的月份
     */
    private Integer signmonth;

    /**
     * 签到的数据，转化为十进制后
     */
    private Integer signdata;

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
     * 主键,宠物的编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键,宠物的编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 用户id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 签到的年份
     */
    public Integer getSignyear() {
        return signyear;
    }

    /**
     * 签到的年份
     */
    public void setSignyear(Integer signyear) {
        this.signyear = signyear;
    }

    /**
     * 签到的月份
     */
    public Integer getSignmonth() {
        return signmonth;
    }

    /**
     * 签到的月份
     */
    public void setSignmonth(Integer signmonth) {
        this.signmonth = signmonth;
    }

    /**
     * 签到的数据，转化为十进制后
     */
    public Integer getSigndata() {
        return signdata;
    }

    /**
     * 签到的数据，转化为十进制后
     */
    public void setSigndata(Integer signdata) {
        this.signdata = signdata;
    }

    /**
     * 签到数据创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 签到数据创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 签到数据更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 签到数据更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Signs other = (Signs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getSignyear() == null ? other.getSignyear() == null : this.getSignyear().equals(other.getSignyear()))
            && (this.getSignmonth() == null ? other.getSignmonth() == null : this.getSignmonth().equals(other.getSignmonth()))
            && (this.getSigndata() == null ? other.getSigndata() == null : this.getSigndata().equals(other.getSigndata()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getSignyear() == null) ? 0 : getSignyear().hashCode());
        result = prime * result + ((getSignmonth() == null) ? 0 : getSignmonth().hashCode());
        result = prime * result + ((getSigndata() == null) ? 0 : getSigndata().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", signyear=").append(signyear);
        sb.append(", signmonth=").append(signmonth);
        sb.append(", signdata=").append(signdata);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}