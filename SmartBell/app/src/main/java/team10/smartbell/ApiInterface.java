package team10.smartbell;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("order")
    Call<Boolean> order(@Query("name") String name);
}
