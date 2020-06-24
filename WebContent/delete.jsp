<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%

    GuestBookDao guestBookDao = new GuestBookDao();
    String password = request.getParameter("password");
    int no = Integer.parseInt(request.getParameter("no"));
    GuestBookVo guestBookVo = guestBookDao.getGuestBook(no);

    if(password.equals(guestBookVo.getPassword())) {
        guestBookDao.guestBookDelete(no);
        response.sendRedirect("./guestbook.jsp");
    } else {
        PrintWriter asdf = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        asdf.println("<script>alert('wrong password');location.href='./guestbook.jsp';</script>");
        asdf.flush();
    }

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
