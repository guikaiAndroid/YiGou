package com.guikai.latte.net.download;

import android.os.AsyncTask;

import com.guikai.latte.net.callback.IRequest;
import com.guikai.latte.net.callback.ISuccess;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... objects) {

        String downloadDor = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[3];
        final InputStream is = body.byteStream();
        if (downloadDor == null || downloadDor.equals("")) {
            downloadDor = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {

        }

        return null;
    }
}
