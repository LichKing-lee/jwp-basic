package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    List<HandlerMapping> mappingList;

    @Override
    public void init() throws ServletException {
        LegacyHandlerMapping rm = new LegacyHandlerMapping();
        rm.initMapping();
        AnnotationHandlerMapping am = new AnnotationHandlerMapping("next.controller");
        am.initialize();

        this.mappingList = Arrays.asList(rm, am);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object controller = mappingList.get(0).getHandler(req);
        controller = controller == null ? mappingList.get(1).getHandler(req) : controller;

        ModelAndView mav;
        if(controller instanceof Controller){
            try {
                mav = ((Controller) controller).execute(req, resp);
                View view = mav.getView();
                view.render(mav.getModel(), req, resp);
                return;
            }catch (Exception e){
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        }else if(controller instanceof HandlerExecution){
            try {
                mav = ((HandlerExecution)controller).handle(req, resp);
                View view = mav.getView();
                view.render(mav.getModel(), req, resp);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new IllegalStateException();
    }
}
