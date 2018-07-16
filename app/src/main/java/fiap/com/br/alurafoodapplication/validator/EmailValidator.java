package fiap.com.br.alurafoodapplication.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public class EmailValidator implements Validator {
    private final TextInputLayout tilEmail;
    private final EditText fieldEmail;
    private final StandardValidator standardValidator;
    private final Context context;


    public EmailValidator(TextInputLayout tilEmail, Context context) {
        this.tilEmail = tilEmail;
        this.fieldEmail = tilEmail.getEditText();
        this.standardValidator = new StandardValidator(this.tilEmail, context);
        this.context = context;

    }

    private boolean standardValidate(String email) {
        if (email.matches(".+@.+\\..+")) {
            return true;
        }
        tilEmail.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean isValid() {
        if (!standardValidator.isValid()) return false;
        String email = fieldEmail.getText().toString();
        if (!standardValidate(email)) return false;
        return true;
    }
}
