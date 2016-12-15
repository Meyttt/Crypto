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

/**
 * Created by a.chebotareva on 09.12.2016.
 */
public class BaseExchange extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String clientPublicKey = (String) req.getSession().getAttribute("publicKey");
        if (clientPublicKey == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        byte[] bytes = IO.readBytes(req.getInputStream());
        String encryptedString = new String(bytes, "UTF-8");
        String decryptedString = CryptoUtil.decrypt(encryptedString, "serverPrivateKey");
        DialogMessage message = JsonUtil.fromJson(decryptedString, DialogMessage.class);

        DialogReply reply = new DialogReply("Reply: " + message.getText());
        String replyString = JsonUtil.toJson(reply);
        resp.getWriter().write(CryptoUtil.encrypt(replyString, clientPublicKey));
    }
}
