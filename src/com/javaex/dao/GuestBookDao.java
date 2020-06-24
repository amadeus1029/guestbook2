package com.javaex.dao;

import com.javaex.vo.GuestBookVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestBookDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String id = "webdb";
    private String pw = "webdb";

    private void getConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, id, pw);
        } catch (ClassNotFoundException e) {
            System.out.println("error: 드라이버 로딩 실패 - " + e);
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
    }

    // 방명록 추가
    public void guestBookInsert(GuestBookVo guestBookVo) {
        getConnection();

        try {

            String query = "";
            query += " INSERT INTO guestbook ";
            query += " VALUES (seq_no_id.nextval, ?, ?, ?, sysdate) ";

            pstmt = conn.prepareStatement(query); // 쿼리로 만들기

            pstmt.setString(1, guestBookVo.getName());
            pstmt.setString(2, guestBookVo.getPassword());
            pstmt.setString(3, guestBookVo.getContent());

            pstmt.executeUpdate(); // 쿼리문 실행

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        close();
    }


    //방명록 리스트 (검색 없음)
    public List<GuestBookVo> getGuestBookList() {
        return getGuestBookList("");
    }

    //방명록 리스트 (검색 대비)
    public List<GuestBookVo> getGuestBookList(String keyword) {
        List<GuestBookVo> guestBookList = new ArrayList<GuestBookVo>();

        getConnection();

        try {
            String query = "";
            query += " select  no, ";
            query += "         name, ";
            query += "         password, ";
            query += "         content, ";
            query += "         reg_date ";
            query += " from guestbook";

            if (keyword != "" || keyword == null) {
                query += " where name like ? ";
                query += " or content like ? ";
                pstmt = conn.prepareStatement(query);

                pstmt.setString(1, '%' + keyword + '%');
                pstmt.setString(2, '%' + keyword + '%');
            } else {
                pstmt = conn.prepareStatement(query);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int no = rs.getInt("no");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String content = rs.getString("content");
                String regDate = rs.getString("reg_date");

                GuestBookVo guestBookVo = new GuestBookVo(no, name, password, content, regDate);
                guestBookList.add(guestBookVo);
            }

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }

        close();

        return guestBookList;

    }


    //방명록 수정
    public void guestBookUpdate(GuestBookVo guestBookVo) {
        getConnection();

        try {
            String query = "";
            query += " update guestbook ";
            query += " set name = ? , ";
            query += "     password = ? , ";
            query += "     content = ? , ";
            query += "     reg_date = ? ";
            query += " where no = ? ";

            pstmt = conn.prepareStatement(query); // 쿼리로 만들기

            pstmt.setString(1, guestBookVo.getName()); // ?(물음표) 중 1번째, 순서중요
            pstmt.setString(2, guestBookVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
            pstmt.setString(3, guestBookVo.getContent()); // ?(물음표) 중 3번째, 순서중요
            pstmt.setString(4, guestBookVo.getDate()); // ?(물음표) 중 3번째, 순서중요
            pstmt.setInt(5, guestBookVo.getNo()); // ?(물음표) 중 4번째, 순서중요

            pstmt.executeUpdate(); // 쿼리문 실행

            // 4.결과처리
            // System.out.println(count + "건 수정되었습니다.");

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }

        close();
    }

    //방명록
    public GuestBookVo getGuestBook(int no) {
        getConnection();

        GuestBookVo guestBookVo = null;

        try {
            String query = ""; // 쿼리문 문자열만들기, ? 주의
            query += " select no, ";
            query += "        name, ";
            query += "        password, ";
            query += "        content, ";
            query += "        reg_date ";
            query += " from guestbook ";
            query += " where no = ? ";

            pstmt = conn.prepareStatement(query); // 쿼리로 만들기
            pstmt.setInt(1, no); // ?(물음표) 중 1번째, 순서중요

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int guestNo = rs.getInt("no");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String content = rs.getString("content");

                guestBookVo = new GuestBookVo(guestNo, name, password, content);
            }

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }

        close();
        return guestBookVo;
    }

    //방명록 삭제
    public void guestBookDelete(int no) {
        getConnection();

        try {
            // 3. SQL문 준비 / 바인딩 / 실행
            String query = ""; // 쿼리문 문자열만들기, ? 주의
            query += " delete from guestbook ";
            query += " where no = ? ";
            pstmt = conn.prepareStatement(query); // 쿼리로 만들기

            pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

            pstmt.executeUpdate(); // 쿼리문 실행


        } catch (SQLException e) {
            System.out.println("error:" + e);
        }

        close();
    }
}
