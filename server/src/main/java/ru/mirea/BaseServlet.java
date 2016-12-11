package ru.mirea;

import ru.mirea.common.Base;
import ru.mirea.common.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by master on 28.11.2016.
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        BigInteger generator = BigInteger.valueOf(11);
        BigInteger module = BigInteger.valueOf(12);
        Base base = new Base(generator,module);
        req.getSession().setAttribute("base",base);
        resp.getWriter().write(JsonUtil.toJson(base));
    }
}
