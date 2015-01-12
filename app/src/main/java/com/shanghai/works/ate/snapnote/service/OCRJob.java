package com.shanghai.works.ate.snapnote.service;

import android.net.Uri;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

/**
 * Created by fc on 15-1-7.
 */
public class OCRJob extends Job {

    public static final int LOW_PRIORITY = 1;
    public Uri imagePath;

    public OCRJob(Uri imagePath){
        super(new Params(LOW_PRIORITY));
        this.imagePath = imagePath;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        //TODO run ocr job

    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
