package pl.braincode.heimdall.services;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.braincode.heimdall.models.ResultItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BifrostAPI {

    @POST("imagetest")
    Call<List<ResultItem>> sendImage(@Body RequestBody bytes);

    @GET("tescik")
    Call<ResponseBody> tescik();

    @POST("image")
    Call<ResponseBody> sendImage2(@Body RequestBody bytes);

}
