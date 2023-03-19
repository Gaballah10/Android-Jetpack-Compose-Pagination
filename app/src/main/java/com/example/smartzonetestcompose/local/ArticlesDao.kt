package com.example.smartzonetestcompose.local


import androidx.room.*
import com.example.smartzonetestcompose.network.model.Article
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article): Completable

    @Query("DELETE FROM article_table")
    fun deleteAllArticles(): Completable

    @Query("DELETE FROM article_table WHERE title = :title")
    fun deleteArticleItem(title: String): Completable

    @Query("SELECT * FROM article_table")
    fun getAllArticles(): Single<List<Article>>


}