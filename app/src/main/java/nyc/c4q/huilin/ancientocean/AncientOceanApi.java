package nyc.c4q.huilin.ancientocean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AncientOceanApi {

    @GET("people")
    Call<List<People>> getDirectoryData();

//    @GET("people/1")
//    Call<People> getPersonInfo();
//
    @POST("people")
    Call<People> addPerson(@Query("id") String id, @Query("name") String name,
                           @Query("favoriteCity") String favoriteCity);

    @PUT("people")
    Call<People> updatePerson(@Query("id") String id, @Query("favoriteCity") String favoriteCity);

    @GET("people/1")
    Call<People> getPersonWithIdOne();

    @DELETE("people/{id}")
    Call<People> deletePerson(@Path("id") String id);
}
