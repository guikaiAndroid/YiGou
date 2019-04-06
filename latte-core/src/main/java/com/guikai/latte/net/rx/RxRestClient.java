package com.guikai.latte.net.rx;

import android.content.Context;

import com.guikai.latte.net.HttpMethod;
import com.guikai.latte.net.RestClientBuilder;
import com.guikai.latte.net.RestCreator;
import com.guikai.latte.net.RestService;
import com.guikai.latte.net.callback.IError;
import com.guikai.latte.net.callback.IFailure;
import com.guikai.latte.net.callback.IRequest;
import com.guikai.latte.net.callback.ISuccess;
import com.guikai.latte.net.callback.RequestCallbacks;
import com.guikai.latte.net.download.DownloadHandler;
import com.guikai.latte.ui.loader.FragmentLoader;
import com.guikai.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Anding on 2019/1/13 17:50
 * Note: retrofit网络的封装:使用建造者模式
 */

public class RxRestClient {

    //创建一个网络请求需要的参数
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private Context CONTEXT;

    public RxRestClient(String url,
                        WeakHashMap<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            FragmentLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = RestCreator.getRxRestService().upload(URL, body);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get() {
       return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
        }
        return request(HttpMethod.POST_RAW);
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
        }
        return request(HttpMethod.PUT_RAW);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request((HttpMethod.UPLOAD));
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }
}
