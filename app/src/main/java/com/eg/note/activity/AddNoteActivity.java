package com.eg.note.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eg.note.R;
import com.eg.note.bean.Note;
import com.eg.note.bean.NoteDao;
import com.eg.note.database.GreenDaoApplication;

public class AddNoteActivity extends AppCompatActivity {

    private TextView et_note;
    private Button btn_save;

    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initView();
        addListeners();
        noteDao = GreenDaoApplication.getInstance().getDaoSession().getNoteDao();
    }

    private void initView() {
        et_note = findViewById(R.id.et_note);
        et_note.requestFocus();
        btn_save = findViewById(R.id.btn_save);
    }

    private void addListeners() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_note.getText().toString();
                if (content.equals("") == false) {
                    Note note = new Note();
                    note.setContent(content);
                    noteDao.save(note);
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "请输入内容！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
