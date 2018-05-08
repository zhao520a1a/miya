package com.miya.search.service;


import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SearchItemService {

    void importItemsToIndex() throws IOException, SolrServerException;

    void importItemToIndexByItemId(Long itemId) throws IOException, SolrServerException;

    void deleteItemsIndex(Long itemId) throws IOException, SolrServerException;
    void deleteItemsIndex() throws IOException, SolrServerException;
}
