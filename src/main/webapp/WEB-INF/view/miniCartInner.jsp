<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/7
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src='js/jquery-1.7.2.min.js'></script>
<script>
    function toItemPage(skuId,spuId) {
        location.href="toItemPage.do?skuId="+skuId+"&spuId="+spuId;
    }

</script>
<head>
    <title>Title</title>
</head>
<body>
    <%--<h6>最新加入的商品</h6>--%>
    <c:forEach items="${cartList}" var="cart">
        <div class="one">
            <img src="images/lec1.png"/>
            <span class="one_name" onclick="toItemPage(${cart.skuId},${cart.shpId})">${cart.skuMch}
            </span>
            <span class="one_prece">
                ￥${cart.skuJg}×${cart.tjshl}<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="deleteCartById(${cart.skuId})">删除</button>
            </span>
        </div>
    </c:forEach>
    <div class="gobottom">
        共<span>${countNum}</span>件商品&nbsp;&nbsp;&nbsp;&nbsp;
        共计￥<span>${hjSum}</span>
        <button class="goprice">去购物车</button>
    </div>
</body>
</html>
