package com.qing.index;

import org.apache.commons.configuration.beanutils.BeanFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Attribute;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qing on 5/12/14.
 */

public class AddIndex {

    SolrInputDocument document = new SolrInputDocument();
    private HttpSolrServer solr;
    private String fileName = "/Users/qing/IdeaProjects/sourcefile/me1.xml";

    public AddIndex(HttpSolrServer solr) {
        this.solr = solr;
    }
    public void buildIndex() {
        SAXReader reader = new SAXReader();
        Document document_temp = null;
        try {
            document_temp = reader.read(new File(fileName));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = document_temp.getRootElement();
        listNodes(root);

        try {
            UpdateResponse response = new UpdateResponse();
            solr.add(document);
            solr.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }


    public void listNodes(Element node) {
        System.out.println("当前节点的名称：：" + node.getName());
        // 获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list) {

            System.out.println(attr.getText() + "-----" + attr.getName()
                    + "---" + attr.getValue());
            if (!(node.getTextTrim().equals(""))) {
                document.addField(attr.getText(), node.getText());
                System.out.println("文本内容：：：：" + node.getText());
            }
        }
        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e);
        }
    }

    private void indexFilesSolrCell(String fileName, String solrId) {

        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        try {
            up.addFile(new File(fileName),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        up.setParam("literal.id", solrId);
        up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
        try {
            solr.request(up);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args)
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AddIndex bean1 = (AddIndex)appContext.getBean("AddIndex");
        bean1.buildIndex();
        //System.out.print();
       // AddIndex a = new AddIndex(bean1.toString());
       // a.buildIndex();

    }



}
