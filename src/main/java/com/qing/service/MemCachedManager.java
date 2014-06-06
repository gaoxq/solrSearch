package com.qing.service;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by qing on 5/28/14.
 */
public class MemCachedManager {


    private MemcachedClient client;
    private List<String> list = new ArrayList<String>();

    public MemCachedManager(){

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("localhost:11211"));
        // 宕机报警
        builder.setFailureMode(true);
        /**
         * 设置连接池大小，即客户端个数
         * In a high concurrent enviroment,you may want to pool memcached clients.
         * But a xmemcached client has to start a reactor thread and some thread pools,
         * if you create too many clients,the cost is very large.
         * Xmemcached supports connection pool instreadof client pool.
         * you can create more connections to one or more memcached servers,
         * and these connections share the same reactor and thread pools,
         * it will reduce the cost of system.
         *  默认的pool size是1。设置这一数值不一定能提高性能，请依据你的项目的测试结果为准。初步的测试表明只有在大并发下才有提升。
         *  设置连接池的一个不良后果就是，同一个memcached的连接之间的数据更新并非同步的
         *  因此你的应用需要自己保证数据更新的原子性（采用CAS或者数据之间毫无关联）。
         */
        builder.setConnectionPoolSize(10);
         // 使用二进制文件
        builder.setCommandFactory(new BinaryCommandFactory());
        try {
            client = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value){

        try {
            /**
             * 第一个是存储的key名称，
             * 第二个是expire时间（单位秒），超过这个时间,memcached将这个数据替换出去，0表示永久存储（默认是一个月)
             * 第三个参数就是实际存储的数据
             */
            client.set(key, 0, value);
            /**
             * Memcached是通过cas协议实现原子更新，所谓原子更新就是compare and set，
             * 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值， memcached比对这个cas值与当前存储数据的cas值是否相等，
             * 如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用
             */
        /*
            GetsResponse<Integer> result = client.gets("a");
            long cas = result.getCas();
            // 尝试将a的值更新为2
            if (!client.cas("a", 0, 2, cas)) {
                System.err.println("cas error");
            }
            */
        } catch (MemcachedException e) {
            System.err.println("MemcachedClient operation fail");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.err.println("MemcachedClient operation timeout");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // ignore
        }

    }
    public String get(String key) {
        if (key == null) {
            return null;
        }

        if (client == null) {
            return null;
        }
        try {
            return client.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public void shutdown() {
        if (client != null) {
            try {
                client.shutdown();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) throws IOException {
        MemCachedManager a = new MemCachedManager();
        a.set("hello", "/Users/qing/IdeaProjects/sourcefile/solr.xml");
        System.out.println(a.get("hello"));
    }

}
