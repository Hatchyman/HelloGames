package fr.epita.hellogames

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    var gameInfo : ArrayList<GameDetail> = arrayListOf<GameDetail>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message = intent.getStringExtra("ID")

        // Capture the layout's TextView and set the string as its text


        val data = arrayListOf<GameDetail>()
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl("https://androidlessonsapi.herokuapp.com/api/")
                .addConverterFactory(jsonConverter)
                .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<GameDetail> {
            override fun onFailure(call: Call<GameDetail>?, t: Throwable?) {
                Log.d("TAG", "Error ")
            }

            override fun onResponse(call: Call<GameDetail>?, response: Response<GameDetail>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            gameInfo.add(responseData)
                            updateFields()
                            data.add(responseData)
                            Log.d("TAG", "WebService success : " + data.size)
                        }
                    }
                }
            }

        }

        service.getDetail(message.toInt()).enqueue(callback)

    }

    fun updateFields()
    {
        val textName = findViewById<TextView>(R.id.name_value)
        val textType = findViewById<TextView>(R.id.type_value)
        val textNb  = findViewById<TextView>(R.id.players_value)
        val textYear = findViewById<TextView>(R.id.year_value)
        val textDescription = findViewById<TextView>(R.id.description)
        val image = findViewById<ImageView>(R.id.imageGame)
        textName.append(" " + gameInfo[0].name)
        textType.append(" " + gameInfo[0].type)
        textNb.append(" " + gameInfo[0].players.toString())
        textYear.append(" " + gameInfo[0].year.toString())
        textDescription.text = gameInfo[0].description_en
        SetImageGame(image)
        val btnUrl = findViewById<Button>(R.id.btn)
        btnUrl.setOnClickListener(this@SecondActivity)
    }

    private fun SetImageGame(imageView: ImageView)
    {
        when (gameInfo[0].id)
        {
            1 -> imageView.setImageResource(R.drawable.tic_tac_toe)
            2 -> imageView.setImageResource(R.drawable.hangman_game)
            3 -> imageView.setImageResource(R.drawable.sudoku)
            4 -> imageView.setImageResource(R.drawable.battleship)
            5 -> imageView.setImageResource(R.drawable.minesweeper)
            6 -> imageView.setImageResource(R.drawable.conways_game_of_life)
            7 -> imageView.setImageResource(R.drawable.memory)
            8 -> imageView.setImageResource(R.drawable.simon)
            9 -> imageView.setImageResource(R.drawable.jigsaw)
            10 -> imageView.setImageResource(R.drawable.mastermind)
        }

    }


    override fun onClick(clickedView: View?) {

        if (clickedView != null)
        {
            when (clickedView.id)
            {
                R.id.btn ->
                {
                    val url = gameInfo[0].url
                    val intent = Intent(Intent.ACTION_VIEW)

                    intent.data = Uri.parse(url)

                    startActivity(intent)
                }
            }
        }
    }
}
