package eng.android.nd.marwatalaat.xyzreader.ui;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyzreader.R;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import eng.android.nd.marwatalaat.xyzreader.adapter.ArticlePagerAdapter;
import eng.android.nd.marwatalaat.xyzreader.model.Article;
import eng.android.nd.marwatalaat.xyzreader.util.PageSplitter;
import eng.android.nd.marwatalaat.xyzreader.util.SharedUtil;
import eng.android.nd.marwatalaat.xyzreader.util.Utility;


/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity {

   
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    private ViewPager mPager;
    private ImageView mPhotoView;
    private CollapsingToolbarLayout collapse_bar;
    private TextView titleView, bylineView;
    private Article article;
    private Toolbar toolbar;
    private FloatingActionButton shareButton;
    private PageSplitter pageSplitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_article_detail);

        setUpToolbar();

        findViewById(R.id.share_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(ArticleDetailActivity.this)
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)));
            }
        });

        collapse_bar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    collapse_bar.setTitle(article.getTITLE());
                    bylineView.setText("");
                    titleView.setText("");
                } else if (verticalOffset == 0) {
                    // Expanded
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    getSupportActionBar().setTitle(null);
                    collapse_bar.setTitle("");
                    titleView.setText(article.getTITLE());
                    Date publishedDate = Utility.parsePublishedDate(article.getPUBLISHED_DATE());
                    if (!publishedDate.before(START_OF_EPOCH.getTime())) {
                        bylineView.setText(Html.fromHtml(Utility.parseTitleBefore(article.getPUBLISHED_DATE(), article.getAUTHOR())));
                    } else {
                        bylineView.setText(Html.fromHtml(Utility.parseTitleAfter(article.getPUBLISHED_DATE(), article.getAUTHOR())));
                    }

                } else {
                    // Somewhere in between
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    getSupportActionBar().setTitle(null);
                    collapse_bar.setTitle("");
                    bylineView.setText("");
                    titleView.setText("");
                }
            }
        });
        int adapterPosition = getIntent().getExtras().getInt("adapterPosition");
        ArrayList<Article> articles = SharedUtil.getArticlesFromShared(this);
        article = articles.get(adapterPosition);
        setUpViewPager();

        titleView = (TextView) findViewById(R.id.article_title);
        bylineView = (TextView) findViewById(R.id.article_byline);
        bylineView.setMovementMethod(new LinkMovementMethod());

        mPhotoView = (ImageView) findViewById(R.id.photo);
        shareButton = (FloatingActionButton) findViewById(R.id.share_fab);

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                this.article = getIntent().getParcelableExtra("article");
            }
        }
    }

    private void setUpViewPager() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        mPager.setPageMarginDrawable(new ColorDrawable(0x22000000));
        mPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pageSplitter = new PageSplitter(mPager.getWidth(), mPager.getHeight(), 1, 0);

                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(getResources().getDimension(R.dimen.text_size));
                String[] splitText = Utility.getArticleBodyLinesCount(article.getBODY());
                for (int i = 0; i < splitText.length; i++) {
                    pageSplitter.append(splitText[i], textPaint);
                    textPaint.setFakeBoldText(true);
                    pageSplitter.append("! ", textPaint);
                    if ((i + 1) % 100 == 0) {
                        pageSplitter.append("\n", textPaint);
                    }
                }

                mPager.setAdapter(new ArticlePagerAdapter(getSupportFragmentManager(), pageSplitter.getPages()));
                mPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(article.getPHOTO_URL()).into(mPhotoView,
                PicassoPalette.with(article.getPHOTO_URL(), mPhotoView)
                        .use(PicassoPalette.Profile.MUTED_DARK)
                        .intoBackground(mPhotoView)
                        .intoBackground(shareButton)
                        .use(PicassoPalette.Profile.VIBRANT)
                        .intoTextColor(titleView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
        );

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ArticleDetailActivity.this, "Page " + ++position+" of "+pageSplitter.getPages().size(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
