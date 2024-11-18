package com.manish.recipeapp.navigation

import androidx.annotation.StringDef

@StringDef(Route.DASHBOARD, Route.CREATE_RECIPE, Route.IDENTIFY_MEAL)
annotation class RouteValue

object Route {
    const val DASHBOARD = "dashboard"
    const val CREATE_RECIPE = "create_recipe"
    const val IDENTIFY_MEAL = "identify_meal"
}

sealed class NavigationScreen(@RouteValue val route: String) {
    data object Dashboard : NavigationScreen(Route.DASHBOARD)
    data object CreateRecipe : NavigationScreen(Route.CREATE_RECIPE)
    data object IdentifyMeal : NavigationScreen(Route.IDENTIFY_MEAL)
}

