package by.smirnov.listener;

import by.smirnov.base.UserBase;
import by.smirnov.facade.User;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

public class CloseSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession().getAttribute("user");
        try {
            user.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
