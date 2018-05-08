package com.miya.search.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult implements Serializable{

	private long totalPages;
	private long recordCount;
	private List<SearchItem> itemList;

}
