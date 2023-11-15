package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName capturerecord
 */
@TableName(value ="capturerecord")
public class Capturerecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 宠物园捕捉项目的编号
     */
    private Integer cid;

    /**
     * 玩家的id
     */
    private Integer uid;

    /**
     * 宠物的编号
     */
    private Integer pid;

    /**
     * 记录创建时间
     */
    private Date createtime;

    /**
     * 记录更新时间
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

    /**
     * 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 宠物园捕捉项目的编号
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 宠物园捕捉项目的编号
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 玩家的id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 玩家的id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 宠物的编号
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 宠物的编号
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 记录创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 记录创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 记录更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 记录更新时间
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
        Capturerecord other = (Capturerecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
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
        sb.append(", cid=").append(cid);
        sb.append(", uid=").append(uid);
        sb.append(", pid=").append(pid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}