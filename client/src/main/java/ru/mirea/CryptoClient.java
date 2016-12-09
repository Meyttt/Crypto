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

    private ServerKeyMessage keyReply;

    public CryptoClient(URI uri) {
        this.uri = uri;
        this.client = HttpClients.createDefault();
    }

    public void exchangeKeys() throws IOException {
        HttpPost keyRequest = new HttpPost(uri.resolve("/keys"));
        ClientKeyMessage keyMessage = new ClientKeyMessage("clientPublicKey");
        String json = JsonUtil.toJson(keyMessage);
        keyRequest.setEntity(new StringEntity(json));
        try (CloseableHttpResponse response = client.execute(keyRequest)) {
            String reply = EntityUtils.toString(response.getEntity());
            keyReply = JsonUtil.fromJson(reply, ServerKeyMessage.class);
        }
    }
    public void baseExchange(int base, int module){
        HttpPost keyRequest = new HttpPost(uri.resolve("/bases"));
        Bases bases = new Bases()
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
