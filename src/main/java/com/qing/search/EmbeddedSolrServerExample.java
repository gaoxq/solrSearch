package com.qing.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.core.CoreContainer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by qing on 5/17/14.
 */
public class EmbeddedSolrServerExample {
    public static void main(String[] args) throws FileNotFoundException,
            SolrServerException {


        CoreContainer container = new CoreContainer("/Users/qing/Project/solr-4.7.2/example/solr");

       // CoreContainer container = new CoreContainer("/tmp/solr", new File("/tmp/solr/solr.xml"));
        System.out.println("default core name: "
                + container.getDefaultCoreName());

        System.out.println(container.getAdminPath());
        System.out.println(container.getAllCoreNames());
       // System.out.println(container.getConfigFile());
        System.out.println(container.getContainerProperties());
//        System.out.println(container.getCore("oscar").toString());
        System.out.println(container.getCoreNames());
        System.out.println(container.getCores());
        System.out.println(container.getDefaultCoreName());
        System.out.println(container.getSolrHome());

        EmbeddedSolrServer server = new EmbeddedSolrServer(container, "");

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:*");
        solrQuery.setStart(1);
        solrQuery.setRows(10);
        solrQuery.setFields("title");
        QueryResponse response = server.query(solrQuery);
        System.out.println(response);

        container.shutdown();
    }


}
