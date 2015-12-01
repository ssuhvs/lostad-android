package com.lostad.app.demo.view.tour;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lostad.app.base.util.DialogUtil;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.util.Validator;
import com.lostad.app.demo.view.MainActivity;
import com.lostad.app.demo.R;
import com.lostad.applib.core.MyCallback;
import com.zxing.view.CaptureActivity;

/**
 * sszvip
 */
public class OrderPayActivity extends PayDemoActivity {

    private String mOrderNo;
    private String mOrderId;
    private Double mMoney ;
    private String mDesc = null;
    private String mSubject = null;
    private String mTourId;
    @ViewInject(R.id.tv_money_card)
    private TextView tv_money_card;
    @ViewInject(R.id.tv_money_order)
    private TextView tv_money_order;
    @ViewInject(R.id.tv_money_to_pay)
    private TextView tv_money_to_pay;

    @ViewInject(R.id.btn_pay)
    private TextView btn_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ViewUtils.inject(this);
        setTitle("订单确认");
        Intent i = getIntent();

        mMoney = i.getDoubleExtra("money",0.0);
        mOrderNo = i.getStringExtra("orderNo");
        mOrderId = i.getStringExtra("orderId");
        mSubject = i.getStringExtra("subject");
        mDesc = i.getStringExtra("desc");
        mTourId = i.getStringExtra("tourId");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_money_to_pay.setText(mMoney + "");
        tv_money_card.setText("");
        tv_money_order.setText(mMoney+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(item.getItemId()==android.R.id.home){
            DialogUtil.showAlertOkCancel(this, "您确定要取消此订单吗?", new MyCallback<Boolean>() {
                @Override
                public void onCallback(Boolean yes) {
                    if (yes) {
                        finish();
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public String getOutTradeNo() {
        return mOrderNo;
    }

    @OnClick(R.id.btn_pay)
    public void payMoney(View v){
       DialogUtil.showAlertOkCancel(this, "您确定要进行结算吗？", new MyCallback<Boolean>() {
           @Override
           public void onCallback(Boolean ok) {
              if(ok){
                  pay(mSubject, mDesc, tv_money_to_pay.getText().toString());
              }
           }
       });
    }

    @Override
    protected void paySuccess(boolean success,String msg){
        if(success){
            btn_pay.setVisibility(View.GONE);
            DialogUtil.showAlert(this, "恭喜您，支付成功！", new MyCallback<Boolean>() {
                @Override
                public void onCallback(Boolean success) {
                    Intent i = new Intent(ctx, MainActivity.class);
                     // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }
            });
        }
    }

    @OnClick(R.id.btn_qr)
    public void getQr(View v){
        Intent i = new Intent(this,CaptureActivity.class);
        startActivityForResult(i, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }

        switch(requestCode){
            case 0:

                String value = data.getStringExtra("value");
                if(Validator.isNotEmpty(value)){
                   try{
                       int index0 = value.indexOf("?x=");
                       int index1 = value.indexOf("&y=");

                       String tourId = value.substring(index0 + 3, index1);
                       String cardId = value.substring(index1+3);
                       LogMe.d(value);
                       LogMe.d("tourId:  "+tourId);
                       LogMe.d("cardId:  "+cardId);

                       if(mTourId.equals(tourId)){
                           loadUserQr(mTourId,cardId,mOrderId);
                       }else{
                            showToast("此二维码不能应用于本项目！");
                       }

                   }catch(Exception e){
                       e.printStackTrace();
                   }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void loadUserQr(final String tourId,final String code,final String orderId){
//        new Thread(){
//            QrCode4j g4j = null;
//            public void run() {
//                g4j = TourManager.getInstance().loadQr(tourId,code,orderId);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(g4j.isSuccess()){
//                             double money = g4j.DATA.JE;
//                             tv_money_card.setText(money+"");
//                             money = mMoney - money;
//                             tv_money_to_pay.setText(money+"");
//                        }else{
//                            tv_money_card.setText("0");
//                            DialogUtil.showToastCust(ctx, g4j.MSG);
//                        }
//
//                    }
//                });
//            };
//        }.start();
    }
}
