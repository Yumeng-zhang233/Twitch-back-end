package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.db.MySQLConnection;
import com.laioffer.jupiter.db.MySQLException;
import com.laioffer.jupiter.entity.FavoriteRequestBody;
import com.laioffer.jupiter.entity.Item;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "FavoriteServlet", value = "/favorite")
public class FavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
            if (session == null) {
                      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                      return;
                 }
             String userId = (String) session.getAttribute("user_id");

        try (MySQLConnection conn = new MySQLConnection()) {
            Map<String, List<Item>> itemMap = conn.getFavoriteItems(userId);

            ServletUtil.writeData(response, itemMap);
        } catch (MySQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get user ID from request URL, this is a temporary solution
        // since we don’t support session now
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
             return;
                }
        String userId = (String) session.getAttribute("user_id");

        FavoriteRequestBody body = ServletUtil.readRequestBody(FavoriteRequestBody.class, request);

        if(body == null){
            System.out.println("Convert Json to FavoriteRequestBody failed");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        try(MySQLConnection conn = new MySQLConnection()){
            conn.setFavoriteItem(userId, body.getFavoriteItem());
        }catch (MySQLException e){
            throw new ServletException(e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);//不能创建一个新的session，如果没登录返回一个空值
        if (session == null) {
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return;
         }
       String userId = (String) session.getAttribute("user_id");

        FavoriteRequestBody body = ServletUtil.readRequestBody(FavoriteRequestBody.class, request);

        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        MySQLConnection connection = null;
        try {
            // Remove the favorite item to the database
            connection = new MySQLConnection();
            connection.unsetFavoriteItem(userId, body.getFavoriteItem().getId());
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
