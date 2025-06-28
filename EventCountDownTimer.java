package com.project;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TimerData") 
public class EventCountDownTimer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final LocalDateTime EVENT_DATE = LocalDateTime.of(2025, 06, 25, 10, 0,0);
    private static final ZoneId TIME_ZONE = ZoneId.of("Asia/Kolkata"); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long millisecondsLeft = calculateTimeLeftInMilliseconds();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{ \"millisecondsLeft\": " + millisecondsLeft + ", " +
                              "\"eventDate\": \"" + getEventDateFormatted() + "\" }";

        response.getWriter().write(jsonResponse);
    }

    private long calculateTimeLeftInMilliseconds() {
        LocalDateTime now = LocalDateTime.now(TIME_ZONE);
        Duration duration = Duration.between(now, EVENT_DATE);
        return duration.toMillis();
    }

    public static String getEventDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' hh:mm:ss a z");
        return EVENT_DATE.atZone(TIME_ZONE).format(formatter);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}