package pl.pabilo8.kraftwerk.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Pabilo8
 * @since 29.08.2021
 */
public class JListDeselectable<E> extends JList<E>
{
	public JListDeselectable(ListModel<E> dataModel)
	{
		super(dataModel);
		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(locationToIndex(e.getPoint())==-1&&!e.isShiftDown()
						&&!isMenuShortcutKeyDown(e))
				{
					clearSelection();
				}
			}

			private boolean isMenuShortcutKeyDown(InputEvent event)
			{
				return (event.getModifiers()&Toolkit.getDefaultToolkit()
						.getMenuShortcutKeyMask())!=0;
			}
		});
		setComponentPopupMenu(new StandardPopupMenu());
	}

	@Override
	public int locationToIndex(Point location)
	{
		int index = super.locationToIndex(location);
		if(index!=-1&&!getCellBounds(index, index).contains(location))
		{
			return -1;
		}
		else
		{
			return index;
		}
	}


}
