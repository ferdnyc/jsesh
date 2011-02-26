package jsesh.editor;

/**
 * Actions ID for the JSesh editor widget.
 * All actions in the editor's action map will (utimately) have their ID here.
 * @author rosmord
 */
public interface ActionsID {

    String BEGINNING_OF_LINE = "edit.beginningOfLine";
    String CLEAR_SELECTION = "edit.clearSelection";
    String COPY = "edit.copy";
    String COPY_AS_BITMAP = "edit.copyAsBitmap";
    String COPY_AS_MDC = "edit.copyAsMDC";
    String COPY_AS_PDF = "edit.copyAsPDF";
	String COPY_AS_RTF = "edit.copyAsRTF";
	String CUT = "edit.cut";
	String END_OF_LINE = "edit.EndOfLine";
	String EXPAND_SELECTION_DOWN = "edit.expandSelectionDown";
	String EXPAND_SELECTION_LEFT = "edit.expandSelectionLeft";
	String EXPAND_SELECTION_RIGHT = "edit.expandSelectionRight";
	String EXPAND_SELECTION_UP = "edit.expandSelectionUp";
	String GO_DOWN = "edit.down";
	String GO_LEFT = "edit.left";
	String GO_RIGHT = "edit.right";
	String GO_UP = "edit.up";
	String GROUP_HORIZONTAL = "text.groupHorizontally";
	String GROUP_VERTICAL = "text.groupVertically";
	//String INSERT_CHAR = "INSERT CHAR";
	String NEW_LINE = "text.newLine";
	String NEW_PAGE = "text.newPage";
	String PASTE = "edit.paste";
	String REDO = "edit.redo";
	String SELECT_ALL=  "edit.selectAll";
	String SELECT_HORIZONTAL_ORIENTATION = "SELECT_HORIZONTAL_ORIENTATION";
	String SELECT_L2R_DIRECTION = "SELECT_L2R_DIRECTION";
	String SELECT_R2L_DIRECTION = "SELECT_R2L_DIRECTION";
	String SELECT_VERTICAL_ORIENTATION = "SELECT_VERTICAL_ORIENTATION";
	String SET_MODE_BOLD = "edit.setModeBold";
	String SET_MODE_HIEROGLYPHS = "edit.setModeHieroglyphs";
	String SET_MODE_ITALIC = "edit.setModeItalic";
	String SET_MODE_LATIN = "edit.setModeLatin";
	String UNDO = "edit.undo";
}
