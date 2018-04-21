package pl.braincode.heimdall.services;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.braincode.heimdall.models.ResultItem;
import pl.braincode.heimdall.models.SearchPhrase;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BifrostAPI {

    @POST("imagetest")
    Call<SearchPhrase> sendImage(@Body RequestBody bytes);

    @POST("tags")
    Call<ArrayList<ResultItem>> getResults(@Body SearchPhrase phrase);

//    @GET("tescik")
//    Call<ResponseBody> tescik();

//    @POST("image")
//    Call<ResponseBody> sendImage2(@Body RequestBody bytes);

}
