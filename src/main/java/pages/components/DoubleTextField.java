package pages.components;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleTextField extends JTextField {

    public DoubleTextField() {
        setDocument(new DoubleDocument());
    }

    public DoubleTextField(int columns) {
        super(columns);
        setDocument(new DoubleDocument());
    }

    private static class DoubleDocument extends PlainDocument {
        private static final Pattern doublePattern = Pattern.compile("\\d*(\\.\\d*)?");

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            String currentText = getText(0, getLength());
            String proposedText = currentText.substring(0, offs) + str + currentText.substring(offs);
            Matcher matcher = doublePattern.matcher(proposedText);

            if (matcher.matches()) super.insertString(offs, str, a);
        }
    }

}
