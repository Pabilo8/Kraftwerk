package pl.pabilo8.kraftwerk.gui.action;

import pl.pabilo8.kraftwerk.Kraftwerk;
import pl.pabilo8.kraftwerk.editor.EditorProject;
import pl.pabilo8.kraftwerk.editor.EditorProject.ModelRestrictionTemplate;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public class ActionNewProjectFromTemplate extends AbstractAction
{
	private final ModelRestrictionTemplate template;

	public ActionNewProjectFromTemplate(ModelRestrictionTemplate template)
	{
		super("project_from_template_"+template.getName(), template.icon);
		this.template = template;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(Kraftwerk.INSTANCE.currentProject.hasBeenChanged)
		{
			int resp = Kraftwerk.createQuitDialog();
			if(resp==JOptionPane.CANCEL_OPTION)
				return;
		}
		EditorProject project = new EditorProject();
		project.template = this.template;
		Kraftwerk.onProjectLoad(project);
	}
}
