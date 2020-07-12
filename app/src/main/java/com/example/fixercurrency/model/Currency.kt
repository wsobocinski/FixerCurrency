package com.example.fixercurrency.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Currency(
    val symbol: String,
    val exchangeValue: String,
    val date: String
) : Parcelable