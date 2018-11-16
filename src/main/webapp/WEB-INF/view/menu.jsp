<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/5
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src='js/jquery-1.7.2.min.js'></script>
<script>
    $(function (){
        //通过 getjson异步加载json数据
        $.getJSON("json/class_1.js",function (json) {
            //dom对象如何转换为jquery对象  $()
            $(json).each(function (i,data){
                $(".nav_mini ul").append(' <li><a href="javascript:" onmouseover="get_class2('+data.id+')">'+data.flmch1+'</a></li>')
            })
        })
    })

    function get_class2(class1Id){
        var class1Id=class1Id;
        $(".two_nav").empty();
        $.getJSON("json/class_2_"+class1Id+".js", function(json){
            //dom对象如何转换为jquery对象  $()
            $(json).each(function(i,data){
                console.log(data);
                $(".two_nav").append('<a href="toListPage.do?flbh2='+data.id+'" target="_blank">'+data.flmch2+'</a>');
                $(".two_nav").show();
            })
        });
    }
</script>
<div class="menu">
    <div class="nav">
        <div class="navs">
            <div class="left_nav">
                全部商品分类
                <div class="nav_mini">
                    <ul>
                        <li>
                            <div class="two_nav">
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <ul>
                <li><a href="">服装城</a></li>
                <li><a href="">美妆馆</a></li>
                <li><a href="">超市</a></li>
                <li><a href="">全球购</a></li>
                <li><a href="">闪购</a></li>
                <li><a href="">团购</a></li>
                <li><a href="">拍卖</a></li>
                <li><a href="">金融</a></li>
                <li><a href="">智能</a></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
