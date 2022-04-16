package com.atharianr.moviecatalogueapi.ui.detail

import com.atharianr.moviecatalogueapi.data.source.local.entity.DetailEntity

interface DetailActivityCallback {
    fun onShareClick(data: DetailEntity, type: Int)
}