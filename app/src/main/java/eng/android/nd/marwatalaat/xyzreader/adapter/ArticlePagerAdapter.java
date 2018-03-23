package eng.android.nd.marwatalaat.xyzreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import eng.android.nd.marwatalaat.xyzreader.ui.ArticleDetailFragment;


/**
 * Created by MarwaTalaat on 4/6/2017.
 */

public class ArticlePagerAdapter extends FragmentStatePagerAdapter {
    private List<CharSequence> pageTexts;

    public ArticlePagerAdapter(FragmentManager fm, List<CharSequence> pageTexts) {
        super(fm);
        this.pageTexts = pageTexts;
    }

    @Override
    public Fragment getItem(int i) {
        return ArticleDetailFragment.newInstance(pageTexts.get(i));
    }

    @Override
    public int getCount() {
        return pageTexts.size();
    }
}
