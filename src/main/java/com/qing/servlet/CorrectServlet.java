package com.qing.servlet;

import com.qing.service.QueryServiceImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

/**
 * Created by qing on 5/18/14.
 */
public class CorrectServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        //String query = req.getParameter("data");
        String query = req.getParameter("xq");

        resp.getWriter().write(query);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //req.setCharacterEncoding("utf-8");
        //resp.setContentType("text/html;charset=utf-8");
        String query = req.getParameter("xq");

        System.out.println(query);
        List<String> results = QueryServiceImpl.searchQuery(query);
        System.out.println(results);
        JSONObject json = new JSONObject();
        for(int i = 0; i < results.size(); i++) {
            json.put("type", results.get(i));

        }

        resp.getWriter().write(json.toString());

    }
    public static void main(String[] args) {

        List<String> results = QueryServiceImpl.searchQuery("第一篇");
        System.out.println(results);
        JSONObject json = new JSONObject();
        for(int i = 0; i < results.size(); i++) {
            json.put("type", results.get(i));
            System.out.println(results.get(i));
        }
    }


}
