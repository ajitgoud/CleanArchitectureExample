package com.android.developer.cleanapparchitecture.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.developer.cleanapparchitecture.domain.MarsProperty

@Entity(tableName = "mars_property")
data class MarsPropertyEntity(
    @PrimaryKey(autoGenerate = false) val id: String,

    @ColumnInfo(name = "image_source_url") val imgSrcUrl: String,

    @ColumnInfo(name = "property_type") val type: String,

    @ColumnInfo(name = "price") val price: Double = 0.0
)

fun List<MarsPropertyEntity>.asDomainModel(): List<MarsProperty> {
    return map {
        MarsProperty(
            id = it.id,
            imgSrcUrl = it.imgSrcUrl,
            type = it.type,
            price = it.price
        )
    }
}

fun List<MarsProperty>.asEntityModel(): List<MarsPropertyEntity> {
    return map {
        MarsPropertyEntity(
            id = it.id,
            imgSrcUrl = it.imgSrcUrl,
            type = it.type,
            price = it.price
        )
    }
}

fun MarsProperty.asEntityModel() = MarsPropertyEntity(
    id = id,
    imgSrcUrl = imgSrcUrl,
    type = type,
    price = price
)

fun MarsPropertyEntity.asDomainModel() = MarsProperty(
    id = id,
    imgSrcUrl = imgSrcUrl,
    type = type,
    price = price
)