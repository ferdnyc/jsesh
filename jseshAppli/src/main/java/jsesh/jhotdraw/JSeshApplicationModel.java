/*
Copyright Serge Rosmorduc
contributor(s) : Serge J. P. Thomas for the fonts
serge.rosmorduc@qenherkhopeshef.org

This software is a computer program whose purpose is to edit ancient egyptian hieroglyphic texts.

This software is governed by the CeCILL license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.
 */
package jsesh.jhotdraw;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;

import jsesh.editor.ActionsID;
import jsesh.editor.JMDCEditor;
import jsesh.editor.actions.text.AddPhilologicalMarkupAction;
import jsesh.editor.actions.text.EditorCartoucheAction;
import jsesh.editor.actions.text.EditorShadeAction;
import jsesh.editorSoftware.actions.CartoucheAction;
import jsesh.jhotdraw.actions.BundleHelper;
import jsesh.jhotdraw.actions.JSeshApplicationActionsID;
import jsesh.jhotdraw.actions.edit.InsertShortTextAction;
import jsesh.jhotdraw.actions.file.EditDocumentPreferencesAction;
import jsesh.jhotdraw.actions.file.ExportAsBitmapAction;
import jsesh.jhotdraw.actions.file.ImportPDFAction;
import jsesh.jhotdraw.actions.file.SetAsModelAction;
import jsesh.jhotdraw.actions.text.EditGroupAction;
import jsesh.jhotdraw.actions.text.InsertElementAction;
import jsesh.mdc.constants.SymbolCodes;
import jsesh.mdcDisplayer.preferences.DrawingSpecification;
import jsesh.swing.signPalette.HieroglyphPaletteListener;
import jsesh.swing.signPalette.JSimplePalette;
import jsesh.swing.signPalette.PalettePresenter;

import org.jhotdraw_7_4_1.app.Application;
import org.jhotdraw_7_4_1.app.DefaultApplicationModel;
import org.jhotdraw_7_4_1.app.View;
import org.jhotdraw_7_4_1.app.action.edit.ClearSelectionAction;
import org.jhotdraw_7_4_1.app.action.edit.CopyAction;
import org.jhotdraw_7_4_1.app.action.edit.CutAction;
import org.jhotdraw_7_4_1.app.action.edit.DeleteAction;
import org.jhotdraw_7_4_1.app.action.edit.DuplicateAction;
import org.jhotdraw_7_4_1.app.action.edit.PasteAction;
import org.jhotdraw_7_4_1.app.action.edit.SelectAllAction;
import org.jhotdraw_7_4_1.gui.JFileURIChooser;
import org.jhotdraw_7_4_1.gui.URIChooser;
import org.jhotdraw_7_4_1.io.ExtensionFileFilter;
import org.qenherkhopeshef.jhotdrawChanges.StandardMenuBuilder;

/**
 * JHotdraw-specific model for the application.
 * 
 * @author rosmord
 * 
 */
@SuppressWarnings("serial")
public class JSeshApplicationModel extends DefaultApplicationModel {

	/**
	 * Some actions require a knowledge of the application...
	 */
	private Application application;

	/**
	 * Everything which is a) application-level and b) non specific to JHotdraw
	 * is delegated to {@link JSeshApplicationBase}.
	 */
	private JSeshApplicationBase jseshBase = new JSeshApplicationBase();

	@Override
	public void initApplication(Application a) {
		super.initApplication(a);
		this.application = a;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jhotdraw_7_4_1.app.DefaultApplicationModel#initView(org.jhotdraw_7_4_1
	 * .app.Application, org.jhotdraw_7_4_1.app.View)
	 */
	@Override
	public void initView(Application a, View v) {
		super.initView(a, v);
		DrawingSpecification drawingSpecifications = jseshBase
				.getDrawingSpecifications().copy();
		JSeshView jSeshView = (JSeshView) v;
		jSeshView.setDrawingSpecifications(drawingSpecifications);
		jSeshView.setMDCModelTransferableBroker(jseshBase);
	}

