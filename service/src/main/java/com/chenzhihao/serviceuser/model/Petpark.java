package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName petpark
 */
@TableName(value ="petpark")
public class Petpark implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 宠物的编号
     */
    private Integer pid;

    /**
     * 宠物的数量
     */
    private Integer count;

    /**
     * 是否可被捕捉 1是可以 2是不可以
     */
    private Integer catched;

    /**
     * 宠物创建时间
     */
    private Date createtime;

    /**
     * 宠物更新时间
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
     * 宠物的数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 宠物的数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 是否可被捕捉 1是可以 2是不可以
     */
    public Integer getCatched() {
        return catched;
    }

    /**
     * 是否可被捕捉 1是可以 2是不可以
     */
    public void setCatched(Integer catched) {
        this.catched = catched;
    }

    /**
     * 宠物创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 宠物创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 宠物更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 宠物更新时间
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
        Petpark other = (Petpark) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getCatched() == null ? other.getCatched() == null : this.getCatched().equals(other.getCatched()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getCatched() == null) ? 0 : getCatched().hashCode());
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
        sb.append(", pid=").append(pid);
        sb.append(", count=").append(count);
        sb.append(", catched=").append(catched);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}