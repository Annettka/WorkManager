package by.it.academy.deviceinfo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceInfoWorkRequest =
            PeriodicWorkRequestBuilder<DeviceInfoWorker>(1, TimeUnit.HOURS, 58, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.HOURS)
                .build()

        val button = findViewById<Button>(R.id.device_info)
        button.setOnClickListener {

            WorkManager.getInstance(this)
                .enqueue(deviceInfoWorkRequest)

        }
    }

}