	@Override
	public List<JMenu> createMenus(Application a, View v) {
		List<JMenu> menus = new ArrayList<JMenu>();
		menus.add(createTextMenu(a, (JSeshView) v));
		// menus.add(createSignMenu(a, (JSeshView) v));
		// View and Help Menu. Tools (insert new Sign ?)
		return menus;
	}

	private JMenu createTextMenu(Application a, JSeshView v) {
		JMenu textMenu = new JMenu();
		BundleHelper.getInstance().configure(textMenu, "text");
		addToMenu(textMenu, a, v, ActionsID.GROUP_HORIZONTAL);
		addToMenu(textMenu, a, v, ActionsID.GROUP_VERTICAL);
		addToMenu(textMenu, a, v, ActionsID.LIGATURE_ELEMENTS);
		addToMenu(textMenu, a, v, ActionsID.LIGATURE_GROUP_WITH_GLYPH);
		addToMenu(textMenu, a, v, ActionsID.LIGATURE_GLYPH_WITH_GROUP);
		addToMenu(textMenu, a, v, ActionsID.EXPLODE_GROUP);
		textMenu.addSeparator();
		addToMenu(textMenu, a, v, EditGroupAction.ID);
		addToMenu(textMenu, a, v, ActionsID.INSERT_SPACE);
		addToMenu(textMenu, a, v, ActionsID.INSERT_HALF_SPACE);
		addToMenu(textMenu, a, v, ActionsID.NEW_PAGE);
		addToMenu(textMenu, a, v, ActionsID.INSERT_RED_POINT);
		addToMenu(textMenu, a, v, ActionsID.INSERT_BLACK_POINT);

		// I am not sure it is interesting to give names to those actions...
		// add the import and export as menus...
		JMenu shadingSymbolsMenu = BundleHelper.getInstance().configure(
				new JMenu(), "text.shadingSymbols");
		addToMenu(shadingSymbolsMenu, a, v,
				JSeshApplicationActionsID.INSERT_FULL_SHADING);
		addToMenu(shadingSymbolsMenu, a, v,
				JSeshApplicationActionsID.INSERT_HORIZONTAL_SHADING);
		addToMenu(shadingSymbolsMenu, a, v,
				JSeshApplicationActionsID.INSERT_VERTICAL_SHADING);
		addToMenu(shadingSymbolsMenu, a, v,
				JSeshApplicationActionsID.INSERT_QUARTER_SHADING);

		textMenu.add(shadingSymbolsMenu);
		textMenu.addSeparator();
		addToMenu(textMenu, a, v, ActionsID.SHADE_ZONE);
		addToMenu(textMenu, a, v, ActionsID.UNSHADE_ZONE);
		addToMenu(textMenu, a, v, ActionsID.RED_ZONE);
		addToMenu(textMenu, a, v, ActionsID.BLACK_ZONE);
		textMenu.addSeparator();
		textMenu.add(buildShadingMenu(a, v));
		textMenu.add(buildCartoucheMenu(a, v));
		textMenu.add(buildEcdoticMenu(a, v));
		return textMenu;
	}


	private JMenu createSignMenu(Application a, JSeshView v) {
		// Reverse sign
		// Size (240, 200, 144, 120, 100, 70, 50, 35, 25, 1)
		// Rotation (graphical! 30 and 45 °)
		// Toggle sign is red
		// Toggle wide sign
		//
		// (ignore grammar, inside word/word end, sentence end)
		// Toggle ignore sign
		// sign shading...

		return null;
	}

	/**
	 * Optional action addition.
	 * 
	 * @param menu
	 * @param a
	 * @param v
	 * @param actionID
	 */
	private void addToMenu(JMenu menu, Application a, JSeshView v,
			String actionID) {
		Action action = a.getActionMap(v).get(actionID);
		if (action != null)
			menu.add(action);
	}

