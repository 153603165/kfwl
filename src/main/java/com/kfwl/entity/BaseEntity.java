package com.kfwl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 统一定义entity基类. <br>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. <br>
 */
@MappedSuperclass
@NoArgsConstructor
@Setter
@Getter
public class BaseEntity implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMEZONE = "GMT+08:00";
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/**
	 * 操作版本(乐观锁,用于并发控制)
	 */
	@Version
	protected Integer version;

	/**
	 * 记录创建者用户登录名
	 */
	protected String createUser;
	/**
	 * 记录创建时间
	 * 
	 * 记录更新时间 设定JSON序列化时的日期格式
	 */
	@JsonFormat(pattern = DATE_TIME_FORMAT, timezone = TIMEZONE)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createTime;

	/**
	 * 记录更新用户 用户登录名
	 */
	protected String updateUser;
	/**
	 * 记录更新时间
	 * 
	 * 记录更新时间 设定JSON序列化时的日期格式
	 */
	@JsonFormat(pattern = DATE_TIME_FORMAT, timezone = TIMEZONE)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateTime;
	/**
	 * 状态可用 0：可用 1：已删除
	 */
	@Column(name = "is_delete")
	protected Boolean delete = false;

	public BaseEntity clone() {
		BaseEntity o = null;
		try {
			o = (BaseEntity) super.clone();// Object中的clone()识别出你要复制的是哪一个对象。
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
