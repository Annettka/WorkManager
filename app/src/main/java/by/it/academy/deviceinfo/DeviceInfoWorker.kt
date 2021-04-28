package by.it.academy.deviceinfo

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class DeviceInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        val batteryInfoWorkRequest =
            OneTimeWorkRequestBuilder<BatteryInfoWorker>()
                .build()
        val memoryInfoWorkRequest =
            OneTimeWorkRequestBuilder<MemoryInfoWorker>()
                .setInitialDelay(4, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance()
            .beginWith(batteryInfoWorkRequest)
            .then(memoryInfoWorkRequest)
            .enqueue()

        return Result.success()
    }
}