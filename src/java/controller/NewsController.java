/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NewsDAO;
import dbObject.News;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class NewsController extends HttpServlet {

    String location = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getListNews(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null || action.length() == 0) {
                action = "list";
            }
            switch (action) {
                case "list":
                    getListNews(request, response);
                    break;
                case "search":
                    searchNews(request, response);
                    break;
                case "get":
                    searchNews(request, response);
                    break;
                case "insert":
                    insertNews(request, response);
                    break;
                case "update":
                    updateNews(request, response);
                    break;
                case "delete":
                    deleteNews(request, response);
                    break;
                default:
                    getListNews(request, response);
                    break;
            }
        } catch (Exception e) {

        }

    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("index.html");
    }

    private void getListNews(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<News> listNews = new ArrayList<>();
        try {
            NewsDAO newsDAO = new NewsDAO();
//            int amount = Integer.parseInt(request.getParameter("10"));//Number of news shown
            listNews = newsDAO.getListNews(10);
            if (location == null) {//check if global variable location contain location of project file
                location = getLocation();
            }
            request.setAttribute("location", location);
            request.setAttribute("listNews", listNews);
            request.getRequestDispatcher("main.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR GETTING LIST NEWS");
        }
    }

    private void getNews(HttpServletRequest request, HttpServletResponse response) {
        try {
            News news;
            NewsDAO newsDAO = new NewsDAO();
            int news_id = Integer.parseInt(request.getParameter("news_id"));
            news = newsDAO.getNews(news_id);
            if (location == null) {//check if global variable location contain location of project file
                location = getLocation();
            }
            request.setAttribute("news", news);
            getListNews(request, response);
        } catch (Exception e) {
            System.out.println("ERROR GETTING NEWS");
        }
    }

    private void updateNews(HttpServletRequest request, HttpServletResponse response) {
        try {
            NewsDAO dao = new    NewsDAO();

            int user_id = Integer.parseInt(request.getParameter("user_id"));
            int news_id = Integer.parseInt(request.getParameter("news_id"));
            int cat_id = Integer.parseInt(request.getParameter("cat_id"));
            String title = request.getParameter("title");
            String subtitle = request.getParameter("subtitle");
            String content = request.getParameter("content");
            String image = request.getParameter("image");

            image = moveImages(dao, image);//get new location of image

            News news = new News(news_id, user_id, cat_id, title, subtitle, content, image);
            dao.updateNews(news);//update news into database
            getListNews(request, response);
        } catch (Exception e) {
            System.out.println("ERROR UPDATING NEWS");
        }
    }

    private void deleteNews(HttpServletRequest request, HttpServletResponse response) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            String rawNewsId = request.getParameter("news_id");
            newsDAO.deleteNews(Integer.parseInt(rawNewsId));//delete by id
            getListNews(request, response);
        } catch (Exception e) {
            System.out.println("error deleting news");
        }
    }

    private void searchNews(HttpServletRequest request, HttpServletResponse response) {
        try {
            String title = request.getParameter("search_title");
            String rawCategoryId = request.getParameter("search_category");

            if (title == null || title.trim().length() == 0) {//if user entered no title in search bar
                if (rawCategoryId == null) {
                    return;    //if user did NOT chose a category choice  
                } else {//if category id is given
                    searchByCategory(request, response, Integer.parseInt(rawCategoryId));//find by category id
                }
            } else {//if user DID entered a title in search bar
                if (rawCategoryId == null) {//if user did NOT chose a category choice 
                    searchByTitle(request, response, title);//find by title
                } else {//if user did choose a category and entered in a title
                    searchByTitleAndCateogory(request, response, title, Integer.parseInt(rawCategoryId));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS");
        }
    }

    private void searchByTitle(HttpServletRequest request, HttpServletResponse response, String title) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            ArrayList<News> listNews = newsDAO.searchTitle(title);
            request.setAttribute("listNews", listNews);
            request.getRequestDispatcher("listNews.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY TITLE");
        }
    }

    private void searchByCategory(HttpServletRequest request, HttpServletResponse response, int cat_id) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            ArrayList<News> listNews = newsDAO.searchCategory(cat_id);
            request.setAttribute("listNews", listNews);
            request.getRequestDispatcher("listNews.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY CATEGORY");
        }
    }

    private void searchByTitleAndCateogory(HttpServletRequest request, HttpServletResponse response, String title, int cat_id) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            ArrayList<News> listNews = newsDAO.searchTitleCat(title, cat_id);
            request.setAttribute("listNews", listNews);
            request.getRequestDispatcher("listNews.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY TITLE AND CATEGORY");
        }
    }

    private void insertNews(HttpServletRequest request, HttpServletResponse response) {
        try {
            NewsDAO dao = new NewsDAO();
            int user_id = Integer.parseInt(request.getParameter("user_id"));
            int cat_id = Integer.parseInt(request.getParameter("cat_id"));
            String title = request.getParameter("title");
            String subtitle = request.getParameter("subtitle");
            String content = request.getParameter("content");
            String image = request.getParameter("image");
            System.out.println(image);
            image = moveImages(dao, image);//get new location of image

            News news = new News(user_id, cat_id, title, subtitle, content, image);

            dao.insertNews(news);//insert news into database

            sendRedirect(request, response);

        } catch (Exception e) {
            System.out.println("ERROR INSERTING NEWS");
        }
    }

    private String moveImages(NewsDAO dao, String image) throws UnsupportedEncodingException, IOException {
        //Move image to new location in project folder called image
//        NewsDAO dao = new NewsDAO();
        Path source = Paths.get(image); //original location of image
        if (location == null) {//check if global variable location contain location of project file
            location = getLocation();
        }
        Path targetDir = Paths.get(location);//get location of image file in project 
        String newName = dao.getLatestId() + ".jpg";
        Path target = targetDir.resolve(newName);//rename image to new Name 
        
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);//copy image into target file
        return newName;//return new image location
    }

    private String getLocation() throws UnsupportedEncodingException {
        //get location of image file in project
        String path = NewsController.class.getProtectionDomain().getCodeSource().getLocation().getPath();//get location of jar file in class News Controller
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        return decodedPath.replace("build/web/WEB-INF/classes/", "").substring(1) + "images/";//only return the location of project file
    }

}
