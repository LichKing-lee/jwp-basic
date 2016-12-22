package next.web;

import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by LichKing on 2016. 12. 19..
 */
@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);
    private UserDao userDao;

    @Override
    public void init(ServletConfig config){
        this.userDao = new UserDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        User user;
        try {
            user = userDao.findByUserId(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("userId :: " + userId);
        }

        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/updateForm.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            log.debug("{}", e);
        }
    }
}
