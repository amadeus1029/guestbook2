<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%
    request.setCharacterEncoding("UTF-8"); //이거 안하면 문자 다 깨짐
    GuestBookDao guestBookDao = new GuestBookDao();
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    String content = request.getParameter("content");
    GuestBookVo guestBookVo = new GuestBookVo(name, password, content);
    guestBookDao.guestBookInsert(guestBookVo);

    response.sendRedirect("./guestbook.jsp");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
