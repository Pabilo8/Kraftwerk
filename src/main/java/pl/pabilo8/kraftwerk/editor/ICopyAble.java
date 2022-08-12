package pl.pabilo8.kraftwerk.editor;

import com.google.gson.JsonObject;

/**
 * @author Pabilo8
 * @since 11.12.2021
 */
public interface ICopyAble
{
	JsonObject handleCopy();

	default JsonObject handleCut()
	{
		handleRemove();
		return handleCopy();
	}

	void handleRemove();

	void handlePaste(JsonObject element);
}
