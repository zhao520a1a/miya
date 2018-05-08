package com.miya.search.service;


import com.miya.search.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int limit, int currPage) throws Exception;
}
