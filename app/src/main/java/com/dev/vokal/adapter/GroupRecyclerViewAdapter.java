package com.dev.vokal.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dev.vokal.R;
import com.dev.vokal.application.VokalApplication;
import com.dev.vokal.design.VokalTextView;
import com.dev.vokal.model.DateItem;
import com.dev.vokal.model.GeneralItem;
import com.dev.vokal.model.ListItem;
import com.dev.vokal.model.SmsDataModel;
import com.dev.vokal.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> consolidatedList;

    public GroupRecyclerViewAdapter(List<ListItem> consolidatedList) {
        this.consolidatedList = consolidatedList;
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.layout_sms_recycler_view, parent, false);
                viewHolder = new GeneralViewHolder(v1);
                break;
            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.layout_date_header, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ListItem.TYPE_GENERAL:
                GeneralItem generalItem = (GeneralItem) consolidatedList.get(position);
                GeneralViewHolder generalViewHolder = (GeneralViewHolder) viewHolder;
                generalViewHolder.bindData(generalItem);
                break;
            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                dateViewHolder.bindDateData(dateItem);
                break;
        }
    }
}

class DateViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.dateTextView)
    VokalTextView dateTextView;

    DateViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, itemView);
    }

    void bindDateData(final DateItem dateItem) {
        int date = dateItem.getDate();
        dateTextView.setText(date + " hour ago");
    }
}

class GeneralViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.koinImageView)
    CircleImageView koinImageView;
    @BindView(R.id.phoneTextView)
    VokalTextView phoneTextView;
    @BindView(R.id.msgTextView)
    VokalTextView msgTextView;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;

    GeneralViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }

    void bindData(final GeneralItem generalItem) {
        SmsDataModel model = generalItem.getSmsDataModel();
        String phoneNumber = AppUtils.getInstance().getValueFromData(model.getNumber()).toString();
        String message = AppUtils.getInstance().getValueFromData(model.getBody()).toString();
        boolean shouldHighlight = model.isShouldHighLight();
        if (shouldHighlight) {
            parentLayout.setBackgroundColor(ContextCompat.getColor(VokalApplication.getAppContext(), R.color.colorShadow));
            new Handler().postDelayed(() -> parentLayout.setBackgroundDrawable(null), 3000);
        }
        phoneTextView.setText(phoneNumber);
        msgTextView.setText(message);
    }
}