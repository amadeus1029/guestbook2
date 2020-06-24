package com.javaex.controller;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if("guestbook".equals(action)) {
            GuestBookDao guestBookDao = new GuestBookDao();
            List<GuestBookVo> guestBookList = guestBookDao.getGuestBookList();

            request.setAttribute("gbList",guestBookList);
            WebUtil.forward(request,response,"/WEB-INF/guestbook.jsp");
        } else if("insert".equals(action)) {
            GuestBookDao guestBookDao = new GuestBookDao();
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String content = request.getParameter("content");
            GuestBookVo guestBookVo = new GuestBookVo(name, password, content);
            guestBookDao.guestBookInsert(guestBookVo);

            WebUtil.redirect(request,response,"/gb2/gbc?action=guestbook");
        } else if("delete".equals(action)) {
            GuestBookDao guestBookDao = new GuestBookDao();
            String password = request.getParameter("password");
            int no = Integer.parseInt(request.getParameter("no"));
            GuestBookVo guestBookVo = guestBookDao.getGuestBook(no);

            if(password.equals(guestBookVo.getPassword())) {
                guestBookDao.guestBookDelete(no);
                WebUtil.redirect(request,response,"/gb2/gbc?action=guestbook");
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html; charset=UTF-8");
                out.println("<script>alert('wrong password');location.href='/gb2/gbc?action=guestbook';</script>");
                out.flush();
            }
        }
    }
}
