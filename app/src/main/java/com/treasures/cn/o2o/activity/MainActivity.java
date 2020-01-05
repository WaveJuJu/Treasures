package com.treasures.cn.o2o.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.treasures.cn.R;
import com.treasures.cn.handler.MemoryData;
import com.treasures.cn.o2o.BaseActivity;
import com.treasures.cn.o2o.activity.login.LoginActivity;
import com.treasures.cn.o2o.fragment.TabNavHostFragment;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private long exitTime = 0;
    public int currentIndex = 0;
    @BindView(R.id.bottom_bar_navigation)
    BottomNavigationView bottomNavigationView;
    private LiveData<NavController> currentNavController;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    private TabNavHostFragment navHostFragment;
    private NavController navController;

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        super.initView();
        navHostFragment = (TabNavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        toLoginActivity();
    }

    private void toLoginActivity() {
        if (!MemoryData.USER_INFO.getPassword().isEmpty() && !MemoryData.isLogin) {
            LoginActivity.openLoginActivity(mActivity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MemoryData.isLogin = false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            return false;
        }
        return NavHostFragment.findNavController(fragment).navigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(mActivity, getString(R.string.onclickis_exit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }
}
