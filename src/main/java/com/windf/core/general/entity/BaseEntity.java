package com.windf.core.general.entity;

import java.sql.Date;

public class BaseEntity extends NameBean  {

	private static final long serialVersionUID = -6391205196821087217L;

	private Date createDate;
	private Date updateDate;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
