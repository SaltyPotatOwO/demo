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
public class GetListNews extends HttpServlet {
   


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ArrayList<News> listNews = new ArrayList<>();
        try {
            NewsDAO newsDAO = new NewsDAO();
//            int amount = Integer.parseInt(request.getParameter("10"));//Number of news shown
            listNews = newsDAO.getListNews(30);//NEEDS TO BE CHANGED 
            request.setAttribute("listNews", listNews);
            request.getRequestDispatcher("HomePage").forward(request, response);
        } catch (Exception e) {
            System.out.println("ERROR GETTING LIST NEWS");
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
