<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/5
  Time: 14:35
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
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src='js/jquery-1.7.2.min.js'></script>
<script>
    function findMiniCart(){
        $.post("findMiniCart.do",function(data){
            $("#miniCartInnerDiv").html(data);
        })
        $("#miniCartInnerDiv").show();
    }
    function outMiniCart(){
        $("#miniCartInnerDiv").hide();
    }
    function deleteCartById(){
        $.post("deleteCartById.do",{"skuId":skuId},function(data){
            $("#cartListInnerDiv").html(data);
        })
    }
</script>
<body>
<div class="search">
    <div class="logo"><a href="/toMainPage.do"><img src="./images/logo.jpg" alt=""></a></div>
    <div class="search_on">
        <div class="se">
            <form action="solrByskuMch.do" method="post">
                <input type="text" name="key" value="${key}" class="lf">
                <input type="submit" class="clik" value="搜索">
            </form>
        </div>
        <div class="se">
            <a href="">取暖神奇</a>
            <a href="">1元秒杀</a>
            <a href="">吹风机</a>
            <a href="">玉兰油</a>
        </div>
    </div>

    <div class="card" onmouseover="findMiniCart()" onmouseout="outMiniCart()">
        <a href="/toCartListPage.do">购物车<div class="num">0</div></a>
        <!--购物车商品-->
        <div class="cart_pro">
            <div id="miniCartInnerDiv" style="display:none;"></div>
        </div>
    </div>
</div>
</body>
</html>
