// package com.simple.generator.base;
//
// import java.io.Serializable;
// import java.util.Date;
//
// import javax.persistence.Transient;
//
// import org.apache.commons.lang3.builder.EqualsBuilder;
// import org.apache.commons.lang3.builder.HashCodeBuilder;
// import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
// import org.apache.commons.lang3.builder.ToStringStyle;
//
// import com.alibaba.fastjson.annotation.JSONField;
//
// /**
//  * 功能说明: <br>
//  * 系统版本: v1.0<br>
//  * 开发人员: @author HuJianfeng<br
//  */
// @SuppressWarnings("serial")
// public class BaseBean implements Serializable {
//
//     @Transient
//     @JSONField(serialize = false)
//     private Date startDate;
//
//     @Transient
//     @JSONField(serialize = false)
//     private Date endDate;
//
//     /** 分页大小 */
//     @Transient
//     @JSONField(serialize = false)
//     private Integer pageSize;
//
//     /** 页码 */
//     @Transient
//     @JSONField(serialize = false)
//     private Integer pageNum;
//
//     /** 排序 */
//     @Transient
//     @JSONField(serialize = false)
//     private String orderBy;
//
//
//     @Override
// 	public String toString() {
// 		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
// 		return ReflectionToStringBuilder.toStringExclude(this, "password");
// 	}
//
// 	@Override
// 	public int hashCode() {
// 		return HashCodeBuilder.reflectionHashCode(this);
// 	}
//
// 	public boolean equals(Object o) {
// 		return EqualsBuilder.reflectionEquals(this, o);
// 	}
// }
