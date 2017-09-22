package com.cndownton.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cndownton.app.R;
import com.cndownton.app.downton.MyApplication;
import com.cndownton.app.downton.data.bean.Access_tokenCode;
import com.cndownton.app.downton.data.bean.LoginCode;
import com.cndownton.app.downton.data.bean.UserInfo;
import com.cndownton.app.downton.data.bean.WXUserInfo;
import com.cndownton.app.downton.main.MainActivity;
import com.cndownton.app.downton.util.CommonUtil;
import com.cndownton.app.downton.util.HMACSHA256;
import com.cndownton.app.downton.util.JsonUitl;
import com.cndownton.app.downton.util.SharedPreferencesUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    private TextView mView;
    private String nowTime;
    private String signStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
        MyApplication.api.handleIntent(getIntent(),this);
//        mView=findViewById(R.id.tv);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show();

                break;
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5269ea51c51983b1&" +
                        "secret=14619a7548b2f56fdc3c20cf52d01a36&code="+((SendAuth.Resp)baseResp).code+"&grant_type=authorization_code";
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
        
                    }
    
                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(WXEntryActivity.this,"1",Toast.LENGTH_LONG).show();
                        Access_tokenCode code= (Access_tokenCode) JsonUitl.INSTANCE.stringToObject(response,Access_tokenCode.class);
//                        SharedPreferencesUtil util =new SharedPreferencesUtil(WXEntryActivity.this,"data_wx");
//                        util.put("token",code.getAccess_token());
//                        util.put("refreshToken",code.getRefresh_token());
//                        util.put("expires",code.getExpires_in());


                        String userInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+code.getAccess_token()
                                +"&openid="+code.getOpenid();
                        OkHttpUtils.get().url(userInfoUrl).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
//                                Toast.makeText(WXEntryActivity.this,response,Toast.LENGTH_LONG).show();
                                WXUserInfo info= (WXUserInfo) JsonUitl.INSTANCE.stringToObject(response,WXUserInfo.class);
                                SharedPreferencesUtil util= new SharedPreferencesUtil(WXEntryActivity.this,"user");
                                nowTime= CommonUtil.INSTANCE.getCurrentTime();
                                signStr=CommonUtil.INSTANCE.getRealShaStr("time="+nowTime,"unionid="+info.getUnionid());


                                util.put("unionid",info.getUnionid());
                                OkHttpUtils.post().url("http://www.cndownton.com/tools/app_api.ashx?action=user_login_weixin_unionid")
                                    .addParams("time",nowTime).addParams("unionid",info.getUnionid()).addParams("sign", HMACSHA256.INSTANCE.sha256_HMAC(WXEntryActivity.this,signStr))
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Log.i("mpf",e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(final String response, int id) {
                                        Log.i("mpf",response);
                                        JSONObject object = null;
                                        try {
                                            object=new JSONObject(response);
                                            if(object.getInt("status")==0){
                                                //新用户
        
                                            }else {
                                                UserInfo info= (UserInfo) JsonUitl.INSTANCE.stringToObject(object.getString("msg"),UserInfo.class);
                                                MyApplication application= (MyApplication) getApplication();
                                                application.logIn(info);
                                                MyApplication.Companion.setNeedFreshMeFrag(true);
//                                                MyApplication.Companion.removeActivity();
//                                                Intent intent=new Intent(WXEntryActivity.this, MainActivity.class);
//                                                intent.putExtra("login",true);
//                                                startActivity(intent);
                                                WXEntryActivity.this.finish();
                                            
                                            
                                            }
                                        } catch (final Exception e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    mView.setText(e.getMessage());
                                                }
                                            });
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                        
                    }
                });

                
                break;

        }

    }
}
