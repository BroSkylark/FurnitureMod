package Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class JSON
{
    private Map<String, String> content;

    public JSON(String path)
    {
        content = new HashMap<String, String>();
        String s = "";

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while(br.ready())
            {
                s += br.readLine();
            }

            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("(?s)\\[\\{(.*)\\}\\](?-s)");
        String c[] = p.matcher(s).group(1).split(",");

        for(String v : c)
        {
            String t[] = v.replaceAll("\"", "").split(":");
            content.put(t[0], t[1]);
        }
    }

    public String getValue(String ID)
    {
        if(!content.containsKey(ID)) throw new RuntimeException("ERR: Key '" + ID + "' doesn't exist!");

        return content.get(ID);
    }
}
