package eng.android.nd.marwatalaat.xyzreader.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import eng.android.nd.marwatalaat.xyzreader.data.ArticleLoader;

/**
 * Created by MarwaTalaat on 4/8/2017.
 */

public class Article implements Parcelable{
    private String _ID;
    private String TITLE;
    private String AUTHOR;
    private String BODY;
    private String THUMB_URL;
    private String PHOTO_URL;
    private String ASPECT_RATIO;
    private String PUBLISHED_DATE;



    public Article(String _ID, String TITLE, String AUTHOR, String BODY, String THUMB_URL, String PHOTO_URL, String ASPECT_RATIO, String PUBLISHED_DATE) {
        this._ID = _ID;
        this.TITLE = TITLE;
        this.AUTHOR = AUTHOR;
        this.BODY = BODY;
        this.THUMB_URL = THUMB_URL;
        this.PHOTO_URL = PHOTO_URL;
        this.ASPECT_RATIO = ASPECT_RATIO;
        this.PUBLISHED_DATE = PUBLISHED_DATE;
    }

    protected Article(Parcel in) {
        _ID = in.readString();
        TITLE = in.readString();
        AUTHOR = in.readString();
        BODY = in.readString();
        THUMB_URL = in.readString();
        PHOTO_URL = in.readString();
        ASPECT_RATIO = in.readString();
        PUBLISHED_DATE = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getBODY() {
        return BODY;
    }

    public void setBODY(String BODY) {
        this.BODY = BODY;
    }

    public String getTHUMB_URL() {
        return THUMB_URL;
    }

    public void setTHUMB_URL(String THUMB_URL) {
        this.THUMB_URL = THUMB_URL;
    }

    public String getPHOTO_URL() {
        return PHOTO_URL;
    }

    public void setPHOTO_URL(String PHOTO_URL) {
        this.PHOTO_URL = PHOTO_URL;
    }

    public String getASPECT_RATIO() {
        return ASPECT_RATIO;
    }

    public void setASPECT_RATIO(String ASPECT_RATIO) {
        this.ASPECT_RATIO = ASPECT_RATIO;
    }

    public String getPUBLISHED_DATE() {
        return PUBLISHED_DATE;
    }

    public void setPUBLISHED_DATE(String PUBLISHED_DATE) {
        this.PUBLISHED_DATE = PUBLISHED_DATE;
    }

    public static ArrayList<Article> getArticleData(Cursor data) {
        ArrayList<Article> articles = new ArrayList<>();
        if (data.moveToFirst()) {
            do {
                String _id = data.getString(ArticleLoader.Query._ID);
                String title = data.getString(ArticleLoader.Query.TITLE);
                String author = data.getString(ArticleLoader.Query.AUTHOR);
                String body = data.getString(ArticleLoader.Query.BODY);
                String thumb_url = data.getString(ArticleLoader.Query.THUMB_URL);
                String photo_url = data.getString(ArticleLoader.Query.PHOTO_URL);
                String aspect_ratio = data.getString(ArticleLoader.Query.ASPECT_RATIO);
                String published_date = data.getString(ArticleLoader.Query.PUBLISHED_DATE);
                articles.add(new Article(_id,title,author,body,thumb_url,photo_url,aspect_ratio,published_date));
            } while (data.moveToNext());
        }
        return articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_ID);
        dest.writeString(TITLE);
        dest.writeString(AUTHOR);
        dest.writeString(BODY);
        dest.writeString(THUMB_URL);
        dest.writeString(PHOTO_URL);
        dest.writeString(ASPECT_RATIO);
        dest.writeString(PUBLISHED_DATE);


    }


    @Override
    public String toString() {
        return _ID+" "+TITLE+" "+AUTHOR;
    }
}
