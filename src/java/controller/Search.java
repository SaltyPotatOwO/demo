/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NewsDAO;
import dbObject.News;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("search_title");
            String rawCategoryId = request.getParameter("search_category");

            if (title == null || title.trim().length() == 0) {//if user entered no title in search bar
                if (rawCategoryId == null) {
                    request.getRequestDispatcher("GetListNews");   //if user did NOT chose a category choice  
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
            forwardToSearchPage(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY TITLE");
        }
    }

    private void searchByCategory(HttpServletRequest request, HttpServletResponse response, int cat_id) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            ArrayList<News> listNews = newsDAO.searchCategory(cat_id);
            request.setAttribute("listNews", listNews);
            forwardToSearchPage(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY CATEGORY");
        }
    }

    private void searchByTitleAndCateogory(HttpServletRequest request, HttpServletResponse response, String title, int cat_id) {
        try {
            NewsDAO newsDAO = new NewsDAO();
            ArrayList<News> listNews = newsDAO.searchTitleCat(title, cat_id);
            request.setAttribute("listNews", listNews);
            forwardToSearchPage(request, response);
        } catch (Exception e) {
            System.out.println("ERROR SEARCHING NEWS BY TITLE AND CATEGORY");
        }
    }

    private void forwardToSearchPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("searchResult.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR FORWARDING TO SEACH PAGE");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
