package nauq.mal.com.formapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.MenuItemAdapter;
import nauq.mal.com.formapp.fragments.bookmark.BookmarkFragment;
import nauq.mal.com.formapp.fragments.ContactUsFragment;
import nauq.mal.com.formapp.fragments.dictionary.SearchFragment;
import nauq.mal.com.formapp.fragments.menulearn.CourseFragment;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.fragments.profile.ProfileFragment;
import nauq.mal.com.formapp.models.MenuItem;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class MainActivity extends BaseActivity implements DrawerLayout.DrawerListener, MenuItemAdapter.IOnMenuItemClicklistener, View.OnClickListener {


    public enum MENU_ITEM {MENU_HOME, MENU_LOGOUT, MENU_BOOKMARKS, MENU_CONTACT, MENU_SETTING, MENU_PROFILE}

    ;
    private ImageView imgAvatar, mImvBack;
    private DrawerLayout mDrawerLayout;
    private TextView tvUsername;
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
            .considerExifParams(true)
            .showImageForEmptyUri(R.drawable.ic_profile_menu).showImageOnFail(R.drawable.ic_profile_menu)
            .showImageOnLoading(R.drawable.ic_profile_menu).bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
    private Fragment mCurrentFragment;
    private RecyclerView mRecyclerViewMenu;
    private MENU_ITEM mCurrentMenu, mMenuBefore;
    private LinearLayout tabBottom;
    private View mLayoutSlideMenu, mCurrentTab, mTabHome, mTabGrammar, mTabToeic, mTabDictionary;
    private ArrayList<Fragment> mDataFragment;
    private EditText etSearch;
    private TextView tvTitle;
    private String type;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        etSearch = findViewById(R.id.et_search);
        tvTitle = findViewById(R.id.tv_title);
        imgAvatar = findViewById(R.id.imv_avatar);
        tvUsername = findViewById(R.id.tv_fullname);
        tabBottom = findViewById(R.id.tab_bottom);
        mImvBack = findViewById(R.id.imv_nav_left);
        mImvBack.setVisibility(View.VISIBLE);
        mImvBack.setImageResource(R.drawable.ic_menu_white);
        mCurrentTab = findViewById(R.id.tab_home);
        mTabToeic = findViewById(R.id.tab_toeic);
        mTabGrammar = findViewById(R.id.tab_grammar);
        mTabDictionary = findViewById(R.id.tab_dictionary);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mLayoutSlideMenu = findViewById(R.id.layout_left_menu);
        mRecyclerViewMenu = findViewById(R.id.recyclerview_menu);
        mRecyclerViewMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(MENU_ITEM.MENU_HOME, R.drawable.ic_home, getString(R.string.txt_home)));
        if (!SharedPreferenceHelper.getInstance(this).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)) {
            menuItems.add(new MenuItem(MENU_ITEM.MENU_BOOKMARKS, R.drawable.ic_bookmark_menuu, getString(R.string.txt_bookmark)));
            menuItems.add(new MenuItem(MENU_ITEM.MENU_PROFILE, R.drawable.ic_profilee, getString(R.string.txt_profile)));
            menuItems.add(new MenuItem(MENU_ITEM.MENU_CONTACT, R.drawable.ic_contact, getString(R.string.txt_contact)));
            menuItems.add(new MenuItem(MENU_ITEM.MENU_LOGOUT, R.drawable.ic_logout, getString(R.string.txt_logout)));
        } else {
            menuItems.add(new MenuItem(MENU_ITEM.MENU_CONTACT, R.drawable.ic_contact, getString(R.string.txt_contact)));
        }
