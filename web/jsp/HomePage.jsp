<%-- 
    Document   : HomePage
    Created on : Mar 7, 2023, 9:55:40 AM
    Author     : Asus
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
<%@page import="model.User"%>
<%@page import="java.util.HashMap"%>
<%@page import="dbObject.News"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="NewsController" method="post">
            <input type="text" name="title" placeholder="Search...">
            <%
                HashMap<Integer,Category> listCategory = (HashMap<Integer,Category>) request.getAttribute("listCategory");
                if (listCategory == null || listCategory.isEmpty()) {
                    out.println("Empty category list <br/><br/>");
                } else {
            %>
            <select name="category_id">           
                <%
                    
                    for (Integer key : listCategory.keySet())   {
                %>    
                <option value="<%=listCategory.get(key).getId() %>"><%=listCategory.get(key).getName()%></option>
                <%
                        }
                    }
                %>
            </select>
            <input type='submit' name='action' value='search'>
        </form>
        <%
            ArrayList<News> listNews = (ArrayList<News>) request.getAttribute("listNews");
            HashMap<Integer,User> listUser = (HashMap<Integer,User>) request.getAttribute("listUser");
            String location = (String) request.getAttribute("location");
            if (listNews == null || listNews.isEmpty()) {
                out.println("Empty list <br/><br/>");
            } else if (listUser == null || listUser.isEmpty()) {
                out.println("Empty list user <br/><br/>");
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
            <%
                for (News news : listNews) {
            %>
            <tr>

                <td>
                    <a href='GetNews?news_id=<%=news.getNews_id()%>'>
                        <image src="<%= location + news.getImage()%>" width="500" height="600">
                    </a>
                </td>
                <td>
                    <a href='Search?cat_id=<%=news.getCat_id()%>'>
                        <%=listCategory.get(news.getCat_id()).getName() %>
                    </a>
                </td>
                <td>
                    <a href='GetNews?news_id=<%=news.getNews_id()%>'>
                        <%=news.getTitle()%>
                    </a>
                </td>
                <td><%=news.getSubtitle()%></td>
                <td>
                    <a href='#'>
                        <%= listUser.get(news.getUser_id()).getName() %>
                    </a>
                </td>
            </tr>
            <%
                    }
                }
            %>
    </body>
</html>
