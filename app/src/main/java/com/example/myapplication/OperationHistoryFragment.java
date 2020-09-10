package com.example.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.timeline.BuildConfig;
import com.example.timeline.R;
import com.example.timeline.databinding.FragmentOperationhistoryBinding;

import java.util.ArrayList;

public class OperationHistoryFragment extends Fragment {
    FragmentOperationhistoryBinding mBinding;
    private ArrayList<OperationHistoryEntity> datas = new ArrayList<>();

    public static OperationHistoryFragment newInstance() {
        return new OperationHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_operationhistory, container, false);
        if (BuildConfig.DEBUG) {
            fakeData();
        }
        final OperationHistoryAdapter adapter = new OperationHistoryAdapter(datas);
        View header = new View(getActivity());

        adapter.addHeaderView(header);
        mBinding.timeLisView.setAdapter(adapter);
        TimeLineItemDecoration itmeDecoration = new TimeLineItemDecoration(getActivity());
        itmeDecoration.setTimeText("2019年");
        mBinding.timeLisView.addItemDecoration(itmeDecoration);
        mBinding.timeLisView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBinding.timeLisView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    final float tX = event.getX();
                    final float tY = event.getY();
                    boolean isYearRect = tX > TimeLineItemDecoration.TOUCH_RECT[0]
                            && tY > TimeLineItemDecoration.TOUCH_RECT[1]
                            && tX < TimeLineItemDecoration.TOUCH_RECT[2]
                            && tY < TimeLineItemDecoration.TOUCH_RECT[3];
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    boolean isFirst = layoutManager.findFirstVisibleItemPosition() == 0;
                    if (isFirst && isYearRect) {
                        recyclerView.playSoundEffect(SoundEffectConstants.CLICK);
                        Toast.makeText(getActivity(), "Year Click", Toast.LENGTH_SHORT).show();
                    }
                    return isYearRect;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });


        return mBinding.getRoot();
    }


    private void fakeData() {
        datas.add(new OperationHistoryEntity("2月26日", "XXX派出所", "【XXX街巡人员】上报【XXX派出所】", 0));
        datas.add(new OperationHistoryEntity("3月10日", "XXX市公安局", "【XXX派出所】上报【XXX市公安局】", 0));
        datas.add(new OperationHistoryEntity("4月11日", "XXX市公安局", "传唤", 2));
        datas.add(new OperationHistoryEntity("5月12日", "调处", "【未化解】", 1));
        datas.add(new OperationHistoryEntity("6月14日", "XXX公安局", "【XXX办公室】上报【XXX派出所】", 0));
        datas.add(new OperationHistoryEntity("7月13日", "上报综治办", "【XXX办公室】上报【XXX派出所】", 1));
        datas.add(new OperationHistoryEntity("8月15日", "XXX公安局", "【XXX办公室】上报【XXX派出所】", 0));
        datas.add(new OperationHistoryEntity("9月16日", "XXX综治办", "【XXX办公室】上报【XXX派出所】", 2));
        datas.add(new OperationHistoryEntity("11月16日", "XXX综治办", "【XXX办公室】上报【XXX派出所】", 2));
        datas.add(new OperationHistoryEntity("12月16日", "XXX综治办", "【XXX办公室】上报【XXX派出所】", 2));
    }
}
