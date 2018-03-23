package eng.android.nd.marwatalaat.xyzreader.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.xyzreader.R;

import eng.android.nd.marwatalaat.xyzreader.data.UpdaterService;
import eng.android.nd.marwatalaat.xyzreader.model.Article;
import eng.android.nd.marwatalaat.xyzreader.reciever.RefreshReciever;


/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity implements ListArticleFragment.Callback {

    private RefreshReciever mRefreshingReceiver;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRefreshing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void refresh() {
        if (!mIsRefreshing)
            startService(new Intent(this, UpdaterService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRefreshingReceiver = new RefreshReciever();
        registerReceiver(mRefreshingReceiver,
                new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    public void updateRefreshingUI(boolean mIsRefreshing) {
        this.mIsRefreshing = mIsRefreshing;
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    @Override
    public void passArticle(Article article) {
        Log.i("art", article.getTITLE());

        Intent i = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
        i.putExtra("article", article);
        startActivity(i);

    }
}
