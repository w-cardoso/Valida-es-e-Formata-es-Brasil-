package fiap.com.br.alurafoodapplication.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import fiap.com.br.alurafoodapplication.mask.PhoneMask;

public class PhoneValidator implements Validator {

    private final TextInputLayout tilPhone;
    private final EditText fieldPhone;
    private final StandardValidator standardValidator;
    private final Context context;
    private final PhoneMask phoneMask = new PhoneMask();

    public PhoneValidator(TextInputLayout tilPhone, Context context) {
        this.tilPhone = tilPhone;
        this.fieldPhone = tilPhone.getEditText();
        this.context = context;
        this.standardValidator = new StandardValidator(tilPhone, context);
    }

    private boolean validateNumberOfDigits(String phone) {
        int digitos = phone.length();
        if (digitos < 10 || digitos > 11) {
            tilPhone.setError("Telefone deve ter entre 10 à 11 digitos");
            return false;
        }
        return true;
    }

    public boolean isValid() {
        if (!standardValidator.isValid()) return false;
        String phone = fieldPhone.getText().toString();
        String phoneWithoutMask = phoneMask.unMaskPhone(phone);
        if (!validateNumberOfDigits(phoneWithoutMask)) return false;
        addMask(phoneWithoutMask);
        return true;
    }

    private void addMask(String phone) {
        String phoneWithMask = phoneMask.phoneMask(phone);
        fieldPhone.setText(phoneWithMask);
    }
}
