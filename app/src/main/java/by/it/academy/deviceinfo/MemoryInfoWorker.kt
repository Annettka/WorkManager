package by.it.academy.deviceinfo

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class MemoryInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    companion object {
        const val NOTIFICATION_ID = 2
        const val NOTIFICATION_ICON = R.drawable.ic_memory
        const val NOTIFICATION_TITLE = "Available device memory"
        const val NOTIFICATION_TEXT = "Available memory is "
    }

    val context = context
    override fun doWork(): Result {

        val memoryInfo = ActivityManager.MemoryInfo()
        val activityManager: ActivityManager =
            context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)
        val availMem = (memoryInfo.availMem) / (1024 * 1024)

        val notification = Notification()
        val bitmap = notification.vectorToBitmap(applicationContext, R.drawable.ic_memory_blue_round)

        notification.sendNotification(
            applicationContext,
            NOTIFICATION_ID,
            bitmap,
            NOTIFICATION_ICON,
            NOTIFICATION_TITLE,
            "$NOTIFICATION_TEXT${availMem}MB"
        )
        Log.i("Memory", "Free RAM= $availMem MB")

        return Result.success()
    }
}