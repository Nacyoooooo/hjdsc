package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName petsconfig
 */
@TableName(value ="petsconfig")
public class Petsconfig implements Serializable {
    /**
     * 主键,宠物的编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物的名字
     */
    private String name;

    /**
     * 宠物的描述
     */
    private String description;

    /**
     * 血量
     */
    private Integer healthpoint;

    /**
     * 物理攻击力
     */
    private Integer physicaldamagepoint;

    /**
     * 法术攻击力
     */
    private Integer magicaldamagepoin;

    /**
     * 物理防御力
     */
    private Integer physicaldefence;

    /**
     * 法术防御力
     */
    private Integer magicaldefence;

    /**
     * 速度
     */
    private Integer speed;

    /**
     * 主属性（不能没有）
     */
    private Integer attribute;

    /**
     * 副属性（可以没有）
     */
    private Integer secondaryattribute;

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
     * 宠物的名字
     */
    public String getName() {
        return name;
    }

    /**
     * 宠物的名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 宠物的描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 宠物的描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 血量
     */
    public Integer getHealthpoint() {
        return healthpoint;
    }

    /**
     * 血量
     */
    public void setHealthpoint(Integer healthpoint) {
        this.healthpoint = healthpoint;
    }

    /**
     * 物理攻击力
     */
    public Integer getPhysicaldamagepoint() {
        return physicaldamagepoint;
    }

    /**
     * 物理攻击力
     */
    public void setPhysicaldamagepoint(Integer physicaldamagepoint) {
        this.physicaldamagepoint = physicaldamagepoint;
    }

    /**
     * 法术攻击力
     */
    public Integer getMagicaldamagepoin() {
        return magicaldamagepoin;
    }

    /**
     * 法术攻击力
     */
    public void setMagicaldamagepoin(Integer magicaldamagepoin) {
        this.magicaldamagepoin = magicaldamagepoin;
    }

    /**
     * 物理防御力
     */
    public Integer getPhysicaldefence() {
        return physicaldefence;
    }

    /**
     * 物理防御力
     */
    public void setPhysicaldefence(Integer physicaldefence) {
        this.physicaldefence = physicaldefence;
    }

    /**
     * 法术防御力
     */
    public Integer getMagicaldefence() {
        return magicaldefence;
    }

    /**
     * 法术防御力
     */
    public void setMagicaldefence(Integer magicaldefence) {
        this.magicaldefence = magicaldefence;
    }

    /**
     * 速度
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * 速度
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     * 主属性（不能没有）
     */
    public Integer getAttribute() {
        return attribute;
    }

    /**
     * 主属性（不能没有）
     */
    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    /**
     * 副属性（可以没有）
     */
    public Integer getSecondaryattribute() {
        return secondaryattribute;
    }

    /**
     * 副属性（可以没有）
     */
    public void setSecondaryattribute(Integer secondaryattribute) {
        this.secondaryattribute = secondaryattribute;
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
        Petsconfig other = (Petsconfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getHealthpoint() == null ? other.getHealthpoint() == null : this.getHealthpoint().equals(other.getHealthpoint()))
            && (this.getPhysicaldamagepoint() == null ? other.getPhysicaldamagepoint() == null : this.getPhysicaldamagepoint().equals(other.getPhysicaldamagepoint()))
            && (this.getMagicaldamagepoin() == null ? other.getMagicaldamagepoin() == null : this.getMagicaldamagepoin().equals(other.getMagicaldamagepoin()))
            && (this.getPhysicaldefence() == null ? other.getPhysicaldefence() == null : this.getPhysicaldefence().equals(other.getPhysicaldefence()))
            && (this.getMagicaldefence() == null ? other.getMagicaldefence() == null : this.getMagicaldefence().equals(other.getMagicaldefence()))
            && (this.getSpeed() == null ? other.getSpeed() == null : this.getSpeed().equals(other.getSpeed()))
            && (this.getAttribute() == null ? other.getAttribute() == null : this.getAttribute().equals(other.getAttribute()))
            && (this.getSecondaryattribute() == null ? other.getSecondaryattribute() == null : this.getSecondaryattribute().equals(other.getSecondaryattribute()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getHealthpoint() == null) ? 0 : getHealthpoint().hashCode());
        result = prime * result + ((getPhysicaldamagepoint() == null) ? 0 : getPhysicaldamagepoint().hashCode());
        result = prime * result + ((getMagicaldamagepoin() == null) ? 0 : getMagicaldamagepoin().hashCode());
        result = prime * result + ((getPhysicaldefence() == null) ? 0 : getPhysicaldefence().hashCode());
        result = prime * result + ((getMagicaldefence() == null) ? 0 : getMagicaldefence().hashCode());
        result = prime * result + ((getSpeed() == null) ? 0 : getSpeed().hashCode());
        result = prime * result + ((getAttribute() == null) ? 0 : getAttribute().hashCode());
        result = prime * result + ((getSecondaryattribute() == null) ? 0 : getSecondaryattribute().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", healthpoint=").append(healthpoint);
        sb.append(", physicaldamagepoint=").append(physicaldamagepoint);
        sb.append(", magicaldamagepoin=").append(magicaldamagepoin);
        sb.append(", physicaldefence=").append(physicaldefence);
        sb.append(", magicaldefence=").append(magicaldefence);
        sb.append(", speed=").append(speed);
        sb.append(", attribute=").append(attribute);
        sb.append(", secondaryattribute=").append(secondaryattribute);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}