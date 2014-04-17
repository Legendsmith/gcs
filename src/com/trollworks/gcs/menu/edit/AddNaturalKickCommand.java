/*
 * Copyright (c) 1998-2014 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * version 2.0. If a copy of the MPL was not distributed with this file, You
 * can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as defined
 * by the Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.menu.edit;

import com.trollworks.toolkit.annotation.Localize;
import com.trollworks.toolkit.utility.Localization;


import com.trollworks.gcs.character.GURPSCharacter;
import com.trollworks.gcs.character.SheetWindow;
import com.trollworks.toolkit.ui.menu.Command;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

/** Provides the "Add Natural Kick" command. */
public class AddNaturalKickCommand extends Command {
	@Localize("Include Kick In Weapons")
	private static String ADD_NATURAL_KICK;

	static {
		Localization.initialize();
	}

	/** The action command this command will issue. */
	public static final String					CMD_ADD_NATURAL_KICK	= "AddNaturalKick";			//$NON-NLS-1$

	/** The singleton {@link AddNaturalKickCommand}. */
	public static final AddNaturalKickCommand	INSTANCE				= new AddNaturalKickCommand();

	private AddNaturalKickCommand() {
		super(ADD_NATURAL_KICK, CMD_ADD_NATURAL_KICK);
	}

	@Override
	public void adjustForMenu(JMenuItem item) {
		Window window = getActiveWindow();
		if (window instanceof SheetWindow) {
			setEnabled(true);
			setMarked(((SheetWindow) window).getCharacter().includeKick());
		} else {
			setEnabled(false);
			setMarked(false);
		}
		updateMark(item);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		GURPSCharacter character = ((SheetWindow) getActiveWindow()).getCharacter();
		character.setIncludeKick(!character.includeKick());
	}
}