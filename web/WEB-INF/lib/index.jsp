<%--
  Created by IntelliJ IDEA.
  User: qing
  Date: 5/23/14
  Time: 9:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<h2>Search</h2>

    <input id="text" type="text" name="name" />
    <button id="b01" type="button">搜索</button>

    <div id="div1"></div>

    <script type="text/javascript">

        $(document).ready(function(){
            $("#b01").click(function(){
                var query = $("#text").val();

                $.ajax({
                    type: "POST",
                    url: "query",
                    data: { xq: query}, //发送至服务端的数据
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "json", //要求服务端返回json
                    success: function (data) {
                        <!--alert("success");-->
                        $("#div1").append(data.type + "<br/>");
                    },
                    error: function(request, status, error){
                        alert("error");
                    }
                });

             });

        });

    </script>
</body>
</html>