	private JSimplePalette createHieroglyphicPalette() {
		PalettePresenter palettePresenter = new PalettePresenter();
		palettePresenter
				.setHieroglyphPaletteListener(new MyHieroglyphicPaletteListener());
		return palettePresenter.getSimplePalette();
	}

	@Override
	public List<JToolBar> createToolBars(Application a, View p) {
		List<JToolBar> toolbars = new ArrayList<JToolBar>();
		JToolBar hoolbar = new JToolBar("hieroglyphs");
		hoolbar.add(createHieroglyphicPalette());
		// toolbars.add(hoolbar);
		toolbars.add(hoolbar);
		return toolbars;
	}

	public ActionMap createActionMap(Application a, View v) {
		JMDCEditor editor = null;
		JSeshView jseshView = (JSeshView) v;
		if (jseshView != null) {
			editor = jseshView.getEditor();
		}

		ActionMap map = super.createActionMap(a, v);

		map.put(EditDocumentPreferencesAction.ID,
				new EditDocumentPreferencesAction(a, v));
		map.put(SetAsModelAction.ID, new SetAsModelAction(a, v));

		map.remove(DuplicateAction.ID);
		map.remove(DeleteAction.ID);
		map.remove(CopyAction.ID);
		map.remove(CutAction.ID);
		map.remove(PasteAction.ID);

		map.remove(SelectAllAction.ID);
		map.remove(ClearSelectionAction.ID);
		// map.put(SelectAllAction.ID,
		// getEditorAction(editor, jsesh.editor.ActionsID.SELECT_ALL));
		// map.put(ClearSelectionAction.ID,
		// getEditorAction(editor, jsesh.editor.ActionsID.CLEAR_SELECTION));
		// addEditorAction(map, editor, ActionsID.COPY_AS_PDF);
		// addEditorAction(map, editor, ActionsID.COPY_AS_RTF);
		// addEditorAction(map, editor, ActionsID.COPY_AS_BITMAP);
		// addEditorAction(map, editor, ActionsID.COPY_AS_MDC);
		//
		// addEditorAction(map, editor, ActionsID.SET_MODE_HIEROGLYPHS);
		// addEditorAction(map, editor, ActionsID.SET_MODE_ITALIC);
		// addEditorAction(map, editor, ActionsID.SET_MODE_LATIN);
		// addEditorAction(map, editor, ActionsID.SET_MODE_BOLD);
		// addEditorAction(map, editor, ActionsID.SET_MODE_LINENUMBER);
		// addEditorAction(map, editor, ActionsID.SET_MODE_TRANSLIT);

		map.put(InsertShortTextAction.ID, new InsertShortTextAction(a, v));

		for (int i = 0; i < 3; i++)
			map.put(jsesh.jhotdraw.actions.edit.SelectCopyPasteConfigurationAction.partialID
					+ i,
					new jsesh.jhotdraw.actions.edit.SelectCopyPasteConfigurationAction(
							a, v, i));

		map.put(EditGroupAction.ID, new EditGroupAction(editor));

		addInsertAction(map, a, v,
				JSeshApplicationActionsID.INSERT_FULL_SHADING,
				SymbolCodes.FULLSHADE);
		addInsertAction(map, a, v,
				JSeshApplicationActionsID.INSERT_HORIZONTAL_SHADING,
				SymbolCodes.HORIZONTALSHADE);
		addInsertAction(map, a, v,
				JSeshApplicationActionsID.INSERT_VERTICAL_SHADING,
				SymbolCodes.VERTICALSHADE);
		addInsertAction(map, a, v,
				JSeshApplicationActionsID.INSERT_QUARTER_SHADING,
				SymbolCodes.QUATERSHADE);

		// Shading symbols;.. full size, vertical, horizontal, quarter.

		// addEditorAction(map, editor, ActionsID.GROUP_HORIZONTAL);
		// addEditorAction(map, editor, ActionsID.GROUP_VERTICAL);
		// addEditorAction(map, editor, ActionsID.LIGATURE_ELEMENTS);

		if (editor != null) {
			for (Object actionIDa : editor.getActionMap().keys()) {
				if (map.get(actionIDa) == null)
					map.put(actionIDa, editor.getActionMap().get(actionIDa));
			}
		}

		return map;
	}

