package com.kfwl.common.easyui;

public class TBody {
	private String field;
	private String title;
	private int width = 150;
	private String align;

	public TBody() {
		super();
	}

	public TBody(String field, String title, int width, String align) {
		super();
		this.field = field;
		this.title = title;
		this.width = width;
		this.align = align;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	@Override
	public String toString() {
		return "TBody [field=" + field + ", title=" + title + ", width=" + width + ", align=" + align + "]";
	}

}