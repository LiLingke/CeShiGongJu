package com.example.administrator.ceshigongju.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/3/19.
 */
@Entity
public class NoteEntity {
    @Id(autoincrement = true)
    private Long id;

    private String time;
    private int sCount;
    private int fCount;
    @Generated(hash = 606683921)
    public NoteEntity(Long id, String time, int sCount, int fCount) {
        this.id = id;
        this.time = time;
        this.sCount = sCount;
        this.fCount = fCount;
    }
    @Generated(hash = 734234824)
    public NoteEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getSCount() {
        return this.sCount;
    }
    public void setSCount(int sCount) {
        this.sCount = sCount;
    }
    public int getFCount() {
        return this.fCount;
    }
    public void setFCount(int fCount) {
        this.fCount = fCount;
    }
}
