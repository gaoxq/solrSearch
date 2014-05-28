package com.qing.index;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by qing on 5/24/14.
 */
public class DeleteIndex {
    private HttpSolrServer solr;
    public DeleteIndex(HttpSolrServer solr) {
        this.solr = solr;
    }

    public void deleteIndex() {
        try {
            solr.deleteById("第一篇");
            //solr.deleteByQuery("*:*");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        DeleteIndex bean1 = (DeleteIndex)appContext.getBean("DeleteIndex");
        bean1.deleteIndex();
       // DeleteIndex a = new DeleteIndex();
       // a.deleteIndex();

    }
}
