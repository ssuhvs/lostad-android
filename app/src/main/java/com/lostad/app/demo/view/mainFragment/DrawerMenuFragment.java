package com.lostad.app.demo.view.mainFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.view.DrawerActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class DrawerMenuFragment extends BaseFragment {

    @ViewInject(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @ViewInject(R.id.rl_container)
    RelativeLayout rl_container;

    private LinearLayoutManager mLayoutManager;
    private DrawerActivity mainActivity;
    private MenuAdapter mAdapter;
    private SideMenuItem.FragmentType currentFragment = SideMenuItem.FragmentType.FreshNews;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof DrawerActivity) {
            mainActivity = (DrawerActivity) activity;
        } else {
            throw new IllegalArgumentException("The activity must be a MainActivity !");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer_menu, container, false);
        x.view().inject(this,view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), SettingActivity.class));
                mainActivity.closeDrawer();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MenuAdapter();
        mAdapter.menuItems.clear();
        mAdapter.menuItems.add(new SideMenuItem("新鲜事", R.drawable.ic_action_chat, SideMenuItem.FragmentType.FreshNews,
                ListWaterFragment.class));
        mAdapter.menuItems.add(new SideMenuItem("无聊图", R.drawable.ic_action_chat, SideMenuItem.FragmentType.BoringPicture,
                ListWaterFragment.class));
        mAdapter.menuItems.add(new SideMenuItem("小电影", R.drawable.ic_action_chat, SideMenuItem.FragmentType.Video,
                ListWaterFragment.class));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<SideMenuItem> menuItems;

        public MenuAdapter() {
            menuItems = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final SideMenuItem menuItem = menuItems.get(position);

            holder.tv_title.setText(menuItem.getTitle());
            holder.img_menu.setImageResource(menuItem.getResourceId());
            holder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (currentFragment != menuItem.getType()) {
                            Fragment fragment = (Fragment) Class.forName(menuItem.getFragment()
                                    .getName()).newInstance();
                            mainActivity.replaceFragment(R.id.frame_container, fragment);
                            currentFragment = menuItem.getType();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mainActivity.closeDrawer();
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuItems.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_menu;
        private TextView tv_title;
        private RelativeLayout rl_container;


        public ViewHolder(View itemView) {
            super(itemView);
            img_menu = (ImageView) itemView.findViewById(R.id.img_menu);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rl_container = (RelativeLayout) itemView.findViewById(R.id.rl_container);
        }
    }


}