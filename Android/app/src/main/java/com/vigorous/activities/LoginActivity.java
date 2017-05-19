package com.vigorous.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vigorous.R;
import com.vigorous.entity.ResultModel;
import com.vigorous.network.Retrofit2Mgr;
import com.vigorous.utils.Constant;
import com.vigorous.utils.SpUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxm.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mEditTextName, mEditTextPassword;
    private Button mBtnLogin, mBtnRegist;
    private String mName=null,mPass=null,mToken=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        mEditTextName = (EditText) findViewById(R.id.et_name);
        mEditTextPassword = (EditText) findViewById(R.id.et_passwrord);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegist = (Button) findViewById(R.id.btn_regist);
        mBtnLogin.setOnClickListener(this);
        mBtnRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (checkParams()) {
                    Retrofit2Mgr.getInstance(Constant.HOTCH_USER).login(mName, mPass, new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            Log.d(TAG,"login " + response.code());
                            ResultModel model = response.body();
                            setToken(model.getContent());
                            SpUtils.set(Constant.LAST_TOKEN,mToken.split("_")[1]);
                            SpUtils.set(Constant.LAST_NAME,mName);
                            SpUtils.set(Constant.LAST_UID,mToken.split("_")[0]);
                            SpUtils.set(Constant.LAST_AVATAR,mToken.split("_")[2]);
                            Log.d(TAG,response.message() + " " + response.body().getContent());
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.btn_regist:
                if (checkParams()) {
                    Retrofit2Mgr.getInstance(Constant.HOTCH_USER).regist(mName, mPass, new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            Log.d(TAG,"regist " + response.code());
                            ResultModel model = response.body();
                            if (model != null) {
                                SpUtils.set(Constant.LAST_TOKEN, model.getContent().split("_")[1]);
                                SpUtils.set(Constant.LAST_NAME, mName);
                                SpUtils.set(Constant.LAST_UID, model.getContent().split("_")[0]);
                                Log.d(TAG, "regist token " + model.getContent().split("_")[1]);
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {

                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void setToken(String token) {
        mToken = token;
    }

    private boolean checkParams() {
        mName = mEditTextName.getText().toString();
        mPass = mEditTextPassword.getText().toString();
        return mName.length() > 0 && mPass.length() > 0;
    }
}
