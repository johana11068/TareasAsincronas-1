package com.example.jonmid.tareasasincronas.Parser;

import com.example.jonmid.tareasasincronas.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AULA 7 on 12/04/2018.
 */

public class Json {
    public static List<Post> getDataJson(String s) throws JSONException {
        JSONArray jsonArray = new JSONArray(s);// pasar cadena a arreglo
        List<Post> postList = new ArrayList<>();//pasar iten por item como objeto

        for (int i = 0; i <= jsonArray.length(); i++){
            JSONObject item = jsonArray.getJSONObject(i);//ingresar a cada item

            Post post = new Post();
            post.setId(item.getInt("id"));
            post.setTittle(item.getString("tittle"));
            post.setBody(item.getString("body"));

            postList.add(post);
        }
        return postList;
    }

}
