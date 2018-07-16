package fiap.com.br.alurafoodapplication.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import fiap.com.br.alurafoodapplication.mask.CpfMask;
import fiap.com.br.alurafoodapplication.R;

import static fiap.com.br.alurafoodapplication.mask.CpfMask.CPF_MASK;

public class CpfValidator implements Validator {
    private final TextInputLayout tilCpf;
    private final EditText fieldCpf;
    private final StandardValidator validatorStandard;
    private final Context context;


    public CpfValidator(TextInputLayout tilCpf, Context context) {
        this.tilCpf = tilCpf;
        this.fieldCpf = tilCpf.getEditText();
        this.validatorStandard = new StandardValidator(tilCpf, context);
        this.context = context;
    }

    private boolean validateFieldNumberOfDigits(String cpf) {
        //String unmaskCpf = CpfMask.unmask(cpf);
        if (cpf.length() != 11) {
            tilCpf.setError(context.getString(R.string.validator_cpf_error_eleven_digits));
            return false;
        }
        return true;

    }

    private boolean validadeCalculationCpf(String cpf) {
        //cpf = CpfMask.unmask(cpf);
        if (cpf.equals("00000000000") || cpf.equals("11111111111")
                || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777")
                || cpf.equals("88888888888") || cpf.equals("99999999999")) {
            tilCpf.setError(context.getString(R.string.validator_cpf_error_cpf_invalid_calculation));
            return false;
        }
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return (true);
            else
                tilCpf.setError(context.getString(R.string.validator_cpf_error_cpf_invalid_calculation));
            return (false);
        } catch (Exception erro) {
            tilCpf.setError(context.getString(R.string.validator_cpf_error_cpf_invalid_calculation));
            return (false);
        }
    }

    public boolean isValid() {
        if (!validatorStandard.isValid()) return false;
        String cpf = fieldCpf.getText().toString();
        String cpfUnMask = CpfMask.unmask(cpf);
        if (!validateFieldNumberOfDigits(cpfUnMask)) return false;
        if (!validadeCalculationCpf(cpfUnMask)) return false;
        addMask(cpfUnMask);
        return true;
    }

    private void addMask(String cpf) {
        fieldCpf.setText(CpfMask.mask(CPF_MASK, cpf));
    }
}
