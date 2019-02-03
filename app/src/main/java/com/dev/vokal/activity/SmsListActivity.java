package com.dev.vokal.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.dev.vokal.R;
import com.dev.vokal.adapter.GroupRecyclerViewAdapter;
import com.dev.vokal.design.EndLessScrollListener;
import com.dev.vokal.model.DateItem;
import com.dev.vokal.model.GeneralItem;
import com.dev.vokal.model.ListItem;
import com.dev.vokal.model.SmsDataModel;
import com.dev.vokal.utils.AppUtils;
import com.dev.vokal.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SmsListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private EndLessScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SmsListActivityPermissionsDispatcher.canReadWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void canRead() {
        getSmsData(getIntent());
    }

    @OnPermissionDenied({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void deniedPermissionsFlow() {
        SmsListActivityPermissionsDispatcher.canReadWithPermissionCheck(this);
    }

    @OnNeverAskAgain({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void onNeverAskAgain() {
        AppUtils.getInstance().createAndShowDialog(pContext);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getSmsData(intent);
    }

    private void getSmsData(Intent intent) {
        boolean highlight = false;
        if (intent != null) {
            if (intent.hasExtra(Constants.KEY_NEW_SMS)) {
                highlight = intent.getBooleanExtra(Constants.KEY_NEW_SMS, false);
            }
        }
        long last24Hours = new Date(System.currentTimeMillis() - 24 * 3600 * 1000).getTime();
        Handler handler = new Handler();
        boolean finalHighlight = highlight;
        handler.post(() -> {
            List<SmsDataModel> smsList = new ArrayList<>();
            Uri uri = Uri.parse(Constants.KEY_SMS_URI);
            Cursor c = getContentResolver().query(uri,
                    new String[]{Constants.KEY_SMS_ID, Constants.KEY_SMS_ADDRESS, Constants.KEY_SMS_DATE,
                            Constants.KEY_SMS_BODY}, Constants.KEY_QUERY_DATE,
                    new String[]{"" + last24Hours}, Constants.KEY_SORT_ORDER);
            if (c != null) {
                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {
                        SmsDataModel sms = new SmsDataModel();
                        if (finalHighlight && i == 0) {
                            sms.setShouldHighLight(finalHighlight);
                        }
                        sms.setBody(c.getString(c.getColumnIndexOrThrow(Constants.KEY_SMS_BODY)));
                        long milliseconds = c.getLong(c.getColumnIndexOrThrow(Constants.KEY_SMS_DATE));
                        sms.setDate(milliseconds);
                        sms.setNumber(c.getString(c.getColumnIndexOrThrow(Constants.KEY_SMS_ADDRESS)));
                        smsList.add(sms);
                        c.moveToNext();
                    }
                }
                c.close();
            }
            TreeMap<Integer, List<SmsDataModel>> map = AppUtils.getInstance().getHourDataFromSmsList(smsList);
            if (map != null) {
                List<ListItem> consolidatedList = new ArrayList<>();
                for (int date : map.keySet()) {
                    DateItem dateItem = new DateItem();
                    dateItem.setDate(date);
                    consolidatedList.add(dateItem);
                    for (SmsDataModel model : Objects.requireNonNull(map.get(date))) {
                        GeneralItem generalItem = new GeneralItem();
                        generalItem.setSmsDataModel(model);
                        consolidatedList.add(generalItem);
                    }
                }
                int resId = R.anim.layout_anim_fall_down;
                GroupRecyclerViewAdapter adapter = new GroupRecyclerViewAdapter(consolidatedList);
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.setLayoutManager(new LinearLayoutManager(SmsListActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        SmsListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
