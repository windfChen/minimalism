package com.windf.core.general.entity;

/**
 * 具有name和id的bean
 * @author windf
 *
 */
public class NameBean extends AbstractBean{
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
