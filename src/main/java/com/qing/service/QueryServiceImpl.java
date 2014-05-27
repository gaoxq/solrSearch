package com.qing.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qing on 5/18/14.
 */
public class QueryServiceImpl {
    public static String urlString = "http://localhost:8983/solr";
    public static SolrServer server = new HttpSolrServer(urlString);


    public static List<String> searchQuery(String xSearch) {
        List<String> resultList = new ArrayList<String>();
        SolrQuery query = new SolrQuery();
        query.setQuery(xSearch);
        query.setFields("id","name");
      //  System.out.println("heiheihei");
        QueryResponse response = null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        SolrDocumentList results = response.getResults();
        //System.out.println(results.size());
        for(int i = 0; i < results.size(); i++) {
           // for(int j = 0; j < results.get(i).size(); j++)
                resultList.add(i, results.get(i).toString());
        }
        return resultList;


    }
}
