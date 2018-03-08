package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameList : ArrayList<Game> = arrayListOf<Game>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf<Game>()
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl("https://androidlessonsapi.herokuapp.com/api/")
                .addConverterFactory(jsonConverter)
                .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
                Log.d("TAG", "Error ")
            }

            override fun onResponse(call: Call<List<Game>>?, response: Response<List<Game>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {

                            Collections.shuffle(responseData)
                            gameList.add(responseData[0])
                            gameList.add(responseData[1])
                            gameList.add(responseData[2])
                            gameList.add(responseData[3])
                            updateGame()

                            data.addAll(responseData)
                            Log.d("TAG", "WebService success : " + data.size)
                        }
                    }
                }
            }

        }

        service.listGames().enqueue(callback)


    }

    fun updateGame()
    {
        val btnGame1 = findViewById<ImageButton>(R.id.game1)
        val btnGame2 = findViewById<ImageButton>(R.id.game2)
        val btnGame3 = findViewById<ImageButton>(R.id.game3)
        val btnGame4 = findViewById<ImageButton>(R.id.game4)

        SetImageGame(btnGame1, 0)
        SetImageGame(btnGame2, 1)
        SetImageGame(btnGame3, 2)
        SetImageGame(btnGame4, 3)

        btnGame1.setOnClickListener(this@MainActivity)
        btnGame2.setOnClickListener(this@MainActivity)
        btnGame3.setOnClickListener(this@MainActivity)
        btnGame4.setOnClickListener(this@MainActivity)

    }

    private fun SetImageGame(imageButton: ImageButton, pos: Int)
    {
        when (gameList[pos].id)
        {
            1 -> imageButton.setImageResource(R.drawable.tic_tac_toe)
            2 -> imageButton.setImageResource(R.drawable.hangman_game)
            3 -> imageButton.setImageResource(R.drawable.sudoku)
            4 -> imageButton.setImageResource(R.drawable.battleship)
            5 -> imageButton.setImageResource(R.drawable.minesweeper)
            6 -> imageButton.setImageResource(R.drawable.conways_game_of_life)
            7 -> imageButton.setImageResource(R.drawable.memory)
            8 -> imageButton.setImageResource(R.drawable.simon)
            9 -> imageButton.setImageResource(R.drawable.jigsaw)
            10 -> imageButton.setImageResource(R.drawable.mastermind)
        }

    }

   private fun sendIdGame(id:Int)
    {
        val intent = Intent(this@MainActivity, SecondActivity::class.java)
        intent.putExtra("ID", id.toString())
        startActivity(intent)
    }

    override fun onClick(clickedView: View?) {
        if (clickedView != null) {
            when (clickedView.id) {
                R.id.game1 -> {
                    sendIdGame(gameList[0].id)
                }

                R.id.game2 -> {
                    sendIdGame(gameList[1].id)
                }



                R.id.game3 -> {

                    sendIdGame(gameList[2].id)
                }

                R.id.game4 -> {
                    sendIdGame(gameList[3].id)
                }
            }
        }
    }
}

