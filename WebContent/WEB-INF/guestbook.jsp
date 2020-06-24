<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%@ page import="java.util.List" %>
<%
    List<GuestBookVo> guestBookList = (List<GuestBookVo>) request.getAttribute("gbList");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>방명록 시스템</title>
    <link rel="stylesheet" href="./assets/css/common.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="./assets/js/modal.js"></script>
</head>
<body>
    <h1>방명록을 남겨주세요</h1>
    <form action="/gb2/gbc" method="get">
        <input type="hidden" name="action" value="insert">
        <table>
            <colgroup>
                <col style="width:200px;">
                <col style="width:300px;">
            </colgroup>
            <tr>
                <td>
                    <span>이름</span>
                    <input type="text" name="name" value="">
                </td>
                <td>
                    <span>비밀번호</span>
                    <input type="password" name="password" value="">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea style="width:95%;height:200px;margin: 10px;" name="content"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <button type="submit">확인</button>
                </td>
            </tr>
        </table>
    </form>
    <%for (GuestBookVo gbVo : guestBookList) {%>
        <table>
            <colgroup>
                <col style="width:50px;">
                <col style="width:100px;">
                <col style="width:250px;">
                <col style="width:100px;">
            </colgroup>
            <tr>
                <td class="no"><%=gbVo.getNo()%></td>
                <td><%=gbVo.getName()%></td>
                <td><%=gbVo.getDate()%></td>
                <td>
                    <button type="button">수정</button>
                    <button type="button" onclick="deleteGuestBook($(this));">삭제</button>
                </td>
            </tr>
            <tr>
                <td colspan="4"><%=gbVo.getContent()%></td>
            </tr>
        </table>
    <%}%>
    <div class="modal-layer" id="deleteGuestBook">
        <form class="modal-wrapper" action="/gb2/gbc" method="get">
            <input type="hidden" name="action" value="delete">
            <h2 class="title">
                삭제하시려면 비밀먼호를 입력해주세요
            </h2>
            <input type="hidden" name="no" value="">
            <input type="password" name="password" value="" placeholder="비밀번호를 입력해주세요">
            <div class="modal-btn-area full-modal-btn">
                <button type="submit" class="modal-confirm">확인</button>
            </div>
        </form>
    </div>
    <script>
        function deleteGuestBook(target) {
            var no = target.parent('td').siblings("td.no").text();
            $("#deleteGuestBook").find("input[name='no']").val(no);
            showModal('#deleteGuestBook');
        }
    </script>
</body>
</html>
