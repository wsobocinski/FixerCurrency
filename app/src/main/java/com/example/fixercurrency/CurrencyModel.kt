package com.example.fixercurrency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrencyModel(
    val symbol: String,
    val exchangeValue: String
) : Parcelable