package pl.pabilo8.kraftwerk.editor;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Unified change listener for {@link javax.swing.JTextField}, {@link javax.swing.JCheckBox} and {@link javax.swing.JSpinner}
 * @author Pabilo8
 * @since 27.11.2021
 */
@FunctionalInterface
public interface SimpleDocumentListener extends DocumentListener, ChangeListener, ItemListener
{
	void onFieldUpdate();

	@Override
	default void insertUpdate(DocumentEvent e) {
		onFieldUpdate();
	}
	@Override
	default void removeUpdate(DocumentEvent e) {
		onFieldUpdate();
	}
	@Override
	default void changedUpdate(DocumentEvent e) {
		onFieldUpdate();
	}

	@Override
	default void stateChanged(ChangeEvent e)
	{
		onFieldUpdate();
	}

	@Override
	default void itemStateChanged(ItemEvent e)
	{
		onFieldUpdate();
	}
}
