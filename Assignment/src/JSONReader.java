
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSONReader {

    public static JSONObject ReadJSON(String pathName) {
        JSONParser parser = new JSONParser();
        FileReader fileReader;
        JSONObject obj = null;

        try {
            File file = new File(pathName);
            fileReader = new FileReader(file);

            obj = (JSONObject) parser.parse(fileReader);
            //parseObject(obj);
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace() + " :File Not Found");
        } catch (ParseException e) {
            System.out.println(e.getStackTrace() + " :Could not parse");
        } catch (IOException e) {
            System.out.println(e.getStackTrace() + " :IOException");
        }
        return obj;
    }

    

}
