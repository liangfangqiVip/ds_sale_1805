<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/11/6
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src='js/jquery-1.7.2.min.js'></script>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>检索</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
    <script>
        function listSkuByAttr(attrId,valId){
            var val="{\"attrId\":\""+attrId+"\",\"valId\":\""+valId+"\"}";
            var str='<input type="text" name="attr_val_id" value='+val+'>';
            $("#save_params_div").append(str);
            //设置返回的参数
            var flbh2 = ${flbh2};
            var param = "flbh2="+flbh2;
            //通过name选择器获取指定的数据
            $("[name='attr_val_id']").each(function(i,json){
                var obj = jQuery.parseJSON($(json).val());
                //字符串分割
                //如何改为json对象
                param = param + "&attrValueList["+i+"].shxmId="+obj.attrId+"&attrValueList["+i+"].shxzhId="+obj.valId;
            })
            //获取到class2、属性
            //通过ajax查询
            //data：需要一个内嵌页面
            $.post("listSkuByAttr.do",param,function(data){
                $("#listSboxInner").html(data);
            })
        }
    </script>
</head>
<body>

    <jsp:include page="top.jsp"></jsp:include>

    <jsp:include page="topImg.jsp"></jsp:include>

    <jsp:include page="search.jsp"></jsp:include>

    <%--<jsp:include page="menu.jsp"></jsp:include>--%>

    <jsp:include page="list-filter.jsp"></jsp:include>

    <jsp:include page="list-Sscreen.jsp"></jsp:include>

    <div id="listSboxInner">
        <jsp:include page="list-Sbox.jsp"></jsp:include>
    </div>

    <jsp:include page="list-footer.jsp"></jsp:include>

</body>
</html>
