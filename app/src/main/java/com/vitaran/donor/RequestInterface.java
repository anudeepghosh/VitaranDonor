package com.vitaran.donor;

import com.vitaran.donor.models.ServerRequest;
import com.vitaran.donor.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("vitaran/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
