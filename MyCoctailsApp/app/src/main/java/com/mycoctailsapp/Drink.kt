package com.mycoctailsapp


import android.os.Parcel
import android.os.Parcelable



data class Drink(
    val strDrink: String,
    val strCategory: String,
    val strDrinkThumb: String,
    val strInstructions: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strDrink)
        parcel.writeString(strCategory)
        parcel.writeString(strDrinkThumb)
        parcel.writeString(strInstructions)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink = Drink(parcel)
        override fun newArray(size: Int): Array<Drink?> = arrayOfNulls(size)
    }
}

data class DrinksResponse(val drinks: List<Drink>)
