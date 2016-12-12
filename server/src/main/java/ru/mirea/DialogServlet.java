package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.CryptoUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by master on 28.11.2016.
 */
public class DialogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        BigInteger clientPublicKey = (BigInteger) req.getSession().getAttribute("code");
        if (clientPublicKey == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        byte[] bytes = IO.readBytes(req.getInputStream());
        String encryptedString = new String(bytes, "UTF-8");
        BigInteger userdata = new BigInteger(encryptedString);
        String userText=CryptoUtil.decrypt(userdata,clientPublicKey);

        System.out.println("USERTEXT AT SERVER: "+userText);

        BigInteger answer = CryptoUtil.encrypt(userText,clientPublicKey);
        resp.getWriter().write(answer.toString());
    }
}
