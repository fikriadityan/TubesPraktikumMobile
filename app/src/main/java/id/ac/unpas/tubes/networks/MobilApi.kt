package id.ac.unpas.tubes.networks

import com.skydoves.sandwich.ApiResponse
import id.ac.unpas.tubes.model.Mobil
import retrofit2.http.*

interface MobilApi {
    @GET("api/mobil")
    suspend fun all(): ApiResponse<MobilGetResponse>
    @GET("api/mobil/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<MobilSingleGetResponse>
    @POST("api/mobil")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: Mobil):
            ApiResponse<MobilSingleGetResponse>
    @PUT("api/mobil/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: Mobil
    ): ApiResponse<MobilSingleGetResponse>
    @DELETE("api/mobil/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<MobilSingleGetResponse>
}