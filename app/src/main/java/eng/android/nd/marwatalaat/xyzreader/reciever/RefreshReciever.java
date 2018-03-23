package eng.android.nd.marwatalaat.xyzreader.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import eng.android.nd.marwatalaat.xyzreader.data.UpdaterService;
import eng.android.nd.marwatalaat.xyzreader.ui.ArticleListActivity;

/**
 * Created by MarwaTalaat on 4/5/2017.
 */

public class RefreshReciever extends BroadcastReceiver {
    private boolean mIsRefreshing;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
            mIsRefreshing = intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING, false);
            ((ArticleListActivity)context).updateRefreshingUI(mIsRefreshing);
        }
    }
}
