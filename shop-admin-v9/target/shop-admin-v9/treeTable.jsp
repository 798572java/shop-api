<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/21 0021
  Time: 上午 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/js/treetable/css/jquery.treetable.css" rel="stylesheet">
    <link href="/js/treetable/css/jquery.treetable.theme.default.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
    <table id="x">
        <tr>
            <th>地区名</th>
            <th>邮编</th>
        </tr>
        <tr data-tt-id="0">
            <td>全国</td>
            <td>100</td>
        </tr>
        <tr data-tt-id="1" data-tt-parent-id="0">
            <td>河南</td>
            <td>111</td>
        </tr>
        <tr data-tt-id="100" data-tt-parent-id="1">
            <td>河南1</td>
            <td>112</td>
        </tr>

        <tr data-tt-id="2" data-tt-parent-id="0">
            <td>河北</td>
            <td>211</td>
        </tr>



        <tr data-tt-id="101" data-tt-parent-id="2">
            <td>河南11</td>
            <td>113</td>
        </tr>

        <tr data-tt-id="1000" data-tt-parent-id="101">
            <td>河南111</td>
            <td>113</td>
        </tr>


        <tr data-tt-id="200" data-tt-parent-id="2">
            <td>河北1</td>
            <td>212</td>
        </tr>
        <tr data-tt-id="201" data-tt-parent-id="200">
            <td>河北11</td>
            <td>2121</td>
        </tr>
        <tr data-tt-id="202" data-tt-parent-id="2">
            <td>河北2</td>
            <td>213</td>
        </tr>
        <tr data-tt-id="203" data-tt-parent-id="202">
            <td>河北21</td>
            <td>2131</td>
        </tr>
    </table>

    <script src="/js/jquery.min.js"></script>
    <script src="/js/treetable/jquery.treetable.js"></script>
    <script>
         $("#x").treetable({expandable: true})
    </script>
</body>
</html>
