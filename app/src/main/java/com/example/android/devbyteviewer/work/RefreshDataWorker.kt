package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by suryamudti on 02/10/2019.
 */
class RefreshDataWorker(
        appContext: Context,
        params : WorkerParameters
) : CoroutineWorker(appContext,params) {

    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {

        val db = getDatabase(applicationContext)
        val repository = VideosRepository(db)

        try {
            repository.refreshVideos()
            Timber.d("Work request for sync is run")
        }catch (e:HttpException){
            return Result.retry()
        }

        return Result.success()
    }
}