package com.eg.note.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "note")
public class Note {
    @Id(autoincrement = true)
    private Long id;
    private String content;

    @Generated(hash = 1364909349)
    public Note(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
