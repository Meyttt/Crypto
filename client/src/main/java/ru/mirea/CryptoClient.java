package ru.mirea;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.mirea.common.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;

/**
 * Created by master on 28.11.2016.
 */
public class CryptoClient implements AutoCloseable {

    private final URI uri;
    private final CloseableHttpClient client;
    private Base base;
    private BigInteger code;

    private KeyMessage keyReply;

    public CryptoClient(URI uri) {
        this.uri = uri;
        this.client = HttpClients.createDefault();
    }

    public void exchangeBase() throws IOException {
        HttpPost keyRequest = new HttpPost(uri.resolve("/base"));
        try (CloseableHttpResponse response = client.execute(keyRequest)) {
            String reply = EntityUtils.toString(response.getEntity());
            base = JsonUtil.fromJson(reply, Base.class);
        }

    }

    public void exchangeKeys() throws IOException {
        HttpPost keyRequest = new HttpPost(uri.resolve("/keys"));
        Integer clientPrivateKey = Integer.valueOf(4411);
        BigInteger clientPublicKey = base.getPublicKey(clientPrivateKey);
        KeyMessage keyMessage = new KeyMessage(clientPublicKey);
        String json = JsonUtil.toJson(keyMessage);
        keyRequest.setEntity(new StringEntity(json));
        try (CloseableHttpResponse response = client.execute(keyRequest)) {
            String reply = EntityUtils.toString(response.getEntity());
            keyReply = JsonUtil.fromJson(reply, KeyMessage.class);
        }
        code = base.getCode(clientPrivateKey,keyReply.getKey());

    }
    public String verification(VerificationData verificationData) throws IOException, VerificationException {
		System.out.println("ver started");
		HttpPost request = new HttpPost(uri.resolve("/verification"));
        BigInteger login = CryptoUtil.encrypt(verificationData.getLogin(),code);
        BigInteger password = CryptoUtil.encrypt(verificationData.getPassword(),code);
        request.setEntity(new StringEntity(JsonUtil.toJson(new VerificationDataEncrypted(login,password))));
        try (CloseableHttpResponse response = client.execute(request)){
			String str = EntityUtils.toString(response.getEntity());
            String serverAnswer = CryptoUtil.decrypt(new BigInteger(str),code);
            return serverAnswer;
        }
    }

    public String registration(VerificationData registrationData) throws IOException, VerificationException {
        HttpPost request = new HttpPost(uri.resolve("/registration"));
        BigInteger login = CryptoUtil.encrypt(registrationData.getLogin(),code);
        BigInteger password = CryptoUtil.encrypt(registrationData.getPassword(),code);
        request.setEntity(new StringEntity(JsonUtil.toJson(new VerificationDataEncrypted(login,password))));
        try (CloseableHttpResponse response = client.execute(request)){
            String str = EntityUtils.toString(response.getEntity());
//			System.out.println(str);
			String serverAnswer = CryptoUtil.decrypt(new BigInteger(str),code);
            return serverAnswer;
        }
    }

    public DialogMessage dialog(DialogMessage message) throws IOException {
        HttpPost request = new HttpPost(uri.resolve("/dialog"));
        BigInteger encrypted = CryptoUtil.encrypt(message.getText(),code);
        request.setEntity(new StringEntity(encrypted+""));
        try (CloseableHttpResponse response = client.execute(request)) {
            String reply = EntityUtils.toString(response.getEntity());
            String decryptedReply = CryptoUtil.decrypt( new BigInteger(reply), code);
            return new DialogMessage(decryptedReply);
        }
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

}
