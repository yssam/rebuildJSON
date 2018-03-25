import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;


/**
 * Created by Sam on 6/13/17.
 */
public class test {


    public static JSONObject newObj= new JSONObject(); // global JSON object for result

    public static void main(String[] args) throws IOException, ParseException {

        //////////////////////////////////////////////////////////
        //// input JSON string, please modify test cases here ////
        //////////////////////////////////////////////////////////
        String s = "{\n" +
                "    \"a\": 1,\n" +
                "    \"b\": true,\n" +
                "    \"c\": {\n" +
                "        \"d\": 3\n" +
                "        \"e\": {\n" +
                "             \"f\": 4\n" +
                "        }\n" +
                "    }\n" +
                "}";
        System.out.println("input JSON text:");
        System.out.println(s);

        // rebuild JSON
        rebuildJSON(s, "");

        // generate output JSON text
        StringWriter out = new StringWriter();
        newObj.writeJSONString(out);
        String returnJSONText = out.toString();


        System.out.println("\n\noutput JSON text:");
        System.out.println(returnJSONText);
    }



    public static void rebuildJSON(String s, String parentStr) throws IOException, ParseException {
        // using parser to get JSON object from string
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(s);

        // iterate each key of JSON object
        Set set = obj.keySet();
        for(Object o:set) {
            // if value is JSON object, recursively call rebuildJSON
            if(obj.get(o).getClass() == org.json.simple.JSONObject.class) {
                JSONObject childObj = (JSONObject) obj.get(o);
                StringWriter out = new StringWriter();
                childObj.writeJSONString(out);
                String jsonText = out.toString();
                // recursive call and pass current key as parent str to next level
                rebuildJSON(jsonText, parentStr + o.toString() + ".");
            }
            else { // if value is not JSON object, we update global JSON object

                newObj.put(parentStr + o.toString(), obj.get(o));
            }
        }
    }


}

