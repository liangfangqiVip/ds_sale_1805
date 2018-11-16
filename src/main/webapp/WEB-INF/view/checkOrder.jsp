<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/12
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单支付</title>
    <link rel="stylesheet" type="text/css" href="css/css.css"/>
    <script src="/js/jquery-1.7.2.min.js"></script>
    <script >
        function address(id,shjr,dzMch,lxfsh){
            var addr = "寄送至 ： "+dzMch+"  &nbsp;"+shjr+"（收） &nbsp;"+lxfsh;
            $("#addressIdInp").val(id);
            $("#dzMchInp").val(dzMch);
            $("#shjrInp").val(shjr);
            $(".msg_sub_adds").html(addr);
        }
        function saveOrder(){
            $("#saveOrderForm").submit();
        }
    </script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>

<jsp:include page="topImg.jsp"></jsp:include>

<form id="saveOrderForm" action="saveOrder.do" method="post">
    <input type="hidden" name="id" id="addressIdInp"/>
    <input type="hidden" name="dzMch" id="dzMchInp"/>
    <input type="hidden" name="shjr" id="shjrInp"/>
</form>

<div class="message">
    <div class="msg_title">
        收货人信息
    </div>
    <c:forEach items="${addressList}" var="address" >
        <div class="msg_addr">
            <input type="radio" name="address" onclick="address(${address.id},'${address.shjr}','${address.dzMch}',${address.lxfsh})" checked>
            <span class="msg_left">
                姓名:${address.shjr}
            </span>
            <span class="msg_right">
					地址：${address.dzMch}
            </span>
        </div>
    </c:forEach>
    <span class="addrs">查看更多地址信息</span>
    <div class="msg_line"></div>
    <div class="msg_title">
        送货清单
    </div>
    <c:forEach items="${checkOrderList}" var="orderList" >
        <div class="msg_list">
            <div class="msg_list_left">
                配送方式
                <div class="left_title">
                    京东快递
                </div>
            </div>
            <div class="msg_list_right">
                <div class="msg_img">
                    <img src="images/msgImg.png"/>
                </div>
                <div class="msg_name">
                    ${orderList.skuMch}
                </div>
                <div class="msg_price">
                    ￥${orderList.skuJg}
                </div>
                <div class="msg_mon">
                    *${orderList.tjshl}
                </div>
                <div class="msg_state">
                    有货
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="msg_line"></div>

    <div class="msg_sub">
        <div class="msg_sub_tit">
            应付金额：
            <b>￥${sum}</b>
        </div>
        <div class="msg_sub_adds">
            寄送至 ： 北京市 昌平区     &nbsp;某某某（收）  185****1222
        </div>
        <button class="msg_btn" onclick="saveOrder()">提交订单</button>
    </div>

</div>

</body>
</html>

