package team10.smartbell;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused, WeakerAccess")
public class Menu {
    enum Type {
        Coffee, Tea, Dessert, Ice
    }


    public static Menu newInstance(String json) {
        return new GsonBuilder().create().fromJson(json, Menu.class);
    }


    private int     type;
    private String  name;
    private float   price;
    private String  description;


    public Type getType() {
        return Type.values()[type - 1];
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    @SuppressWarnings("all")
    public static List<Menu> sample(Context context) {
        try {
            InputStream stream = context.getAssets().open("sample.json");

            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();

            String json = new String(buffer);

            List<Menu> list = new ArrayList<>();

            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement element : array) {
                String obj = element.getAsJsonObject().toString();
                list.add(Menu.newInstance(obj));
            }

            return list;
        } catch (Exception e) {
            Log.e("", "", e);
        }

        return null;
    }
}
