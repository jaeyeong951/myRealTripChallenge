package com.example.myrealtripchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
//        supportActionBar?.run {
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowHomeEnabled(true)
//        }
        val host = nav_host_fragment as NavHostFragment
        val navController = host.navController

       // appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment), drawer)
       // setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.splashFragment->{
                    main_toolbar.visibility = View.GONE
                }
                R.id.mainFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    main_toolbar.title = ""
                    //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
                }
                R.id.webViewFragment->{
                    main_toolbar.visibility = View.VISIBLE
                    main_toolbar.title = ""
                   //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }

}
