package com.qing.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Created by qing on 5/12/14.
 */
public class Search {

    public static String urlString = "http://localhost:8983/solr";
    public static SolrServer server = new HttpSolrServer(urlString);
    public static void searchQuery()
    {

        SolrQuery query = new SolrQuery();
        query.setQuery("wangyuzhou");
        query.setFields("id","name");
        QueryResponse response = null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        SolrDocumentList results = response.getResults();
        System.out.println(results.size());
        for(int i = 0; i < results.size(); i++)
            System.out.println(results.get(i));

    }
    public static void queryExample() throws SolrServerException{

        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "id:第一篇");
        params.set("fl", "id, name, manu, features");
        params.set("start", 0);
        params.set("rows", 10);
        QueryResponse response = server.query(params);
        for(int i = 0; i < response.getResults().size(); i++) {
            SolrDocument solrDocument = response.getResults().get(i);
            System.out.println(solrDocument + "\n\n\n");
        }
    }
    public static void main(String[] args)
    {
        //searchQuery();
        try {
            queryExample();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

    }
}
