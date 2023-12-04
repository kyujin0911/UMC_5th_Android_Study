package umc.mission.floclone.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import umc.mission.floclone.ui.login.LoginActivity
import umc.mission.floclone.ui.main.MainActivity
import umc.mission.floclone.ui.adapter.ViewpagerFragmentAdapter
import umc.mission.floclone.ui.adapter.ViewpagerFragmentAdapter.Companion.LOCKER
import umc.mission.floclone.data.local.AUTH
import umc.mission.floclone.data.local.JWT
import umc.mission.floclone.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    private lateinit var binding: FragmentLockerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewPager2()
        binding.fragmentLockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }
    private fun initViewPager2(){
        val fragmentStateAdapter = ViewpagerFragmentAdapter(this, LOCKER)
        binding.fragmentLockerViewpager2.adapter = fragmentStateAdapter
        TabLayoutMediator(binding.fragmentLockerTablayout, binding.fragmentLockerViewpager2) { tab, position ->
            binding.fragmentLockerViewpager2.currentItem = tab.position
            tab.text = when(position){
                0 -> "저장한 곡"
                1 -> "음악파일"
                else -> "저장앨범"
            }
        }.attach()
    }

    private fun getJwt(): String? {
        val spf = activity?.getSharedPreferences(AUTH, AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString(JWT, null)
    }

    private fun initViews(){
        val jwt: String? = getJwt()
        Log.d("locker", jwt.toString())
        if(jwt == null){
            binding.fragmentLockerLoginTv.apply {
                text = "로그인"
                setOnClickListener { startActivity(Intent(activity, LoginActivity::class.java)) }
            }
        }
        else{
            binding.fragmentLockerLoginTv.apply {
                text = "로그아웃"
                setOnClickListener {
                    logout()
                    startActivity(Intent(activity, MainActivity::class.java)) }
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences(AUTH, AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove(JWT)
        editor.apply()
    }
}