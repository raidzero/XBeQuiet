XBeQuiet
========

This module works by hooking and replacing the following methods

android.media.SoundPool.play()
android.media.MediaPlayer.start()
android.media.AudioTrack.play()

in any app the user has selected. If an app uses another method to play sound, it will do nothing :)
