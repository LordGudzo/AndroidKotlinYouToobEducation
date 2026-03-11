package com.mycoctailsapp

sealed class View(val route: String) {

    object MainCocktailView: View("maincocktailview")
    object DrinkDetailView: View("drinkdetailview")
}