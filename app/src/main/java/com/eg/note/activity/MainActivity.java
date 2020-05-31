package com.eg.note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.eg.note.R;
import com.eg.note.bean.Note;
import com.eg.note.bean.NoteDao;
import com.eg.note.database.GreenDaoApplication;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_add;
    private ListView lv_note;

    private List<Note> noteList;
    private List<String> noteListData = new ArrayList<>();
    private ArrayAdapter<String> noteListAdapter;

    private NoteDao noteDao;

    private int REQUEST_CODE_ADD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addListeners();
        noteDao = GreenDaoApplication.getInstance().getDaoSession().getNoteDao();
        initNoteList();
    }

    /**
     * 绑定视图
     */
    private void initView() {
        btn_add = findViewById(R.id.btn_add);
        lv_note = findViewById(R.id.lv_note);
    }

    /**
     * 添加监听
     */
    private void addListeners() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    /**
     * 加载代办事项列表
     */
    private void initNoteList() {
        noteList = noteDao.queryBuilder().list();
        for (Note note : noteList) {
            noteListData.add(note.getContent());
        }
        noteListAdapter = new ArrayAdapter<String>(this, R.layout.item_note, noteListData);
        lv_note.setAdapter(noteListAdapter);
        lv_note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e("tag", "position: " + position + " noteList: " + noteList.size());
                Note note = noteList.get(position);
                final String noteContent = note.getContent();
                long noteId = note.getId();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.confirm_delete)
                        .setMessage(noteId + " : " + noteContent)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteDao.delete(noteList.get(position));
                                noteList.remove(position);
                                noteListData.remove(position);
                                noteListAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this,
                                        R.string.delete_finish + ": "
                                                + noteContent, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
                return true;
            }
        });
    }

    /**
     * 更新列表
     */
    private void updateNoteListView() {
        noteListData.clear();
        noteList = noteDao.queryBuilder().list();
        for (Note note : noteList) {
            noteListData.add(note.getContent());
        }
        noteListAdapter.notifyDataSetChanged();
    }

    /**
     * Activity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加新note后
        if (requestCode == REQUEST_CODE_ADD) {
            updateNoteListView();
        }
    }
}
