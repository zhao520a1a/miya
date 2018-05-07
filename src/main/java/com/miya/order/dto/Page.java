package com.miya.order.dto;


import java.util.List;


public class Page<T> {
    /**
     * 查询的结果集
      */
    private List<T> rows;
    /**
     * 分页对象
     */
    private Pagination pagination;

    

	public Page() {
	}
	public Page(List<T> rows, Pagination pagination) {
		this.rows = rows;
		this.pagination = pagination;
	}

	/**
     * 获取查询结果集的总记录数
     * @return
     */
    public int getTotalCount(){
        return (pagination!=null)?pagination.getTotalCount():((rows!=null)?rows.size():0);
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotalPages(){
        return (pagination!=null)?pagination.getTotalPages():1;
    }

    /**
     * 获取查询的当前页数
     * @return
     */
    public int getCurrentPage(){
        return (pagination!=null)?pagination.getCurrentPage():1;
    }

    /**
     * 获取每页查询的size
     * @return
     */
    public int getPageSize(){
        return (pagination!=null)?pagination.getPageSize():((rows!=null)?rows.size():0);
    }

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
