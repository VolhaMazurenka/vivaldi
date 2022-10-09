package com.mazurenka.vivaldi.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.mazurenka.vivaldi.R
import com.mazurenka.vivaldi.data.model.AudioModel
import com.mazurenka.vivaldi.utils.isO


class PlayerService : Service(), Player.Listener {

    private var player: ExoPlayer? = null
    private var mediaSession: MediaSessionCompat? = null
    private var notificationManager: NotificationManager? = null

    private var audioList: ArrayList<AudioModel>? = null


    override fun onBind(intent: Intent?): IBinder {
        Log.i(TAG, "${this::class.java} onBind")
        return PlayerServiceBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "${this::class.java} onCreate")

        initializeMediaSession()
        initializePlayer()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "${this::class.java} onStartCommand")
        Log.i(TAG, "${this::class.java} is running")

        audioList = intent?.getParcelableExtra(AUDIO_LIST)
        preparePlayer()

        if (isO()) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)
            val notificationBuilder = Notification.Builder(this, CHANNEL_ID)
                .setContentText("Service is Running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_foreground)

            startForeground(NOTIFICATION_ID, notificationBuilder.build())
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun initializeMediaSession() {
        Log.i(TAG, "${this::class.java} initializeMediaSession")

        mediaSession = MediaSessionCompat(this, TAG)
        mediaSession?.setMediaButtonReceiver(null)
        mediaSession?.isActive = true
    }

    private fun initializePlayer() {
        Log.i(TAG, "${this::class.java} initializePlayer")

        if (player == null) {
            player = ExoPlayer.Builder(this).build()
        }
    }

    private fun preparePlayer() {
        player?.setMediaItems(getMediaList())
        player?.prepare()
        player?.playWhenReady = true

        player?.addListener(this)
    }

    private fun getMediaList(): List<MediaItem> {
        val mediaItemsList = mutableListOf<MediaItem>()
        audioList?.let {
            for (item in it) {
                val mediaItem = MediaItem.fromUri(item.audioUri)
                mediaItemsList.add(mediaItem)
            }
        }
        return mediaItemsList
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "${this::class.java} onDestroy")

        releasePlayer()
        mediaSession?.isActive = false
    }

    private fun releasePlayer() {
        Log.i(TAG, "${this::class.java} releasePlayer")

        player!!.removeListener(this)
        player!!.stop()
        player!!.release()
        player = null
    }


    inner class PlayerServiceBinder : Binder() {
        fun getExoPlayerInstance(): ExoPlayer? {
            Log.i(TAG, "${this::class.java} getExoPlayerInstance")

            return player
        }
    }

    companion object {
        const val NOTIFICATION_ID = 251
        const val CHANNEL_ID = "PLAYER_SERVICE_NOTIFICATION_CHANNEL_ID"
        private const val TAG = "PLAYER_APP"
        private const val AUDIO_LIST = "AUDIO_LIST"
    }
}