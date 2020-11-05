package com.swufe.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.swufe.note.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdatper";
    ListView listView;

    public MyAdapter(Context context, int resource,
                     ArrayList<HashMap<String, String>> list) {
        super(context, resource, list);
    }




    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View itemView = converView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView time = (TextView) itemView.findViewById(R.id.itemTime);

        title.setText(map.get("CONTENT"));
        time.setText(map.get("TIME"));

        return itemView;
    }
}
