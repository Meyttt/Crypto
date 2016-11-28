package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.ClientKeyMessage;
import ru.mirea.common.JsonUtil;
import ru.mirea.common.ServerKeyMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        ClientKeyMessage message = JsonUtil.fromJson(string, ClientKeyMessage.class);
        req.getSession().setAttribute("publicKey", message.getKey());

        ServerKeyMessage reply = new ServerKeyMessage("serverPublicKey");
        resp.getWriter().write(JsonUtil.toJson(reply));
    }
}
