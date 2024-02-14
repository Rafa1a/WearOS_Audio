// AudioHelper.kt
package com.aprendendo.wearos_audio

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.media.AudioManager

class AudioHelpers(private val context: Context) {

    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun audioOutputAvailable(type: Int): Boolean {
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }

        return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).any { it.type == type }
    }
}
