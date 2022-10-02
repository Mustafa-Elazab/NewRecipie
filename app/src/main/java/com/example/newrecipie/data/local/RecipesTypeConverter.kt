package com.example.newrecipie.data.local

import androidx.room.TypeConverter
import com.example.newrecipie.model.MealDetails


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    var gson = Gson()



    @TypeConverter
    fun resultToString(result: MealDetails): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): MealDetails {
        val listType = object : TypeToken<MealDetails>() {}.type
        return gson.fromJson(data, listType)
    }

}