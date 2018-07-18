package fiap.com.br.alurafoodapplication.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fiap.com.br.alurafoodapplication.R;
import fiap.com.br.alurafoodapplication.mask.CpfMask;
import fiap.com.br.alurafoodapplication.validator.CpfValidator;
import fiap.com.br.alurafoodapplication.validator.PasswordValidator;
import fiap.com.br.alurafoodapplication.validator.Validator;

public class LoginActivity extends AppCompatActivity {

    private final List<Validator> validators = new ArrayList<>();
    private EditText fieldCpf;
    private EditText fieldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpFieldCpf();
        setUpFieldPassword();
        setUpButtonRegister();
    }


    private void setUpFieldCpf() {
        TextInputLayout tilCpf = findViewById(R.id.login_til_cpf);
        final CpfValidator validator = new CpfValidator(tilCpf, getApplicationContext());
        fieldCpf = tilCpf.getEditText();
        validators.add(validator);
        fieldCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String cpf = fieldCpf.getText().toString();
                if (hasFocus) {
                    fieldCpf.setText(CpfMask.unmask(cpf));
                } else {
                    validator.isValid();
                }
            }
        });
    }

    private void setUpFieldPassword() {
        TextInputLayout tilPassword = findViewById(R.id.login_til_password);
        fieldPassword = tilPassword.getEditText();
        final PasswordValidator validator = new PasswordValidator(tilPassword, getApplicationContext());
        validators.add(validator);
        fieldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validator.isValid();
                }
            }
        });
    }


    private void setUpButtonRegister() {
        Button btnRegister = findViewById(R.id.login_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean formIsValid = validAllFields();
                if (formIsValid) {
                    Toast.makeText(LoginActivity.this, "DEU CERTO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validAllFields() {
        boolean formIsValid = true;
        for (Validator validator : validators) {
            if (!validator.isValid()) {
                formIsValid = false;
            }
        }
        return formIsValid;
    }
}
