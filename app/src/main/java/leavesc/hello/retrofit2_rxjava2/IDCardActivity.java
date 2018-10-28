package leavesc.hello.retrofit2_rxjava2;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import leavesc.hello.retrofit2_rxjava2.view.BaseActivity;
import leavesc.hello.retrofit2_rxjava2.viewmodel.IDCardViewModel;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.LViewModelProviders;

public class IDCardActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private IDCardViewModel idCardViewModel;

    private EditText et_cardNo;

    private TextView tv_idCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        et_cardNo = findViewById(R.id.et_cardNo);
        tv_idCard = findViewById(R.id.tv_idCard);
    }

    @Override
    protected ViewModel initViewModel() {
        idCardViewModel = LViewModelProviders.of(this, IDCardViewModel.class);
        idCardViewModel.getIdCardLiveData().observe(this, this::handlerIDCard);
        return idCardViewModel;
    }

    private void handlerIDCard(IDCard idCard) {
        tv_idCard.setText(new Gson().toJson(idCard));
    }

    public void queryIDCard(View view) {
        tv_idCard.setText(null);
        idCardViewModel.queryIDCard(et_cardNo.getText().toString());
    }

}
