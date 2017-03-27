package com.booboomx.hotvideo.net;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.booboomx.hotvideo.app.App;
import com.booboomx.hotvideo.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by booboomx on 17/3/27.
 */

public abstract class DownLoadSubscribe extends Subscriber<ResponseBody> {

    private static String TAG = "DownLoadSubscribe";
    private String mSaveFilePath;
    private File mFile;

    public DownLoadSubscribe(@NonNull String fileName) {
        mSaveFilePath = FileUtils.getCacheDir(App.getInstance()).getAbsolutePath();
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    public DownLoadSubscribe(@NonNull String filePath, @NonNull String fileName) {
        mSaveFilePath = filePath;
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    @Override
    public void onCompleted() {
        onCompleted(mFile);
    }

    public abstract void onCompleted(File file);

    @Override
    public void onError(Throwable e) {
        e.getStackTrace();
    }

    @Override
    public void onNext(final ResponseBody responseBody) {

    }

    public abstract void onProgress(double progress, long downloadByte, long totalByte);


    public File getFile() {
        return mFile;
    }

    Handler handler = new Handler(Looper.getMainLooper());
    long fileSizeDownloaded = 0;
    long fileSize = 0;
    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                fileSize = body.contentLength();


                inputStream = body.byteStream();
                outputStream = new FileOutputStream(getFile());

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(fileSizeDownloaded * 1.0f / fileSize, fileSizeDownloaded, fileSize);

                        }
                    });
                }

                outputStream.flush();

                return true;
            } catch (final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(e);
                    }
                });

                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (final IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
            return false;
        }
    }


}

