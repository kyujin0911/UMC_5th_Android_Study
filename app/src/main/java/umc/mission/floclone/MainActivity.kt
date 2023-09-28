package umc.mission.floclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import umc.mission.floclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        binding.activityMainBtnPlay.setOnClickListener {
            binding.activityMainBtnPlay.isVisible = false
            binding.activityMainBtnPause.isVisible = true
        }

        binding.activityMainBtnPause.setOnClickListener {
            binding.activityMainBtnPlay.isVisible = true
            binding.activityMainBtnPause.isVisible = false
        }
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_main_fragment_container, HomeFragment())
            commitAllowingStateLoss()
        }

        binding.activityMainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mnu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mnu_locker -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mnu_look -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mnu_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}