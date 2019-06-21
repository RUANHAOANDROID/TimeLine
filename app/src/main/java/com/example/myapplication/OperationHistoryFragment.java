package com.example.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.OperationhistoryFragmentBinding;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

public class OperationHistoryFragment extends SupportFragment {
    OperationhistoryFragmentBinding mBinding;
    private ArrayList<OperationHistoryEntity> datas = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OperationHistoryFragment() {
        // Required empty public constructor

    }

    public static OperationHistoryFragment newInstance(String param1, String param2) {
        OperationHistoryFragment fragment = new OperationHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.operationhistory_fragment, container, false);
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
                        Toast.makeText(_mActivity, "年份Click", Toast.LENGTH_SHORT).show();
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
        datas.add(new OperationHistoryEntity("2月26日", "安阳公安局", "【一村一警办公室】上报【河南郑州派出所】", 0));
        datas.add(new OperationHistoryEntity("3月10日", "新乡公安局", "【一村一警办公室】上报【河南郑州派出所】", 0));
        datas.add(new OperationHistoryEntity("4月11日", "洛阳公安局", "【一村一警办公室】上报【河南郑州派出所】", 2));
        datas.add(new OperationHistoryEntity("5月12日", "调处", "【未化解】", 1));
        datas.add(new OperationHistoryEntity("7月14日", "邓州公安局", "【一村一警办公室】上报【河南郑州派出所】", 0));
        datas.add(new OperationHistoryEntity("6月13日", "上报综治办", "【一村一警办公室】上报【河南郑州派出所】", 1));
        datas.add(new OperationHistoryEntity("8月15日", "郑州公安局", "【一村一警办公室】上报【河南郑州派出所】", 0));
        datas.add(new OperationHistoryEntity("9月16日", "上报综治办", "【一村一警办公室】上报【河南郑州派出所】", 2));
    }
}
