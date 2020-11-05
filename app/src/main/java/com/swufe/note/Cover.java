package com.swufe.note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.swufe.note.adapter.MyAdapter;
import com.swufe.note.bean.NoteBean;
import com.swufe.note.database.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cover extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private static final String TAG = "cover";
    ListView listView;
    private List<NoteBean> list;
    private DBManager dbManager;
    ArrayList listItems;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        listView = findViewById(R.id.mylist);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setEmptyView(findViewById(R.id.nodata));

        ImageView imageView=findViewById(R.id.add);
        initData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cover.this,Record.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initData() {
        dbManager = new DBManager(this);
        showQuery();
    }

    private void showQuery() {
        if(list!=null){
            list.clear();
        }
        list=dbManager.listAll();
        listItems = new ArrayList<HashMap<String, String>>();
        for(NoteBean nb:list){
            HashMap<String,String> hm =new HashMap<>();
            hm.put("CONTENT",nb.getContent());
            hm.put("TIME",nb.getTime());
            listItems.add(hm);
        }
        adapter= new MyAdapter(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);
//        Log.i(TAG, "onItemClick: titleStr=" + listItems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NoteBean noteBean =list.get(position);
        Intent intent=new Intent(Cover.this,Record.class);
        intent.putExtra("id",String.valueOf(noteBean.getId()));
        intent.putExtra("content",noteBean.getContent());
        Cover.this.startActivityForResult(intent,1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        AlertDialog dialog;
        AlertDialog.Builder builder=new AlertDialog.Builder(Cover.this)
                .setMessage("是否删除此记录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteBean notepadBean=list.get(position);
                        if(dbManager.delete(notepadBean.getId())){
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Cover.this,"删除成功",Toast.LENGTH_LONG).show();
                            showQuery();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog=builder.create();
        dialog.show();

        return true;
    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1&&resultCode==2){
            showQuery();
        }
    }
}