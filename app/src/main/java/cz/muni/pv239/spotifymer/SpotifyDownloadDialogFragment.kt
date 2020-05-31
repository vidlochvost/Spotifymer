package cz.muni.pv239.spotifymer

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SpotifyDownloadDialogFragment : DialogFragment() {

    val appPackageName = "com.spotify.music"
    val referrer =
        "adjust_campaign=PACKAGE_NAME&adjust_tracker=ndjczk&utm_source=adjust_preinstall"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.download_spotify)
                .setPositiveButton(R.string.download) { _, _ ->
                    try {
                        val uri = Uri.parse("market://details")
                            .buildUpon()
                            .appendQueryParameter("id", appPackageName)
                            .appendQueryParameter("referrer", referrer)
                            .build()
                        this.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    } catch (ignored: ActivityNotFoundException) {
                        val uri =
                            Uri.parse("https://play.google.com/store/apps/details")
                                .buildUpon()
                                .appendQueryParameter("id", appPackageName)
                                .appendQueryParameter("referrer", referrer)
                                .build()
                        this.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }
                }
                .setNegativeButton(R.string.back) { _, _ ->
                    this.activity?.finish()
                }.setCancelable(false)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
