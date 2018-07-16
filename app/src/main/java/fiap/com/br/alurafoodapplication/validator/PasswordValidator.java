package fiap.com.br.alurafoodapplication.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import fiap.com.br.alurafoodapplication.R;

public class PasswordValidator implements Validator {
    private final TextInputLayout tilPassword;
    private final EditText fieldPassword;
    private final StandardValidator standardValidator;
    private Context context;

    public PasswordValidator(TextInputLayout tilPassword, Context context) {
        this.tilPassword = tilPassword;
        this.fieldPassword = tilPassword.getEditText();
        this.standardValidator = new StandardValidator(this.tilPassword, context);
        this.context = context;
    }

    private boolean standardValidate(String password) {
        if (password.matches("((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,16})")) {
            return true;
        }
        tilPassword.setError(context.getString(R.string.form_txt_error_password));
        return false;

    }

    @Override
    public boolean isValid() {
        if (!standardValidator.isValid()) return false;
        String password = fieldPassword.getText().toString();
        if (!standardValidate(password)) return false;
        return true;

    }
}
