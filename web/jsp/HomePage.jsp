<%-- 
    Document   : HomePage
    Created on : Mar 7, 2023, 9:55:40 AM
    Author     : Asus
--%>

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
                ArrayList<Category> listCategory = (ArrayList<Category>) request.getAttribute("listCategory");
                if (listCategory == null || listCategory.isEmpty()) {
                    out.println("Empty category list <br/><br/>");
                } else {
            %>
            <select name="category_id">           
                <%
                    for (Category cat : listCategory)   {
                %>    
                <option value="<%=cat.getCat_id()%>"><%=cat.getCat_name()%></option>
                <%
                        }
                    }
                %>
            </select>
            <input type='submit' name='action' value='search'>
        </form>
        <%
            ArrayList<News> listNews = (ArrayList<News>) request.getAttribute("listNews");
            String location = (String) request.getAttribute("location");
            String location = (Integer) request.getAttribute("user_id");
            if (listNews == null || listNews.isEmpty()) {
                out.println("Empty list <br/><br/>");
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
                    <a href='NewsController?news_id=<%=news.getNews_id()%>&action=get'>
                        <image src="<%= location + news.getImage()%>" width="500" height="600">
                    </a>
                </td>
                <td>
                    <a href='NewsController?cat_id=<%=news.getCat_id()%>&action=search'>
                        <%=news.getCat_name()%>
                    </a>
                </td>
                <td>
                    <a href='NewsController?news_id=<%=news.getNews_id()%>&action=get'>
                        <%=news.getTitle()%>
                    </a>
                </td>
                <td><%=news.getSubtitle()%></td>
                <td>
                    <a href='NewsController?news_id=<%=news.getNews_id()%>&action=get'>
                        <%=news.getTitle()%>
                    </a>
                </td>
                <td><%=news.getUser_id()%></td>
            </tr>
            <%
                    }
                }
            %>
    </body>
</html>
