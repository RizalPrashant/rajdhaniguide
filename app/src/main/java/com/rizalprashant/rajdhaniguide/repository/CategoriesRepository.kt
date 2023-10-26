package com.rizalprashant.rajdhaniguide.repository

import androidx.lifecycle.LiveData
import com.rizalprashant.rajdhaniguide.data.CategoriesDao
import com.rizalprashant.rajdhaniguide.model.Categories

class CategoriesRepository(private val categoriesDao: CategoriesDao) {

    val readAllDataCategory: LiveData<List<Categories>> = categoriesDao.readAllData()

    suspend fun addCategory(categories: Categories){
        categoriesDao.addCategory(categories)
    }

}