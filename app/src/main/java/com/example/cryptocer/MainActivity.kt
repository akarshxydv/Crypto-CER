package com.example.cryptocer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: CoinAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var showTime: TextView
    private val coinListViewModel: CoinListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showTime=findViewById(R.id.timeShow)
        swipeLayout = findViewById(R.id.swipeLayout)
        recyclerView = findViewById(R.id.coinRecyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        coinListViewModel.getAllCoin(Constant.apiKey)

        setUpSwipeToRefresh()
        observeCoinList()

    }
    private fun setUpSwipeToRefresh() {
        swipeLayout.setOnRefreshListener {

            coinListViewModel.getAllCoin(Constant.apiKey)

        }



        val autoRefreshInterval = 3 * 60 * 1000L
        val autoRefreshHandler = Handler()
        val autoRefreshRunnable = object : Runnable {
            override fun run() {
                coinListViewModel.getAllCoin(Constant.apiKey)
                autoRefreshHandler.postDelayed(this, autoRefreshInterval)
            }
        }
        autoRefreshHandler.postDelayed(autoRefreshRunnable, autoRefreshInterval)
    }

    private fun observeCoinList() {
        lifecycleScope.launch(Dispatchers.Main) {
            coinListViewModel._coinValue.collect {
                when {
                    it.isLoading -> {
                        swipeLayout.isRefreshing = true
                    }

                    it.error.isNotBlank() -> {
                        swipeLayout.isRefreshing = false
                        Log.d("AKA", it.error)
                    }

                    it.coinDetail.isNotEmpty() -> {
                        swipeLayout.isRefreshing = false
                        myAdapter = CoinAdapter(it.coinDetail)
                        recyclerView.addItemDecoration(
                            DividerItemDecoration(
                                baseContext,
                                layoutManager.orientation
                            )
                        )
                        recyclerView.adapter = myAdapter


                        showTime.text = "Last Refresh: ${getCurrentTime()}"
                    }
                }
            }
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
