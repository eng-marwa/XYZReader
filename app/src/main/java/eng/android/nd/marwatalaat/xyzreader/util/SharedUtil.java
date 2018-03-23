package eng.android.nd.marwatalaat.xyzreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import eng.android.nd.marwatalaat.xyzreader.model.Article;

/**
 * Created by MarwaTalaat on 4/10/2017.
 */

public class SharedUtil {
    public static ArrayList<Article> getArticlesFromShared(Context mContext) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String articles = preferences.getString("articles", null);
        Gson gson = new Gson();
        ArrayList<Article> articleList = gson.fromJson(articles, new TypeToken<ArrayList<Article>>(){}.getType());

        return articleList;
    }

    public static void saveArticleInShared(ArrayList<Article> mArticles,Context mContext) {
        Gson gson = new Gson();
        String arrayListStr = gson.toJson(mArticles);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        preferences.edit().putString("articles",arrayListStr).commit();

    }
}
