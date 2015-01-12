package com.shanghai.works.ate.snapnote.model;

import com.orm.SugarRecord;

/**
 * Created by fc on 15-1-6.
 */
public class SnapTag extends SugarRecord<SnapTag>{
    private String indexContent;
    private long date;
    private long noteId;

    public SnapTag(){

    }
}
