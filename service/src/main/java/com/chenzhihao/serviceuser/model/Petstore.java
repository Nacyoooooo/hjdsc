package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName petstore
 */
@TableName(value ="petstore")
public class Petstore implements Serializable {
    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物的编号
     */
    private Integer pid;

    /**
     * 玩家的编号
     */
    private Integer uid;

    /**
     * 血量 0-31
     */
    private Integer healthpoint;

    /**
     * 物理攻击力 0-31
     */
    private Integer physicaldamagepoint;

    /**
     * 法术攻击力 0-31
     */
    private Integer magicaldamagepoin;

    /**
     * 物理防御力 0-31
     */
    private Integer physicaldefence;

    /**
     * 法术防御力 0-31
     */
    private Integer magicaldefence;

    /**
     * 速度 0-31
     */
    private Integer speed;

    /**
     * 宠物获取时间
     */
    private Date createtime;

    /**
     * 宠物更新时间
     */
    private Date updatetime;
    private Integer performed;

    public Integer getPerformed() {
        return performed;
    }

    public void setPerformed(Integer performed) {
        this.performed = performed;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键自增
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
     * 玩家的编号
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 玩家的编号
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 血量 0-31
     */
    public Integer getHealthpoint() {
        return healthpoint;
    }

    /**
     * 血量 0-31
     */
    public void setHealthpoint(Integer healthpoint) {
        this.healthpoint = healthpoint;
    }

    /**
     * 物理攻击力 0-31
     */
    public Integer getPhysicaldamagepoint() {
        return physicaldamagepoint;
    }

    /**
     * 物理攻击力 0-31
     */
    public void setPhysicaldamagepoint(Integer physicaldamagepoint) {
        this.physicaldamagepoint = physicaldamagepoint;
    }

    /**
     * 法术攻击力 0-31
     */
    public Integer getMagicaldamagepoin() {
        return magicaldamagepoin;
    }

    /**
     * 法术攻击力 0-31
     */
    public void setMagicaldamagepoin(Integer magicaldamagepoin) {
        this.magicaldamagepoin = magicaldamagepoin;
    }

    /**
     * 物理防御力 0-31
     */
    public Integer getPhysicaldefence() {
        return physicaldefence;
    }

    /**
     * 物理防御力 0-31
     */
    public void setPhysicaldefence(Integer physicaldefence) {
        this.physicaldefence = physicaldefence;
    }

    /**
     * 法术防御力 0-31
     */
    public Integer getMagicaldefence() {
        return magicaldefence;
    }

    /**
     * 法术防御力 0-31
     */
    public void setMagicaldefence(Integer magicaldefence) {
        this.magicaldefence = magicaldefence;
    }

    /**
     * 速度 0-31
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * 速度 0-31
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     * 宠物获取时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 宠物获取时间
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
        Petstore other = (Petstore) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getHealthpoint() == null ? other.getHealthpoint() == null : this.getHealthpoint().equals(other.getHealthpoint()))
            && (this.getPhysicaldamagepoint() == null ? other.getPhysicaldamagepoint() == null : this.getPhysicaldamagepoint().equals(other.getPhysicaldamagepoint()))
            && (this.getMagicaldamagepoin() == null ? other.getMagicaldamagepoin() == null : this.getMagicaldamagepoin().equals(other.getMagicaldamagepoin()))
            && (this.getPhysicaldefence() == null ? other.getPhysicaldefence() == null : this.getPhysicaldefence().equals(other.getPhysicaldefence()))
            && (this.getMagicaldefence() == null ? other.getMagicaldefence() == null : this.getMagicaldefence().equals(other.getMagicaldefence()))
            && (this.getSpeed() == null ? other.getSpeed() == null : this.getSpeed().equals(other.getSpeed()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getHealthpoint() == null) ? 0 : getHealthpoint().hashCode());
        result = prime * result + ((getPhysicaldamagepoint() == null) ? 0 : getPhysicaldamagepoint().hashCode());
        result = prime * result + ((getMagicaldamagepoin() == null) ? 0 : getMagicaldamagepoin().hashCode());
        result = prime * result + ((getPhysicaldefence() == null) ? 0 : getPhysicaldefence().hashCode());
        result = prime * result + ((getMagicaldefence() == null) ? 0 : getMagicaldefence().hashCode());
        result = prime * result + ((getSpeed() == null) ? 0 : getSpeed().hashCode());
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
        sb.append(", uid=").append(uid);
        sb.append(", healthpoint=").append(healthpoint);
        sb.append(", physicaldamagepoint=").append(physicaldamagepoint);
        sb.append(", magicaldamagepoin=").append(magicaldamagepoin);
        sb.append(", physicaldefence=").append(physicaldefence);
        sb.append(", magicaldefence=").append(magicaldefence);
        sb.append(", speed=").append(speed);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}