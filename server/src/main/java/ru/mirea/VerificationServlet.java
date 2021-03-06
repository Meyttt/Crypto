package ru.mirea;

import org.eclipse.jetty.util.IO;
import ru.mirea.common.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Admin on 14.12.2016.
 */
public class VerificationServlet extends HttpServlet {
	Database database = new Database();

	public VerificationServlet() throws IOException, ClassNotFoundException {
	}

	@Override
	protected void doPost(HttpServletRequest req,
						  HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		byte[] bytes = IO.readBytes(req.getInputStream());
		String string = new String(bytes, "UTF-8");
		VerificationDataEncrypted verificationDataEncrypted = JsonUtil.fromJson(string,VerificationDataEncrypted.class);
		String login = CryptoUtil.decrypt(verificationDataEncrypted.getLogin(), (BigInteger) req.getSession().getAttribute("code"));
		String password = CryptoUtil.decrypt(verificationDataEncrypted.getPassword(),(BigInteger) req.getSession().getAttribute("code"));
		String answer=null;
		try {
			answer = database.verification(login,password);
			BigInteger encryptAnswer=CryptoUtil.encrypt(answer, (BigInteger) req.getSession().getAttribute("code"));
			System.out.println(encryptAnswer.toString());
			resp.getWriter().write(encryptAnswer.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