	private void addInsertAction(ActionMap map, Application a, View jseshView,
			String id, int code) {
		map.put(id, new InsertElementAction(a, jseshView, id, code));
	}

	@Override
	public URIChooser createOpenChooser(Application a, View v) {
		JFileURIChooser chooser = new JFileURIChooser();
		String description = BundleHelper.getInstance().getLabel(
				"file.glyphFile.text");
		chooser.addChoosableFileFilter(new ExtensionFileFilter(description,
				"gly"));
		return chooser;
	}

	@Override
	public URIChooser createSaveChooser(Application a, View v) {
		JFileURIChooser chooser = new JFileURIChooser();
		String description = BundleHelper.getInstance().getLabel(
				"file.glyphFile.text");
		chooser.addChoosableFileFilter(new ExtensionFileFilter(description,
				"gly"));
		return chooser;
	}

	private class MyHieroglyphicPaletteListener implements
			HieroglyphPaletteListener {

		public void signSelected(String code) {
			if (application.getActiveView() != null
					&& application.getActiveView() instanceof JSeshView) {
				JSeshView currentView = (JSeshView) application.getActiveView();
				currentView.insertCode(code);
			} else {
				System.err.println(application.getActiveView());
			}
		}
	}

	/**
	 * @param configurationNumber
	 * @see jsesh.jhotdraw.JSeshApplicationBase#selectCopyPasteConfiguration(int)
	 */
	public void selectCopyPasteConfiguration(int configurationNumber) {
		jseshBase.selectCopyPasteConfiguration(configurationNumber);
	}

	@Override
	/**
	 * Create the standard menus for this application.
	 * This method is not in the original jhotdraw 7. A better, yet similar, option has 
	 * been made available recently, and will be used a bit later.
	 * 
	 * Remark : action creation should not go here !!!!!!
	 */
	public StandardMenuBuilder getStandardMenuBuilder() {
		return new StandardMenuBuilder() {

			public void atEndOfFileMenu(JMenu fileMenu, Application app,
					View view) {
				ActionMap map = app.getActionMap(view);
				JSeshView jSeshView = (JSeshView) view;

				// add the import and export as menus...
				JMenu importMenu = BundleHelper.getInstance().configure(
						new JMenu(), "file.import");
				importMenu.add(new ImportPDFAction(app));
				JMenu exportMenu = BundleHelper.getInstance().configure(
						new JMenu(), "file.export");
				exportMenu.add(new ExportAsBitmapAction(app, view));

				fileMenu.add(importMenu);
				fileMenu.add(exportMenu);
				fileMenu.addSeparator();
				addToMenu(fileMenu, app, jSeshView, SetAsModelAction.ID);
				fileMenu.addSeparator();
				addToMenu(fileMenu, app, jSeshView,
						EditDocumentPreferencesAction.ID);
			}

			public void atEndOfEditMenu(JMenu editMenu, Application app,
					View view) {
				editMenu.addSeparator();
				JMenu copyAsMenu = BundleHelper.getInstance().configure(
						new JMenu(), "edit.copyAs");
				ActionMap map = app.getActionMap(view);
				copyAsMenu.add(map.get(ActionsID.COPY_AS_PDF));
				copyAsMenu.add(map.get(ActionsID.COPY_AS_RTF));
				copyAsMenu.add(map.get(ActionsID.COPY_AS_BITMAP));
				copyAsMenu.add(map.get(ActionsID.COPY_AS_MDC));

				editMenu.add(copyAsMenu);
				editMenu.addSeparator();
				editMenu.add(map.get(ActionsID.SET_MODE_HIEROGLYPHS));
				editMenu.add(map.get(ActionsID.SET_MODE_LATIN));
				editMenu.add(map.get(ActionsID.SET_MODE_ITALIC));
				editMenu.add(map.get(ActionsID.SET_MODE_TRANSLIT));
				editMenu.add(map.get(ActionsID.SET_MODE_LINENUMBER));
				editMenu.add(map.get(InsertShortTextAction.ID));

				editMenu.addSeparator();
				ButtonGroup group = new ButtonGroup();
				JRadioButtonMenuItem copyAndPaste1 = new JRadioButtonMenuItem(
						map.get(jsesh.jhotdraw.actions.edit.SelectCopyPasteConfigurationAction.partialID
								+ "0"));
				JRadioButtonMenuItem copyAndPaste2 = new JRadioButtonMenuItem(
						map.get(jsesh.jhotdraw.actions.edit.SelectCopyPasteConfigurationAction.partialID
								+ "1"));
				JRadioButtonMenuItem copyAndPaste3 = new JRadioButtonMenuItem(
						map.get(jsesh.jhotdraw.actions.edit.SelectCopyPasteConfigurationAction.partialID
								+ "2"));
				group.add(copyAndPaste1);
				group.add(copyAndPaste2);
				group.add(copyAndPaste3);
				editMenu.add(copyAndPaste1);
				editMenu.add(copyAndPaste2);
				editMenu.add(copyAndPaste3);
				copyAndPaste1.setSelected(true);
			}

			public void afterFileOpen(JMenu fileMenu, Application app, View view) {
			}

			public void afterFileNew(JMenu fileMenu, Application app, View view) {
			}

			public void afterFileClose(JMenu fileMenu, Application app,
					View view) {
			}
		};
	}

