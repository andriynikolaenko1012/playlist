package app.com.playlistfeedreader.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import app.com.playlistfeedreader.R
import app.com.playlistfeedreader.activity.MainActivity
import app.com.playlistfeedreader.network.APIService
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance

//import com.github.salomonbrys.kodein.LazyKodein
//import com.github.salomonbrys.kodein.android.appKodein
//import com.github.salomonbrys.kodein.instance
//import speech.com.app.speech.R
//import speech.com.app.speech.activity.MainActivity
//import speech.com.app.speech.network.RetrofitInterface


abstract class BaseFragment : Fragment() {

    protected val kodein = LazyKodein(appKodein)
    protected val apiService = kodein.instance<APIService>()

    protected lateinit var mainActivity: MainActivity
    private var progressDialog: ProgressDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
    }

//    fun showProgress(show: Boolean) {
//        mainActivity.progressView!!.visibility = if (show) View.VISIBLE else View.GONE
//    }
//

    fun showProgress(){
        progressDialog?.dismiss()
        progressDialog = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)
        progressDialog?.setTitle("")
        progressDialog?.setMessage("Loading. Please wait...")
        progressDialog?.isIndeterminate = true
        progressDialog?.show()
    }

    fun stopProgress(){
        progressDialog?.dismiss()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true, tag: String? = null) {
        val ft = mainActivity.supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        if(tag == null)
            ft.replace(R.id.placeholder, fragment)
        else
            ft.replace(R.id.placeholder, fragment, tag)
        if (addToBackStack) {
            ft.addToBackStack(null)
        }

        ft.commit()
    }

    fun goBack(){
        if (mainActivity.supportFragmentManager.backStackEntryCount > 0) {
            mainActivity.supportFragmentManager.popBackStackImmediate()
        }
    }
}
