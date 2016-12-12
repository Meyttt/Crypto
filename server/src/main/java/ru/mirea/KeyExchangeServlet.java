package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.Base;
import ru.mirea.common.KeyMessage;
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
public class KeyExchangeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        byte[] bytes = IO.readBytes(req.getInputStream());
        String string = new String(bytes, "UTF-8");
        KeyMessage message = JsonUtil.fromJson(string, KeyMessage.class);
        Integer serverPrivateKey = Integer.valueOf(15381);
        Base base = (Base) req.getSession().getAttribute("base");
        BigInteger serverPublicKey=base.getPublicKey(serverPrivateKey);
        BigInteger code = base.getCode(serverPrivateKey,message.getKey());
        req.getSession().setAttribute("code",code);
//        req.getSession().setAttribute("publicKey", message.getKey());

        KeyMessage reply = new KeyMessage(serverPublicKey);
        resp.getWriter().write(JsonUtil.toJson(reply));
    }
}
