package ru.mirea;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.mirea.common.*;

import java.io.IOException;
import java.net.URI;

/**
 * Created by master on 28.11.2016.
 */
public class CryptoClient implements AutoCloseable {

    private final URI uri;
    private final CloseableHttpClient client;
    Base base;

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
        KeyMessage keyMessage = new KeyMessage("clientPublicKey");
        String json = JsonUtil.toJson(keyMessage);
        keyRequest.setEntity(new StringEntity(json));
        try (CloseableHttpResponse response = client.execute(keyRequest)) {
            String reply = EntityUtils.toString(response.getEntity());
            keyReply = JsonUtil.fromJson(reply, KeyMessage.class);
        }
    }

    public DialogReply dialog(DialogMessage message) throws IOException {
        HttpPost request = new HttpPost(uri.resolve("/dialog"));
        String json = JsonUtil.toJson(message);
        String encryptedJson = CryptoUtil.encrypt(json, keyReply.getKey());
        request.setEntity(new StringEntity(encryptedJson));
        try (CloseableHttpResponse response = client.execute(request)) {
            String reply = EntityUtils.toString(response.getEntity());
            String decryptedReply = CryptoUtil.decrypt(reply, "clientPrivateKey");
            return JsonUtil.fromJson(decryptedReply, DialogReply.class);
        }
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
