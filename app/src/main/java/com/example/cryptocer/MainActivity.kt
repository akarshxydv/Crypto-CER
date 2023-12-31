package com.example.cryptocer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cryptocer.viewModel.coinList.CoinListViewModel
import com.example.cryptocer.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: CoinAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var swipeLayout: SwipeRefreshLayout
    private val coinListViewModel:CoinListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeLayout = findViewById(R.id.swipeLayout)
        recyclerView = findViewById(R.id.coinRecyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //setUpTheRecyclerView()

       coinListViewModel.getAllCoin(Constant.apiKey)



        lifecycleScope.launch (Dispatchers.Main) {

            coinListViewModel._coinValue.collect{
                when{
                    it.isLoading->{

                    }
                    it.error.isNotBlank()->{
                        Log.d("AKA",it.error.toString())
                    }
                    it.coinDetail.isNotEmpty()->{
                        Log.d("AKA",it.coinDetail[1].toString())
                        myAdapter = CoinAdapter(it.coinDetail)
                        recyclerView.addItemDecoration(
                            DividerItemDecoration(
                                baseContext,
                                layoutManager.orientation
                            )
                        )
                        recyclerView.adapter = myAdapter
                    }
                }
            }

        }






//            val retrofit = Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(CoinLayerApi::class.java)
//
//          val apicall=retrofit.getCoinRate(apiKey)
//        val apicall2=retrofit.getAllCoin(apiKey)


//        apicall.enqueue(object : Callback<CoinRate> {
//            override fun onResponse(call: Call<CoinRate>, response: Response<CoinRate>) {
//                if (response.isSuccessful) {
//                    val coinList = response.body()
//                    // Process the coinList here
//                    if (coinList != null) {
//                        Log.d("AKA",coinList.toString())
//                    }else{
//                        Log.d("AKAM","no data")
//                    }
//                } else {
//                    // Handle the error
//                    Log.d("AKAT","no response")
//                }
//            }

//            override fun onFailure(call: Call<CoinRate>, t: Throwable) {
//                // Handle the failure
//                Log.d("AKAF",t.message.toString())
//            }
//        })


//        apicall2.enqueue(object : Callback<CoinListDto> {
//            override fun onResponse(call: Call<CoinListDto>, response: Response<CoinListDto>) {
//                if (response.isSuccessful) {
//                    val coinList = response.body()?.crypto
//                    // Process the coinList here
//                    if (coinList != null) {
//                        Log.d("AKA",coinList.toString())
//                    }else{
//                        Log.d("AKAM","no data")
//                    }
//                } else {
//                    // Handle the error
//                    Log.d("AKAT","no response")
//                }
//            }
//
//            override fun onFailure(call: Call<CoinListDto>, t: Throwable) {
//                // Handle the failure
//                Log.d("AKAF",t.message.toString())
//            }
//        })




    }
//    private fun setUpTheRecyclerView() {
//        coinRecyclerView=findViewById(R.id.coinRecyclerView)
//        coinAdapter = CoinAdapter(this@MainActivity, ArrayList())
//        coinRecyclerView.adapter = coinAdapter
//        coinRecyclerView.layoutManager = layoutManager
////        binding.coinRecyclerView.addItemDecoration(
////            DividerItemDecoration(
////                binding.coinRecyclerView.context,
////                (GridLayoutManager(this, 1)).orientation
////            )
////        )
//    }
}
