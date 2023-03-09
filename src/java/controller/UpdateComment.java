/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CommentDAO;
import dbObject.Comments;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class UpdateComment extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            CommentDAO commentDAO = new CommentDAO();

            int comment_id = Integer.parseInt(request.getParameter("comment_id"));
            int news_id = Integer.parseInt(request.getParameter("news_id"));
            String comment_content = request.getParameter("comment_content");
            
            Comments comment = new Comments(comment_id, comment_content);
            commentDAO.updateComments(comment);//update comment into database
            
            response.sendRedirect("GetNews?news_id="+news_id);
        } catch (Exception e) {
            System.out.println("ERROR UPDATING COMMENT");
        }
    }

}
