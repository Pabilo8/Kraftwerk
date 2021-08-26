package pl.pabilo8.kraftwerk.editor.elements;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Pabilo8
 * @since 15.08.2021
 */
public abstract class ModelElement extends DefaultMutableTreeNode
{
	String name = "";
	int x = 0, y = 0, z = 0;

	public ModelElement(String name,int x, int y, int z)
	{
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
