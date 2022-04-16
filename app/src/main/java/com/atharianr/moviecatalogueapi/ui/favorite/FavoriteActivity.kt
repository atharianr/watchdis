package com.atharianr.moviecatalogueapi.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.moviecatalogueapi.R
import com.atharianr.moviecatalogueapi.databinding.ActivityFavoriteBinding
import com.atharianr.moviecatalogueapi.utils.Constant
import com.atharianr.moviecatalogueapi.utils.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MovieCatalogueAPI)
        setContentView(binding.root)

        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        val view = window.decorView
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = true

        val type = intent.getIntExtra(Constant.TYPE, 0)
        val favoriteAdapter = FavoriteAdapter()

        viewModel.setType(type)
        viewModel.getFavorite().observe(this) {
            Log.d("FavoriteActivity", it.toString())
            if (it != null) {
                if (it.isNotEmpty()) {
                    favoriteAdapter.setData(it)
                    binding.tvEmpty.visibility = View.GONE
                    with(binding.rvFavorite) {
                        this.visibility = View.VISIBLE
                        this.layoutManager = LinearLayoutManager(context)
                        this.setHasFixedSize(true)
                        this.adapter = favoriteAdapter
                    }
                } else {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                }
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        when (type) {
            Constant.TYPE_MOVIE -> binding.customTitle.text = getString(R.string.favorite_movies)
            Constant.TYPE_TV_SHOW -> binding.customTitle.text =
                getString(R.string.favorite_tv_shows)
        }
    }
}