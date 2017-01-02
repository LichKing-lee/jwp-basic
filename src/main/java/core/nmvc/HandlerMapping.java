package core.nmvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LichKing on 2017. 1. 2..
 */
public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
}
