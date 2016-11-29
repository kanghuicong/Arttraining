package com.example.kk.arttraining.ui.me.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.ui.me.adapter.OrderAdapter;
import com.example.kk.arttraining.ui.me.presenter.OrderPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/19 14:37
 * 说明:已付款的订单
 */
public class OrderPayedFragment extends Fragment implements IOrderView, BottomPullSwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener{

    ListView lv_order;
    private View view;
    private Context context;
    private List<OrderBean> list;
    private OrderAdapter orderAdapter;
    private int count;
    private OrderPresenter presenter;
    private boolean REFRESH_FIRST_FLAG = true;
    private List<OrderBean> listData;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        presenter = new OrderPresenter(this);
        view = View.inflate(context, R.layout.me_order_fragment, null);
        lv_order = (ListView) view.findViewById(R.id.lv_order);
        ButterKnife.inject(view);
        initView();
        return view;
    }

    private void initView() {
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(context);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) view.findViewById(R.id.order_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.autoRefresh();

        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    public void onRefresh() {
        AlreadyPaid("refresh");
    }
    @Override
    public void onLoad() {
        AlreadyPaid("load");
    }

    @Override
    public void getAllOrder(String type) {

    }

    @Override
    public void unPayOrder(String type) {

    }

    @Override
    public void AlreadyPaid(String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("status", "1");
        if(type.equals("load"))map.put("self",orderAdapter.getSelfId());
        presenter.getAlreadyPayOrderData(map,type);

    }

    //刷新成功
    @Override
    public void SuccessRefresh(List<OrderBean> payOrderList) {
        swipeRefreshLayout.setRefreshing(false);
        listData = payOrderList;
        if (listData.size() >= 9) swipeRefreshLayout.setOnLoadListener(this);
        if (REFRESH_FIRST_FLAG) {
            orderAdapter = new OrderAdapter(context, listData);
            lv_order.setAdapter(orderAdapter);
        } else {
            orderAdapter.notifyDataSetChanged();
        }

    }

    //加载成功
    @Override
    public void SuccessLoad(List<OrderBean> payOrderList) {
        swipeRefreshLayout.setLoading(false);
        listData.addAll(payOrderList);
        orderAdapter.refreshCount(listData.size());
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailedError(String error_code, String errorMsg) {
        swipeRefreshLayout.setRefreshing(false);
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(context, getResources().getString(R.string.toast_token_nvalid));
        } else if(error_code.equals("20007")){
            UIUtil.ToastshowShort(context, "没有更多订单了哦！");
        }else {
            UIUtil.ToastshowShort(context, errorMsg);
        }

    }


}
