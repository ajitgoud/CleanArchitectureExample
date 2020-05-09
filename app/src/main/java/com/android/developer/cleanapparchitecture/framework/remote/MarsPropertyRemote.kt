package com.android.developer.cleanapparchitecture.framework.remote

import android.os.Parcelable
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsPropertyRemote(
    val id: String,
    val imgSrcUrl: String,
    val type: String,
    val price: Double
) : Parcelable {
    constructor() : this("", "", "", 0.0)
}

fun List<MarsPropertyRemote>.asDomainModel(): List<MarsProperty> {
    return map {
        MarsProperty(
            id = it.id,
            imgSrcUrl = it.imgSrcUrl,
            type = it.type,
            price = it.price
        )
    }
}


fun MarsPropertyRemote.asDomainModel() = MarsProperty(
    id = id, imgSrcUrl = imgSrcUrl, type = type, price = price
)



