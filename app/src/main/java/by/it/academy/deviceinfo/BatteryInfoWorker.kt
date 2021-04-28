package by.it.academy.deviceinfo

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat

class BatteryInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_ICON = R.drawable.ic_battery_info
        const val NOTIFICATION_TITLE = "Battery level"
        const val NOTIFICATION_TEXT = "Current battery level is "
    }

    override fun doWork(): Result {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            applicationContext.registerReceiver(null, ifilter)
        }

        val batteryPct: Int? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale
        }

        val notification = Notification()
        val bitmap = notification.vectorToBitmap(applicationContext, R.drawable.ic_battery_blue)

        notification.sendNotification(
            applicationContext,
            NOTIFICATION_ID,
            bitmap,
            NOTIFICATION_ICON,
            NOTIFICATION_TITLE,
            "$NOTIFICATION_TEXT$batteryPct%"
        )

        val sdf = SimpleDateFormat("HH:mm, dd-EEEE-yyyy")
        val date: String = sdf.format(java.util.Calendar.getInstance().time)

        Log.i("battery charge", "${batteryPct}, $date")

        return Result.success()
    }


}