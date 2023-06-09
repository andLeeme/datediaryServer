package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.ScheduleRequest;
import bless.datediary.model.ScheduleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ScheduleController {
    @PostMapping("/api/main2")
    public ArrayList<ScheduleResponse> Schedule(String a) throws SQLException {

        System.out.println(a);

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        ArrayList<ScheduleResponse> result = new ArrayList<ScheduleResponse>();

        DBconn = new DBConn();
        conn = DBconn.connect();
        String sql = " select * from schedule;";
        Statement stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery(sql);
        while (rst.next()) {
            ScheduleResponse schedule = new ScheduleResponse();
            schedule.setCouple_index(rst.getString(1));
            schedule.setSchedule_index(rst.getString(2));
            schedule.setStart_day(rst.getString(3));
            schedule.setStart_time(rst.getString(4));
            schedule.setEnd_day(rst.getString(5));
            schedule.setEnd_time(rst.getString(6));
            schedule.setAllDayCheck(rst.getString(7));
            schedule.setTitle(rst.getString(8));
            schedule.setContents(rst.getString(9));
            schedule.setPlace_code(rst.getString(10));
            schedule.setMission_code(rst.getString(11));
            schedule.setStory_reg(rst.getString(12));

            result.add(schedule);
        }
        rst.close();
        stmt.close();
        conn.close();

        System.out.println(result);

        return result;
    }


    @PostMapping("/api/postTest")
    public ArrayList<HashMap<String,Object>> postTest(@RequestBody HashMap<String, Object> _tmp) throws SQLException {


        System.out.println(_tmp.get("id"));
        System.out.println(_tmp.get("password"));

        ArrayList<ScheduleRequest> postModels = new ArrayList<ScheduleRequest>();

        for (int i = 0; i < _tmp.size(); i++) {

            ScheduleRequest postModel = new ScheduleRequest();

            postModel.setId(_tmp.get("id").toString());

            postModel.setPassword(_tmp.get("password").toString());

            postModels.add(postModel);

            System.out.println(postModels.get(0));
        }

        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;

        String result = "기본값";

        try {

            DBconn = new DBConn();
            conn = DBconn.connect();

            String id = postModels.get(0).getId();
            String password = postModels.get(0).getPassword();

            System.out.println("입력한 아이디 : "+id);
            System.out.println("입력한 비밀번호 : "+password);

            String sql = "select user_id from user where user_id =\"" + id + "\"" + "and user_pw =\"" + password + "\";";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String selectId = rs.getString(1).toString();
                if (selectId == null) {
                    result = "아이디가 없어요..";
                    System.out.println(result);
                } else if (selectId.equals(id)) {
                    result = id + "님 환영합니다!";
                }
            } else {
                result = "아이디가 없어요..";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }

        }

        System.out.println("결과값 : " + result);

        HashMap<String,Object> result2 = new HashMap<String,Object>();

        result2.put("result", result);
        result2.put("status", "서버에서 가져온 데이터입니다");

        ArrayList<HashMap<String,Object>> result3 = new ArrayList<HashMap<String,Object>>();

        result3.add(result2);

        System.out.println(result3);

        return result3;
    }

    @GetMapping("/hyunha")
    public String postTest() throws SQLException {

        return "정말 고마워 사랑해";
    }
}
