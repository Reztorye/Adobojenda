package View;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6104469763680156211L;
	private final String placeholder;
    private boolean isPlaceholder;

    public PlaceholderTextField(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;
        this.isPlaceholder = true;  
        setForeground(Color.GRAY);
        
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholder) { 
                    setText("");
                    setForeground(Color.BLACK);
                    isPlaceholder = false; 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    isPlaceholder = true;  
                }
            }
        });
    }

    @Override
    public String getText() {
        return isPlaceholder ? "" : super.getText();  
    }

	public String getPlaceholder() {
		return placeholder;
	}
}