//        menuItems.add(new MenuItem(MENU_ITEM.MENU_BOOKMARKS, R.drawable.ic_bookmark_menuu, getString(R.string.txt_bookmark)));
//        menuItems.add(new MenuItem(MENU_ITEM.MENU_PROFILE, R.drawable.ic_profilee, getString(R.string.txt_profile)));
//        menuItems.add(new MenuItem(MENU_ITEM.MENU_SETTING, R.drawable.ic_settingg, getString(R.string.txt_setting)));
//        menuItems.add(new MenuItem(MENU_ITEM.MENU_LOGOUT, R.drawable.ic_logout, getString(R.string.txt_logout)));
        MenuItemAdapter menuAdapter = new MenuItemAdapter(this, menuItems);
        menuAdapter.setItemListener(this);
        mRecyclerViewMenu.setAdapter(menuAdapter);
        mCurrentMenu = MENU_ITEM.MENU_HOME;
        menuAdapter.setItemSelected(MENU_ITEM.MENU_HOME);
        mDrawerLayout.addDrawerListener(this);

        type = getString(R.string.txt_home);
        mCurrentTab.setSelected(true);

        setTitle(getString(R.string.txt_home));
        setNewPage(new HomeFragment());
        loadTag();
        addListener();
    }

    private void loadTag() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SharedPreferenceHelper.getInstance(this).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)) {
            loadProfile();
        }
    }

    private void loadProfile() {
        String userJson = SharedPreferenceHelper.getInstance(this).get(Constants.PREF_USER_PROFILE);
        if (userJson != null && userJson.length() > 0) {
            User user = new Gson().fromJson(userJson, User.class);
            if (user.getAvatar() != null && user.getAvatar() != "") {
                Picasso.with(this).load(Constants.LINK_IMG + user.getAvatar()).into(imgAvatar);
            }
            if (user.getName() != null && user.getName() != "") {
                tvUsername.setText(user.getName());
            }
        }
    }

    public void reloadProfile() {
        if (SharedPreferenceHelper.getInstance(this).get(Constants.PREF_IMG_ID) != null) {
            Picasso.with(this).load(Constants.LINK_IMG + SharedPreferenceHelper.getInstance(this).get(Constants.PREF_IMG_ID)).into(imgAvatar);
        }
        if (SharedPreferenceHelper.getInstance(this).get(Constants.PREF_PERSON_NAME) != null) {
            tvUsername.setText(SharedPreferenceHelper.getInstance(this).get(Constants.PREF_PERSON_NAME));
        }
    }

    @Override
    protected void addListener() {
        mCurrentTab.setOnClickListener(this);
        mTabDictionary.setOnClickListener(this);
        mTabGrammar.setOnClickListener(this);
        mTabToeic.setOnClickListener(this);
        mImvBack.setOnClickListener(this);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        loadProfile();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (mCurrentMenu == null || mMenuBefore == null) {
            return;
        }
        mMenuBefore = null;
        switch (mCurrentMenu) {
            case MENU_HOME:
                setTitle(getString(R.string.txt_home));
                mCurrentFragment = HomeFragment.newInstance("");
                mCurrentTab.setSelected(false);
                mCurrentTab = findViewById(R.id.tab_home);
                mCurrentTab.setSelected(true);
                type = getString(R.string.txt_home);
                tabBottom.setVisibility(View.VISIBLE);
                setNewPage(mCurrentFragment);
                break;
            case MENU_PROFILE:
                setTitle(getString(R.string.txt_profile));
                mCurrentFragment = ProfileFragment.newInstance();
                tabBottom.setVisibility(View.GONE);
                type = getString(R.string.txt_profile);
                setNewPage(mCurrentFragment);
                break;
            case MENU_CONTACT:
                setTitle(getString(R.string.txt_contact));
                mCurrentFragment = ContactUsFragment.newInstance();
                tabBottom.setVisibility(View.GONE);
                type = getString(R.string.txt_contact);
                setNewPage(mCurrentFragment);
                break;
            case MENU_BOOKMARKS:
                setTitle(getString(R.string.txt_bookmark));
                mCurrentFragment = BookmarkFragment.newInstance();
                tabBottom.setVisibility(View.GONE);
                type = getString(R.string.txt_bookmark);
                setNewPage(mCurrentFragment);
                break;
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (type.equals(getString(R.string.txt_profile)) || type.equals(getString(R.string.txt_contact))
                || type.equals(getString(R.string.txt_bookmark))) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if(type.equals(getString(R.string.txt_home))){
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(MENU_ITEM menuId, MENU_ITEM currentMenu) {
        if (currentMenu == MENU_ITEM.MENU_LOGOUT) {
            mMenuBefore = null;
            showPopupLogout();
        } else {
            mCurrentMenu = menuId;
            mMenuBefore = menuId;
        }
        mDrawerLayout.closeDrawer(mLayoutSlideMenu);
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ADD_QUES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
//               mCurrentFragment.lo
            }
        }
    }

    private void showPopupLogout() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.txt_are_you_sure_logout))
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showLoading(true);
//                        new LogoutTask(MainActivity.this, MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        logout();
                    }
                })
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void logout() {
        SharedPreferenceHelper.getInstance(this).clearSharePrefs();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_home:
                mCurrentTab.setSelected(false);
                mCurrentTab = view;
                mCurrentTab.setSelected(true);
                setTitle(getString(R.string.txt_home));
                setNewPage(new HomeFragment());
                break;
            case R.id.tab_grammar:
                mCurrentTab.setSelected(false);
                mCurrentTab = view;
                mCurrentTab.setSelected(true);
                setTitle(getString(R.string.txt_grammar));
                setNewPage(new CourseFragment());
                break;
            case R.id.tab_dictionary:
                mCurrentTab.setSelected(false);
                mCurrentTab = view;
                mCurrentTab.setSelected(true);
                setTitle(getString(R.string.txt_hint_search));
                setNewPage(new SearchFragment());
                break;
            case R.id.imv_nav_left:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
    }


}
