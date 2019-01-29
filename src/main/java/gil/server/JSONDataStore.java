package gil.server;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class JSONDataStore {
    private IOProvider ioProvider;

    public JSONDataStore(IOProvider ioProvider) {
        this.ioProvider = ioProvider;
    }

    public JsonObject loadData() throws IOException {
        InputStream input = ioProvider.getInputStream();
        JsonReader reader = Json.createReader(new InputStreamReader(input, "UTF-8"));
        JsonObject data;

        try {
            data = reader.readObject();
        } catch(JsonParsingException e) {
            data = Json.createObjectBuilder().build();
        } finally {
            reader.close();
            input.close();
        }

        return data;
    }

    public void storeData(JsonObject data) throws IOException {
        OutputStream output = ioProvider.getOutputStream();
        PrintWriter printWriter = new PrintWriter(output);

        printWriter.write(data.toString());
        printWriter.flush();
        printWriter.close();
    }

}
