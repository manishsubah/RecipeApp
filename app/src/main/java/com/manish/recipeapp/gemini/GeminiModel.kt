package com.manish.recipeapp.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig

/**Access API key from BuildConfig file
 * You can create a apiKey variable in local.properties file and enable buildConfig in you app level build.gradle file*/
val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = "AIzaSyAOar-1Lu9p4YgQlVK0j8pJxzTkkgh4f7U",
    generationConfig =  GenerationConfig.builder().apply { temperature = 1F }.build()
)
