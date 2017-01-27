package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.Future;

/**
 * Created by master on 28.11.2016.
 */
public class DialogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        byte[] bytes = IO.readBytes(req.getInputStream());
        String string = new String(bytes, "UTF-8");
        String userMessageEncrypted = JsonUtil.fromJson(string,String.class);
        String userMessage = CryptoUtil.decrypt(new BigInteger(userMessageEncrypted), (BigInteger) req.getSession().getAttribute("code"));
        Quarantine quarantine = new Quarantine(userMessage.split("\\n"));
        String answer = null;
        try {
            answer = quarantine.call();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BigInteger encryptAnswer=CryptoUtil.encrypt(answer, (BigInteger) req.getSession().getAttribute("code"));
        resp.getWriter().write(encryptAnswer.toString());
    }
}
