<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/5
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script src="js/jquery-1.7.2.min.js"></script>
<script>
    $(function (){
        var yhMch = getCookieByKey("yhMch")
        $("#yhMchSpan").html(yhMch);
    })
    function getCookieByKey(key){
        var val = "";
        //获取cookie
        var ck = document.cookie;
        //空格替换
        ck = ck.replace(" ","");
        //分割
        var ckArr = ck.split(";");//yhMch=zs;xxx=yyy;
        for (var i = 0; i<ckArr.length; i++){
            console.log(ckArr[i]);//yhMch=zs
            var arr = ckArr[i].split("=");//yhMch =[0] zs = [1]
            if(arr[0] == key){
                val = arr[1];
            }
        }
        return val;
    }
</script>
<div class="top">

    <div class="top_text">
        <c:if test="${empty user}">
            <a href="/toLoginPage.do">之前登录用户：<span id="yhMchSpan"></span></a>
            <a href="/toLoginPage.do">用户登录</a>
            <a href="/toRegPage.do">用户注册</a>
        </c:if>
        <c:if test="${!empty user}">
            <a href="toLoginPage.do">当前用户":${user.yhMch}</a>
            <a href="logout.do">注销</a>
        </c:if>

    </div>
</div>
</body>
</html>
