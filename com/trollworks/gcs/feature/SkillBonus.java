/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is GURPS Character Sheet.
 *
 * The Initial Developer of the Original Code is Richard A. Wilkes.
 * Portions created by the Initial Developer are Copyright (C) 1998-2002,
 * 2005-2009 the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * ***** END LICENSE BLOCK ***** */

package com.trollworks.gcs.feature;

import com.trollworks.gcs.criteria.StringCompareType;
import com.trollworks.gcs.criteria.StringCriteria;
import com.trollworks.gcs.skill.Skill;
import com.trollworks.gcs.widgets.outline.ListRow;
import com.trollworks.ttk.xml.XMLReader;
import com.trollworks.ttk.xml.XMLWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/** A skill bonus. */
public class SkillBonus extends Bonus {
	/** The XML tag. */
	public static final String	TAG_ROOT			= "skill_bonus";	//$NON-NLS-1$
	private static final String	TAG_NAME			= "name";			//$NON-NLS-1$
	private static final String	TAG_SPECIALIZATION	= "specialization"; //$NON-NLS-1$
	private static final String	EMPTY				= "";				//$NON-NLS-1$
	private StringCriteria		mNameCriteria;
	private StringCriteria		mSpecializationCriteria;

	/** Creates a new skill bonus. */
	public SkillBonus() {
		super(1);
		mNameCriteria = new StringCriteria(StringCompareType.IS, EMPTY);
		mSpecializationCriteria = new StringCriteria(StringCompareType.IS_ANYTHING, EMPTY);
	}

	/**
	 * Loads a {@link SkillBonus}.
	 * 
	 * @param reader The XML reader to use.
	 */
	public SkillBonus(XMLReader reader) throws IOException {
		this();
		load(reader);
	}

	/**
	 * Creates a clone of the specified bonus.
	 * 
	 * @param other The bonus to clone.
	 */
	public SkillBonus(SkillBonus other) {
		super(other);
		mNameCriteria = new StringCriteria(other.mNameCriteria);
		mSpecializationCriteria = new StringCriteria(other.mSpecializationCriteria);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof SkillBonus && super.equals(obj)) {
			SkillBonus sb = (SkillBonus) obj;
			if (mNameCriteria.equals(sb.mNameCriteria)) {
				return mSpecializationCriteria.equals(sb.mSpecializationCriteria);
			}
		}
		return false;
	}

	@Override
	public Feature cloneFeature() {
		return new SkillBonus(this);
	}

	@Override
	public String getXMLTag() {
		return TAG_ROOT;
	}

	@Override
	public String getKey() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Skill.ID_NAME);
		if (mNameCriteria.getType() == StringCompareType.IS && mSpecializationCriteria.getType() == StringCompareType.IS_ANYTHING) {
			buffer.append('/');
			buffer.append(mNameCriteria.getQualifier());
		} else {
			buffer.append("*"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

	@Override
	protected void loadSelf(XMLReader reader) throws IOException {
		String name = reader.getName();
		if (TAG_NAME.equals(name)) {
			mNameCriteria.load(reader);
		} else if (TAG_SPECIALIZATION.equals(name)) {
			mSpecializationCriteria.load(reader);
		} else {
			super.loadSelf(reader);
		}
	}

	/**
	 * Saves the bonus.
	 * 
	 * @param out The XML writer to use.
	 */
	@Override
	public void save(XMLWriter out) {
		out.startSimpleTagEOL(TAG_ROOT);
		mNameCriteria.save(out, TAG_NAME);
		mSpecializationCriteria.save(out, TAG_SPECIALIZATION);
		saveBase(out);
		out.endTagEOL(TAG_ROOT, true);
	}

	/** @return The name criteria. */
	public StringCriteria getNameCriteria() {
		return mNameCriteria;
	}

	/** @return The name criteria. */
	public StringCriteria getSpecializationCriteria() {
		return mSpecializationCriteria;
	}

	@Override
	public void fillWithNameableKeys(HashSet<String> set) {
		ListRow.extractNameables(set, mNameCriteria.getQualifier());
		ListRow.extractNameables(set, mSpecializationCriteria.getQualifier());
	}

	@Override
	public void applyNameableKeys(HashMap<String, String> map) {
		mNameCriteria.setQualifier(ListRow.nameNameables(map, mNameCriteria.getQualifier()));
		mSpecializationCriteria.setQualifier(ListRow.nameNameables(map, mSpecializationCriteria.getQualifier()));
	}
}