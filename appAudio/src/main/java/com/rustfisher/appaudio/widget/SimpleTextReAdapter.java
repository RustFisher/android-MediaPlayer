package com.rustfisher.appaudio.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rustfisher.appaudio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Progress data adapter
 * Created by Rust on 2018/5/25.
 */
public class SimpleTextReAdapter extends RecyclerView.Adapter<SimpleTextReAdapter.VH> {

    private List<SimpleItem> mDataList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public SimpleTextReAdapter() {

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new VH(itemRoot);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final SimpleItem item = mDataList.get(position);
        holder.nameTv.setText(item.nameEn);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void replaceData(List<SimpleItem> list) {
        mDataList = new ArrayList<>(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * Fake data
     */
    public static class SimpleItem {
        public String nameEn;
        public String nameZh;

        public SimpleItem(String nameEn, String nameZh) {
            this.nameEn = nameEn;
            this.nameZh = nameZh;
        }

        @Override
        public String toString() {
            return "nameEn: " + nameEn + ", nameZh: " + nameZh;
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        View itemRoot;
        TextView nameTv;

        VH(View itemView) {
            super(itemView);
            itemRoot = itemView;
            nameTv = itemView.findViewById(R.id.item_simple_tv1);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SimpleItem item);
    }
}
