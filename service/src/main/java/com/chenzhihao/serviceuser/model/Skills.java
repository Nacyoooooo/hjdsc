package com.chenzhihao.serviceuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName skills
 */
@TableName(value ="skills")
public class Skills implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能类型 1是威力技能 2是变化技能，回血
     */
    private Integer type;

    /**
     * 技能威力 威力技能为正值 变化技能为负值
     */
    private Integer power;

    /**
     * 技能的属性
     */
    private Integer attribute;

    /**
     * 技能文本描述
     */
    private String description;

    /**
     * 技能使用次数
     */
    private Integer usetimes;

    /**
     * 记录创建时间
     */
    private Date createtime;

    /**
     * 记录更新时间
     */
    private Date updatetime;

    /**
     * 威力技能的伤害类型 1是物理，2是魔法
     */
    private Integer powertype;

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
     * 技能名称
     */
    public String getName() {
        return name;
    }

    /**
     * 技能名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 技能类型 1是威力技能 2是变化技能，回血
     */
    public Integer getType() {
        return type;
    }

    /**
     * 技能类型 1是威力技能 2是变化技能，回血
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 技能威力 威力技能为正值 变化技能为负值
     */
    public Integer getPower() {
        return power;
    }

    /**
     * 技能威力 威力技能为正值 变化技能为负值
     */
    public void setPower(Integer power) {
        this.power = power;
    }

    /**
     * 技能的属性
     */
    public Integer getAttribute() {
        return attribute;
    }

    /**
     * 技能的属性
     */
    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    /**
     * 技能文本描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 技能文本描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 技能使用次数
     */
    public Integer getUsetimes() {
        return usetimes;
    }

    /**
     * 技能使用次数
     */
    public void setUsetimes(Integer usetimes) {
        this.usetimes = usetimes;
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

    /**
     * 威力技能的伤害类型 1是物理，2是魔法
     */
    public Integer getPowertype() {
        return powertype;
    }

    /**
     * 威力技能的伤害类型 1是物理，2是魔法
     */
    public void setPowertype(Integer powertype) {
        this.powertype = powertype;
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
        Skills other = (Skills) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPower() == null ? other.getPower() == null : this.getPower().equals(other.getPower()))
            && (this.getAttribute() == null ? other.getAttribute() == null : this.getAttribute().equals(other.getAttribute()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getUsetimes() == null ? other.getUsetimes() == null : this.getUsetimes().equals(other.getUsetimes()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getPowertype() == null ? other.getPowertype() == null : this.getPowertype().equals(other.getPowertype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPower() == null) ? 0 : getPower().hashCode());
        result = prime * result + ((getAttribute() == null) ? 0 : getAttribute().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getUsetimes() == null) ? 0 : getUsetimes().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getPowertype() == null) ? 0 : getPowertype().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", power=").append(power);
        sb.append(", attribute=").append(attribute);
        sb.append(", description=").append(description);
        sb.append(", usetimes=").append(usetimes);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", powertype=").append(powertype);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}