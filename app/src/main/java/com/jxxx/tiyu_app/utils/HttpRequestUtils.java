package com.jxxx.tiyu_app.utils;

import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.Result;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpRequestUtils {
    public static void uploadFiles(String filePath,String version, UploadFileInterface fileInterface) {
        if(StringUtil.isBlank(filePath)){
            fileInterface.succeed("-1");
            return;
        }
        File file = new File(filePath);
        String data = StringUtil.getTimeToYMD(System.currentTimeMillis(),"yyyy-MM-dd");
        Map<String, RequestBody> map = new HashMap<>();
        map.put("clientType", toRequestBody("1"));
        map.put("userId", toRequestBody(SharedUtils.getUserId()));
        map.put("version", toRequestBody(version));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的name是用file
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        MultipartBody.Part[] ba =  {MultipartBody.Part.createFormData("files", file.getName(), requestFile)};
        RetrofitUtil.getInstance().apiService()
                .submitFiles(body, map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Result result) {
                        if (result.getCode() == 200) {
                            fileInterface.succeed(result.getData().toString());
                        }else{
                            fileInterface.failure();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        fileInterface.failure();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
    /**
     * 创建请求体
     *
     * @param value
     * @return
     */
    public static  RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }
    public interface UploadFileInterface{
        void succeed(String path);
        void failure();
    }
}
