package fr.epita.hellogames

import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hp on 07/03/18.
 */

interface WebServiceInterface
{
    @GET("game/list")
    fun listGames(): retrofit2.Call<List<Game>>

    @GET("game/details")

    fun getDetail(@Query ("game_id") gameId: Int): retrofit2.Call<GameDetail>
}