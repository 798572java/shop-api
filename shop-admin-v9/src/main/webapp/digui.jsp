<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/15
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script src="/js/jquery.min.js"></script>
</body>
<script>
$(function () {
   // let x1 = x(13);
   // alert(x1);
    let number = cj(8);
    alert(number);
})

    function cj(id) {
        if(id==1){
            return 1;
        }else{
           return cj(id-1)*id;
        }
    }
        


    function x(id) {
        if(id==0){
            return 0;
        }
        if(id==1){
            return 1;
        }
        return x(id-1)+x(id-2);
    }
    
    

    




</script>
</html>
