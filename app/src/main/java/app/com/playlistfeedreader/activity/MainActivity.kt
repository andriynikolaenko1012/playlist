package app.com.playlistfeedreader.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import app.com.playlistfeedreader.R
import app.com.playlistfeedreader.fragment.PlaylistFragment
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : KodeinAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null){

            val plId = getString(R.string.playlist_id_1)

            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.placeholder, PlaylistFragment.newInstance(plId))
                .commit()
            nav_view.menu.getItem(0).isChecked = true
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment?

        when (item.itemId) {
            R.id.nav_camera -> {
                fragment = PlaylistFragment.newInstance(getString(R.string.playlist_id_1))
            }
            R.id.nav_gallery -> {
                fragment = PlaylistFragment.newInstance(getString(R.string.playlist_id_2))
            }
            R.id.nav_slideshow -> {
                fragment = PlaylistFragment.newInstance(getString(R.string.playlist_id_3))
            }
            else -> fragment = PlaylistFragment()
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.placeholder, fragment).commit()
        item.isChecked = false
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
