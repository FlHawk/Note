package com.swufe.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.swufe.note.bean.NoteBean;
import com.swufe.note.database.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record extends AppCompatActivity implements View.OnClickListener {
    ImageView delete;
    ImageView note_save;
    EditText content;
    private String id;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        content=(EditText) findViewById(R.id.content);//记录的内容
        delete=(ImageView)findViewById(R.id.delete);//清空的按钮
        note_save=(ImageView)findViewById(R.id.note_save);//保存的按钮
        dbManager = new DBManager(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            if (id != null) {
                content.setText(intent.getStringExtra("content"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete:
                content.setText(" ");
                break;
            case R.id.note_save:
                String noteContent =content.getText().toString().trim();
                if(id!=null){
                    //修改记录的功能
                    if(noteContent.length()>0){
                        String text = content.getText().toString();
                        NoteBean bean = new NoteBean(text,NoteBean.getrealTime());
                        bean.setId(Integer.parseInt(id));
                        if (dbManager.update(bean)){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("修改失败");
                        }
                    } else{
                        showToast("修改的记录内容不能为空");
                    }
                }else{
                    //添加记录的功能
                    if(noteContent.length()>0){
                        String text = content.getText().toString();
                        NoteBean bean = new NoteBean(text,NoteBean.getrealTime());
                        if (dbManager.add(bean)){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("保存失败");
                        }
                    } else{
                        showToast("保存的记录内容不能为空");
                    }
                }
                break;
        }
    }
    public void showToast(String message){
        Toast.makeText(Record.this,message,Toast.LENGTH_LONG).show();
    }
}