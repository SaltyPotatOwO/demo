<%-- 
Document   : main
Created on : Mar 1, 2023, 9:27:27 PM
Author     : Asus
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dbObject.News"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <h1>List news.</h1>
        <%
            ArrayList<News> listNews = (ArrayList<News>) request.getAttribute("listNews");
            if (listNews == null || listNews.isEmpty()) {
                out.println("Empty list <br/><br/>");
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
            <%
                for (News news : listNews) {
            %>
            <tr>

                <td> 
                    <a href='NewsController?news_id=<%=news.getNews_id()%>&action=get'>
                        <image src="images/<%= news.getImage()%>" width="500" height="600"/>
                    </a>
                </td>
                <td>
                    <a href='NewsController?cat_id=<%=news.getCat_id()%>&action=search'>
                        <%=news.getCat_id()%>
                    </a>
                </td>
                <td>
                    <a href='NewsController?news_id=<%=news.getNews_id()%>&action=get'>
                        <%=news.getTitle()%>
                    </a>
                </td>
                <td><%=news.getSubtitle()%></td>

                <td><%=news.getUser_id()%></td>
            </tr>
            <%
                    }
                }
            %>
            <a href ="InsertCategory"><button > Add Category </button></a>


    </body>
</html>
