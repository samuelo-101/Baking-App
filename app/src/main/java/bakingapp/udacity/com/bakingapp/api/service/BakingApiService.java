package bakingapp.udacity.com.bakingapp.api.service;

import java.util.List;

import bakingapp.udacity.com.bakingapp.api.model.Recipe;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface BakingApiService {

    @GET("baking.json")
    Single<Response<List<Recipe>>> fetchRecipes();

}
