package umc.mission.floclone.ui.main.look

import umc.mission.floclone.data.remote.song.FloChartResult

interface LookView {
    fun onGetSongLoading()
    fun onGetSongSuccess(code: Int, result: FloChartResult)
    fun onGetSongFailure(code: Int, message: String)
}