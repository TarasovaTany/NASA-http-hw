import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String nasaUrl = "https://api.nasa.gov/planetary/apod" +
                "?api_key=gCU5S1venfCHOpLvdKbci4gmyhBiN6l2taGR1hOo";

        ObjectMapper mapper = new ObjectMapper();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(nasaUrl);

        CloseableHttpResponse response = client.execute(request);

        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
        System.out.println(answer);

        CloseableHttpResponse image = client.execute(new HttpGet(answer.url));

        String[] answerSplitted = answer.url.split("/");

        FileOutputStream file = new FileOutputStream(answerSplitted[answerSplitted.length - 1]);
        image.getEntity().writeTo(file);
    }
}
