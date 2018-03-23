package eng.android.nd.marwatalaat.xyzreader.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;

import java.util.ArrayList;
import java.util.List;

import eng.android.nd.marwatalaat.xyzreader.adapter.ArticleAdapter;
import eng.android.nd.marwatalaat.xyzreader.data.ArticleLoader;
import eng.android.nd.marwatalaat.xyzreader.model.Article;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListArticleFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {


    private RecyclerView mRecyclerView;
    private ArrayList<Article> articles;
    private View view;

    public ListArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_article, container, false);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            ((ArticleListActivity) getActivity()).refresh();
        }
        return view;
    }

    private void setUpRecyclerView(ArrayList<Article> articles) {
        ArticleAdapter adapter = new ArticleAdapter(getActivity(), articles);
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(sglm);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        articles = Article.getArticleData(data);
        setUpRecyclerView(articles);
        ((ArticleListActivity) getActivity()).updateRefreshingUI(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }
    public interface Callback {
        void passArticle(Article article);
    }
}
