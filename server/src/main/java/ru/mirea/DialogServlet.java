package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.CryptoUtil;
import ru.mirea.common.DialogMessage;
import ru.mirea.common.DialogReply;
import ru.mirea.common.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

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
        System.out.println("getting "+encryptedString);
        encryptedString= Base64.getDecoder().decode(encryptedString).toString();
        String decryptedString = CryptoUtil.decrypt(encryptedString, (BigInteger) req.getSession().getAttribute("code"));
        DialogMessage message = JsonUtil.fromJson(decryptedString, DialogMessage.class);

        DialogReply reply = new DialogReply("Reply: " + message.getText());
        String replyString = JsonUtil.toJson(reply);
        resp.getWriter().write(CryptoUtil.encrypt(replyString, (BigInteger) req.getSession().getAttribute("code")));
    }
}
