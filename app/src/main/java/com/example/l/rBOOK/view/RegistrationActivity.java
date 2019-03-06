package com.example.l.rBOOK.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.MainActivity;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.User;
import com.example.l.rBOOK.presenter.RegistrationPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewInterface {
    @BindView(R.id.registration_constraint_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.image_registration)
    ImageView imageRegistration;
    @BindView(R.id.registration_id)
    MaterialEditText editID;
    @BindView(R.id.registration_nickname)
    MaterialEditText editName;
    @BindView(R.id.registration_identity)
    MaterialEditText editIdentity;
    @BindView(R.id.registration_password)
    MaterialEditText editPassword;
    @BindView(R.id.registration_password_repeat)
    MaterialEditText editPasswordRepeat;
    @BindView(R.id.button_sign)
    MaterialButton signButton;


    private String thePassword;
    private static RegistrationActivity instance;
    private RegistrationPresenter presenter;
    GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        ButterKnife.bind(this);

        globalData = (GlobalData) getApplication();
        initViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(){
        presenter=new RegistrationPresenter(this);
        instance=this;

        signButton.setOnClickListener(v -> {
            String account = getAccount();
            String nickname=editName.getText().toString();
            String password = getPassword();
            String passwordRepeat = getPasswordRepeat();
            String identity=editIdentity.getText().toString();
            if(!account.equals("")&&!password.equals("")&&!passwordRepeat.equals("")&&!nickname.equals("")){

                if(password.equals(passwordRepeat)){
                    if(identity.length()==18){
                        Map<String, Object> map = new HashMap<>();
                        map.put("username", getAccount());
                        map.put("password", getPassword());
                        map.put("nickname", nickname);
                        map.put("identity",identity);
                        thePassword=password;
                        presenter.registration(this, map);
                    }
                    else {
                        editIdentity.setError("incorrect identity number");
                    }
                } else{
                    editPasswordRepeat.setError(getString(R.string.password_not_same));
                }
            } else {
                if(account.equals("")){
                    editID.setError(getString(R.string.no_empty));
                }
                if(nickname.equals("")){
                    editName.setError(getString(R.string.no_empty));
                }
                if(identity.equals(""))
                {
                    editIdentity.setError(getString(R.string.no_empty));
                }
                if(password.equals("")){
                    editPassword.setError(getString(R.string.no_empty));
                }
                if(passwordRepeat.equals("")){
                    editPasswordRepeat.setError(getString(R.string.no_empty));
                }
            }

        });

        constraintLayout.setOnTouchListener((v, event) -> {
            constraintLayout.setFocusable(true);
            constraintLayout.setFocusableInTouchMode(true);
            constraintLayout.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editID.getWindowToken(),0);
            imm.hideSoftInputFromWindow(editPassword.getWindowToken(),0);
            imm.hideSoftInputFromWindow(editPasswordRepeat.getWindowToken(),0);
            return false;
        });

    }

    @Override
    public String getAccount() {
        return editID.getText().toString();
    }


    @Override
    public String getPassword() {
        return editPassword.getText().toString();
    }

    private String getPasswordRepeat() {
        return editPasswordRepeat.getText().toString();
    }

    @Override
    public void registrationSuccess() {
        Toast.makeText(this,R.string.registration_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
        instance.finish();
        startActivity(intent);

    }

    @Override
    public void registrationFail() {
        Toast.makeText(this,R.string.registration_fail, Toast.LENGTH_SHORT).show();
        editID.setText("");
        editPassword.setText("");
        editPasswordRepeat.setText("");
    }

}
