package com.qing.servlet;

import com.qing.service.MemCachedManager;
import com.qing.service.QueryService;

import com.qing.service.QueryServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

/**
 * Created by qing on 5/18/14.
 */
public class QueryServlet extends HttpServlet{

    private QueryService queryService;
//    private MemCachedManager memCache = new MemCachedManager();

    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        queryService = applicationContext.getBean(QueryService.class);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        //String query = req.getParameter("data");
        //String query = req.getParameter("xq");

        resp.getWriter().write("x");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String query = req.getParameter("xq");
        JSONObject json = new JSONObject();
        System.out.println("query"+query);
//        if(memCache.get(query) != null) {
//            json.put("type", memCache.get(query));
//        }
//        else
       {
            List<String> results = queryService.searchQuery(query);
            System.out.println("results" + results);

            for (int i = 0; i < results.size(); i++) {
                json.put("type", results.get(i));

            }
        }
        resp.getWriter().write(json.toString());

    }
/*
    public void test() {
        if(memCache.get("solr") != null) {
            System.out.println(memCache.get("hello"));
        }
        else {
            List<String> results = queryService.searchQuery("Test with some GB18030 encoded characters");
            System.out.println(results);
            JSONObject json = new JSONObject();
            for (int i = 0; i < results.size(); i++) {
                json.put("type", results.get(i));
                System.out.println(results.get(i));
            }
        }

    }

    public static void main(String[] args) {
        QueryServlet a = new QueryServlet();
        a.test();
    }
*/
}
