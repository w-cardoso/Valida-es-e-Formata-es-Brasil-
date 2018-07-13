package fiap.com.br.alurafoodapplication.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import fiap.com.br.alurafoodapplication.Mask;
import fiap.com.br.alurafoodapplication.R;
import fiap.com.br.alurafoodapplication.validator.ValidatorCpf;
import fiap.com.br.alurafoodapplication.validator.ValidatorStandard;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFields();
    }

    private void initializeFields() {
        setUpFieldName();
        setUpFieldCpf();
        setUpFieldPhone();
        setUpFieldEmail();
        setUpFieldPassword();
    }

    private void setUpFieldPassword() {
        TextInputLayout tilPassword = findViewById(R.id.form_til_password);
        validateEmptyField(tilPassword);
    }

    private void setUpFieldEmail() {
        TextInputLayout tilEmail = findViewById(R.id.form_til_email);
        validateEmptyField(tilEmail);
    }

    private void setUpFieldPhone() {
        TextInputLayout tilPhone = findViewById(R.id.form_til_phone);
        validateEmptyField(tilPhone);
    }

    private void setUpFieldCpf() {
        TextInputLayout tilCpf = findViewById(R.id.form_til_cpf);
        final ValidatorCpf validatorCpf = new ValidatorCpf(tilCpf, getApplicationContext());
        final EditText fieldCpf = tilCpf.getEditText();

        fieldCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String cpf = fieldCpf.getText().toString();
                if (hasFocus) {
                    fieldCpf.setText(Mask.unmask(cpf));
                } else {
                    validatorCpf.isValid();
                }
            }
        });
    }


    private void setUpFieldName() {
        TextInputLayout tilName = findViewById(R.id.form_til_name);
        validateEmptyField(tilName);
    }

    private void validateEmptyField(final TextInputLayout tilField) {
        final EditText field = tilField.getEditText();
        final ValidatorStandard validator = new ValidatorStandard(tilField, getApplicationContext());
        field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validator.isValid();

                }
            }
        });
    }


}
