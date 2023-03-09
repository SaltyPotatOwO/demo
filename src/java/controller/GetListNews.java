/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CatDAO;
import dao.NewsDAO;
import dao.UserDAO;
import dbObject.News;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.User;

/**
 *
 * @author Asus
 */
public class GetListNews extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            NewsDAO newsDAO = new NewsDAO();
            CatDAO catsDAO = new CatDAO();
            UserDAO userDAO = new UserDAO();
            String location = "images/";
//            int amount = Integer.parseInt(request.getParameter("10"));//Number of news shown
            ArrayList<News> listNews = newsDAO.getListNews(30);//NEEDS TO BE CHANGED 
            HashMap<Integer,Category> listCategory = catsDAO.getAllCategorys();
            HashMap<Integer,User> listUser = userDAO.getAllUser();
            
            request.setAttribute("listNews", listNews);
            request.setAttribute("listCategory", listCategory);
            request.setAttribute("listUser", listUser);
            request.setAttribute("location", location);
            request.getRequestDispatcher("HomePage").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }


}
