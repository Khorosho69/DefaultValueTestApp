package com.test.antont.testapp.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.test.antont.testapp.R;
import com.test.antont.testapp.databases.DBHelper;
import com.test.antont.testapp.models.AppInfo;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<AppInfo> mDataset;
    private Context mContext;

    public RecyclerViewAdapter(List<AppInfo> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mCheckBox.setText(mDataset.get(position).getAppName());
        holder.mCheckBox.setChecked(mDataset.get(position).getStatus());
        holder.mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> onCheckedChanged(position, b, compoundButton));

    }

    private void onCheckedChanged(int position, Boolean status, CompoundButton compoundButton) {
        if (!compoundButton.isPressed()) {
            return;
        }
        AppInfo newItem = new AppInfo(mDataset.get(position).getPackageName(), mDataset.get(position).getAppName(), status);
        mDataset.set(position, newItem);
        DBHelper mDBHelper = new DBHelper(mContext);
        SQLiteDatabase mDataBase = mDBHelper.getWritableDatabase();
        mDBHelper.updateAppInfo(mDataBase, newItem);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;

        ViewHolder(View v) {
            super(v);
            mCheckBox = v.findViewById(R.id.appItemCheckBox);
        }
    }
}