package com.example.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.retrofittest.entity.FileUploadResponseVo;
import com.example.retrofittest.network.NetworkService;
import com.example.retrofittest.retrofit.HttpMethod;
import com.example.retrofittest.rxjava.BaseObserver;
import com.example.retrofittest.rxjava.RxSchedulers;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = HttpMethod.getInstance().create(NetworkService.class);

//        upLoadPic();
    }

    /**
     * 上传图片
     */
    private void upLoadPic() {
        String userId="";
        String pathFile="";
        String picType = "USER_AVATAR";

        File file = new File(pathFile);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pic",file.getName(),requestFile);
        service.upLoadAvatar(toRequestBody(userId),toRequestBody(picType),part)
                .compose(RxSchedulers.<FileUploadResponseVo>compose(getApplicationContext()))
                .subscribe(new BaseObserver<FileUploadResponseVo>(getApplicationContext()) {
                    @Override
                    public void onSuccess(FileUploadResponseVo fileUploadResponseVo) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }


    /**
     * 字符串转RequestBody，用于retrofit图文上传
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

}
