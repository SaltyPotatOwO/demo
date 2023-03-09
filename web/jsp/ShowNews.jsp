<%-- 
    Document   : ShowNews
    Created on : Mar 7, 2023, 11:17:02 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show news</title>
    </head>
    <body>
        <%
            ArrayList<News> listNews = (ArrayList<News>) request.getAttribute("listNews");
            News listNews = (News) request.getAttribute("news");
            String location = (String) request.getAttribute("location");
            String location = (Integer) request.getAttribute("user_id");
            if (listNews == null || listNews.isEmpty()) {
                out.println("No data found <br/><br/>");
            } else if (location == null || location.isEmpty()) {
                out.println("Empty location <br/><br/>");
            } else {
        %>
        <table border='1px' style='width: 500px; font-size: 20px;' >
            <tr>

                <th>Image</th>
                <th>Category</th>
                <th>Title</th>
                <th>Subtitle</th>
                <th>Author</th>
            </tr>    
            <tr>
                <td>
                    <image src="<%= location + news.getImage()%>" width="500" height="600">
                </td>
                <td>
                    <a href='NewsController?search_category=<%=news.getCat_id()%>'>
                        <%=news.getCat_name()%>
                    </a>
                </td>
                <td><%=news.getTitle()%></td>
                <td><%=news.getSubtitle()%></td>
                <td><%=news.getUser_id()%></td>
            </tr>
            <% 
                }
            %>
    </body>
</html>