	/**
	 * Build a menu for shading quadrants.
	 * @param v
	 * @param a
	 * @param menubar
	 */
	private JMenu buildShadingMenu(Application a, JSeshView v) {
		JMenu shadingMenu = BundleHelper.getInstance().configure(
				new JMenu(), "text.shadingMenu");
		shadingMenu.setMnemonic(KeyEvent.VK_H);
		JPopupMenu pm = shadingMenu.getPopupMenu();
		pm.setLayout(new GridLayout(0, 4));
		for (String s : EditorShadeAction.getActionNames()) {
			shadingMenu.add(a.getActionMap(v).get(s));
		}
		return shadingMenu;
	}

	private void buildShadeSignMenu(JMenu parent) {
		/*
		 * JMenu signShading = new JMenu("Sign Shading"); JPopupMenu pm =
		 * signShading.getPopupMenu(); pm.setLayout(new GridLayout(0, 4)); for
		 * (int i = 0; i < workflow.getShadeSignActions().length; i++) {
		 * signShading.add(workflow.getShadeSignActions()[i]); }
		 * parent.add(signShading);
		 */
	}

	/**
	 * @param menubar
	 * @return 
	 */
	private JMenu buildCartoucheMenu(Application a, JSeshView v) {
		JMenu cartoucheMenu = BundleHelper.getInstance().configure(
				new JMenu(), "text.cartoucheMenu");

		cartoucheMenu.setMnemonic(KeyEvent.VK_C);
		JPopupMenu pm = cartoucheMenu.getPopupMenu();
		pm.setLayout(new GridLayout(0, 4));
		for (String s : EditorCartoucheAction.cartoucheActionNames) {
			cartoucheMenu.add(a.getActionMap(v).get(s));
		}
		return cartoucheMenu;
	}

	/**
	 * Build menu for Ecdotic/Philological marks.
	 * @param a
	 * @param v
	 * @return
	 */
	
	private JMenu buildEcdoticMenu(Application a, JSeshView v) {
		JMenu ecdoticMenu = BundleHelper.getInstance().configure(
				new JMenu(), "text.ecdoticMenu");

		JPopupMenu pm = ecdoticMenu.getPopupMenu();
		pm.setLayout(new GridLayout(0, 4));
		for (String s : AddPhilologicalMarkupAction.philologyActionNames) {
			ecdoticMenu.add(a.getActionMap(v).get(s));
		}
		return ecdoticMenu;
	}

}
