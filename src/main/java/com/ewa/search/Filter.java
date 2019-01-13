package com.ewa.search;

public class Filter {
	private String order;
	private String direction;
	private int page;
	private int itemsperpage;
	
	public Filter() {
	}
	
	public Filter(String order, String direction, int page, int itemsperpage) {
		this.order = order;
		this.page = page;
		this.direction = direction;
		this.itemsperpage = itemsperpage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getItemsperpage() {
		return itemsperpage;
	}

	public void setItemsperpage(int itemsperpage) {
		this.itemsperpage = itemsperpage;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
