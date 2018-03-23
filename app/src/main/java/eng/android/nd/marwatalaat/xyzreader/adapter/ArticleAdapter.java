package eng.android.nd.marwatalaat.xyzreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import eng.android.nd.marwatalaat.xyzreader.data.ImageLoaderHelper;
import eng.android.nd.marwatalaat.xyzreader.model.Article;
import eng.android.nd.marwatalaat.xyzreader.ui.ArticleDetailActivity;
import eng.android.nd.marwatalaat.xyzreader.util.DynamicHeightNetworkImageView;
import eng.android.nd.marwatalaat.xyzreader.util.SharedUtil;
import eng.android.nd.marwatalaat.xyzreader.util.Utility;

/**
 * Created by MarwaTalaat on 4/5/2017.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private ArrayList<Article> mArticles;
    private Context mContext;

    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);


    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.titleView.setText(article.getTITLE());
        Date publishedDate = Utility.parsePublishedDate(article.getPUBLISHED_DATE());
        if (!publishedDate.before(START_OF_EPOCH.getTime())) {

            holder.subtitleView.setText(Html.fromHtml(Utility.parseTitleBefore(article.getPUBLISHED_DATE(), article.getAUTHOR())));
        } else {
            holder.subtitleView.setText(Html.fromHtml(Utility.parseTitleAfter(article.getPUBLISHED_DATE(), article.getAUTHOR())));
        }
        holder.thumbnailView.setImageUrl(
                article.getTHUMB_URL(),
                ImageLoaderHelper.getInstance(mContext).getImageLoader());
        holder.thumbnailView.setAspectRatio(Float.parseFloat(article.getASPECT_RATIO()));
        Picasso.with(mContext).load(article.getTHUMB_URL())
                .placeholder(R.drawable.logo)
                .into(holder.thumbnailView);

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public DynamicHeightNetworkImageView thumbnailView;
        public TextView titleView;
        public TextView subtitleView;

        public ArticleViewHolder(View view) {
            super(view);
            thumbnailView = (DynamicHeightNetworkImageView) view.findViewById(R.id.thumbnail);
            titleView = (TextView) view.findViewById(R.id.article_title);
            subtitleView = (TextView) view.findViewById(R.id.article_subtitle);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedUtil.saveArticleInShared(mArticles, mContext);
                    Intent i = new Intent(mContext, ArticleDetailActivity.class);
                    i.putExtra("adapterPosition", getAdapterPosition());
                    mContext.startActivity(i);

                }
            });
        }
    }


}
