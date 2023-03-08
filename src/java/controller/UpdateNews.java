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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

/**
 *
 * @author Asus
 */
public class UpdateNews extends HttpServlet {
   String location = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
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
        NewsDAO dao = new NewsDAO();
        String[] dataArray = new String[6];
        News news = null;
        int i = 0;
        try {
            
            ServletFileUpload upload = new ServletFileUpload();
            response.setContentType("text/plain");
            FileItemIterator iterator = upload.getItemIterator(request);
            FileItemStream item = iterator.next();
            while (i < 5) {
                dataArray[i] = Streams.asString(item.openStream());
                item = iterator.next();
                i++;
            }
            dataArray[i] = item.getName();
            moveImages(item);
            for (String data : dataArray) {
                System.out.println(data);
            }
            news.setNews_id(Integer.parseInt(dataArray[0]));
            news.setCat_id(Integer.parseInt(dataArray[1]));
            news.setTitle(dataArray[2]);
            news.setSubtitle(dataArray[3]);
            news.setContent(dataArray[4]);
            news.setImage(dataArray[5]);
            dao.insertNews(news);
            
            response.sendRedirect("index.html");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void moveImages(FileItemStream item) {
        NewsDAO dao = new NewsDAO();
        try {
            InputStream initialStream = item.openStream();
            //Move image to new location in project folder called image
            if (location == null) {
                location = getLocation();
            }
            Path targetDir = Paths.get(location);//get location of image file in project 
            Path target = targetDir.resolve(item.getName());//get location of copied image in the project(targetDir + name of image)
            Files.copy(initialStream, target, StandardCopyOption.REPLACE_EXISTING);//copy image into target file
            IOUtils.closeQuietly(initialStream);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getLocation() throws UnsupportedEncodingException {
        //get location of image file in project
        String path = UpdateNews.class.getProtectionDomain().getCodeSource().getLocation().getPath();//get location of jar file in class News Controller
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        return decodedPath.replace("build/web/WEB-INF/classes/", "").substring(1) + "web/images/";//only return the location of project file
    }

}
