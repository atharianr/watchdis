package com.atharianr.moviecatalogueapi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.atharianr.moviecatalogueapi.R
import com.atharianr.moviecatalogueapi.databinding.ActivityHomeBinding
import com.atharianr.moviecatalogueapi.ui.favorite.FavoriteActivity
import com.atharianr.moviecatalogueapi.utils.Constant
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MovieCatalogueAPI)
        setContentView(binding.root)

        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_purple)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(supportFragmentManager, this.lifecycle)

        binding.viewPager2.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.tabs,
            binding.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.btnMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.setOnMenuItemClickListener(this::onOptionsItemSelected)
            popup.inflate(R.menu.favorite_menu)
            popup.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, FavoriteActivity::class.java)

        when (item.itemId) {
            R.id.movies -> {
                intent.putExtra(Constant.TYPE, Constant.TYPE_MOVIE)
                startActivity(intent)
                return true
            }
            R.id.tv_shows -> {
                intent.putExtra(Constant.TYPE, Constant.TYPE_TV_SHOW)
                startActivity(intent)
                return true
            }
        }

        return false
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_show)
    }
}